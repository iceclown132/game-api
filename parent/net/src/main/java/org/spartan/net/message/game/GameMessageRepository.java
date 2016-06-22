package org.spartan.net.message.game;

import static org.joox.JOOX.$;

import java.nio.file.Files;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.spartan.cdi.Container;
import org.spartan.cdi.util.ReflectionsInitializer;
import org.spartan.net.message.IncomingMessageDefinition;
import org.spartan.net.message.Message;
import org.spartan.net.message.MessageRepository;
import org.spartan.net.message.OutgoingMessageDefinition;
import org.spartan.net.message.attribute.Attribute;
import org.spartan.net.message.attribute.AttributeType;
import org.spartan.net.message.game.annotation.Intercepter;
import org.spartan.net.message.game.annotation.Serializer;
import org.spartan.net.message.game.annotation.StatefulIntercepter;
import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;
import org.w3c.dom.Element;

/**
 * This class is very very messy class
 * 
 * @author brock
 *
 */
public class GameMessageRepository implements MessageRepository {
	
	/**
	 * The logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(GameMessageRepository.class);
	
	/**
	 * The reflections instance
	 */
	private final Reflections reflections = ReflectionsInitializer.initialize();

	/**
	 * The colleciton of incoming message definitions
	 */
	private final Set<IncomingMessageDefinition> incoming_definitions = new HashSet<>();
	
	/**
	 * The collection of outgoing message definitions
	 */
	private final Set<OutgoingMessageDefinition> outgoing_definitions = new HashSet<>();

	/**
	 * Initializes the repository
	 * 
	 * @param container
	 * @throws Exception 
	 */
	public void initialize(Container container) throws Exception {
		loadAnnotationConfiguration(container);
		loadXmlConfiguration(container);
	}

	@Override
	public Set<IncomingMessageDefinition> incomingStatefulDefinition(ConnectionState connectionState) {
		return incoming_definitions.stream().filter(definition -> connectionState.equals(definition.getConnectionState())).collect(Collectors.toSet());
	}

	@Override
	public Set<OutgoingMessageDefinition> outgoingStatefulDefinition(ConnectionState connectionState) {
		return outgoing_definitions.stream().filter(definition -> connectionState.equals(definition.getConnectionState())).collect(Collectors.toSet());
	}

	@Override
	public Set<IncomingMessageDefinition> incomingNumberedDefinition(int opcode) {
		return incoming_definitions.stream().filter(definition -> opcode == definition.getOpcode()).collect(Collectors.toSet());
	}

	@Override
	public Set<OutgoingMessageDefinition> outgoingTypedDefinition(Class<?> type) {
		return outgoing_definitions.stream().filter(definition -> type.equals(definition.getModel())).collect(Collectors.toSet());
	}
	
	/**
	 * Ugly but necessary code
	 * 
	 * @param container
	 * @throws Exception 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws DOMException 
	 * @throws NumberFormatException 
	 */
	private final void loadXmlConfiguration(Container container) throws Exception {
		$($(Files.newBufferedReader(container.resource("net/protocol/message-decoders.xml"))).document()).find("decoder").forEach(node -> {
			int opcode = Integer.parseInt(attribute(node, "opcode"));
			int size = Integer.parseInt(attribute(node, "length"));
			String model = attribute(node, "model");
			Queue<Attribute> attributes = new LinkedList<>();
			$(node).find("attribute").forEach(inner_node -> {
				String attribute_name = inner_node.getTextContent();
				String attribute_type = attribute(inner_node, "type");
				attributes.add(new Attribute(attribute_name, AttributeType.valueOf(attribute_type)));
			});
			incoming_definitions.add(new IncomingMessageDefinition(opcode, size, loadClass(model),
					ConnectionState.GAME, new ReflectionMessageDecorator(container, attributes)));
			logger.info("reflection decorator - opcode:{}, produces:{}", opcode, model);
		});
		
		/*
		 * Load the file that parses the message encoders
		 */
		$($(Files.newBufferedReader(container.resource("net/protocol/message-encoders.xml"))).document()).find("encoder").forEach(node -> {
			int opcode = Integer.parseInt(attribute(node, "opcode"));
			int size = Integer.parseInt(attribute(node, "length"));
			String model = attribute(node, "model");
			Queue<Attribute> attributes = new LinkedList<>();
			$(node).find("attribute").forEach(inner_node -> {
				String attribute_name = inner_node.getTextContent();
				String attribute_type = attribute(inner_node, "type");
				attributes.add(new Attribute(attribute_name, AttributeType.valueOf(attribute_type)));
			});
			outgoing_definitions.add(new OutgoingMessageDefinition(opcode, size, loadClass(model), ConnectionState.GAME, new ReflectionMessageSerializer(attributes)));
			logger.info("reflection encoder - opcode:{}, produces:{}", opcode, model);
		});
	}

	/**
	 * Ugly but necessary code
	 * 
	 * @param container
	 */
	private final void loadAnnotationConfiguration(Container container) {
		/*
		 * Look for all of the methods annotated with Intercepter and add a new incoming message definition for them
		 */
		reflections.getMethodsAnnotatedWith(Intercepter.class).forEach(method -> {
			Intercepter annotation = method.getDeclaredAnnotation(Intercepter.class);
			
			if (!method.getReturnType().equals(Message.class) && method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(Message.class)) {
				incoming_definitions.add(new IncomingMessageDefinition(annotation.value(), annotation.size(), method.getReturnType(), ConnectionState.GAME,
						new ProxyMethodMessageDecorator(method, container.manager().get(method.getDeclaringClass()), container)));
				
				logger.info("indexed interceptor method - index:{}, method:{}.{}, produces:{}",
						annotation.value(), method.getDeclaringClass().getName(), method.getName(), method.getReturnType().getName());
			}
			else {
				throw new IllegalArgumentException("intercepter methods must not return Message or Void and should have only Message as parameter - "
						+ method.getDeclaringClass().getName() + "." + method.getName());
			}
		});
		
		/*
		 * Look for all of the methods annotated with StatefulIntercepter and add a new incoming message definition for them
		 */
		reflections.getMethodsAnnotatedWith(StatefulIntercepter.class).forEach(method -> {
			StatefulIntercepter annotation = method.getDeclaredAnnotation(StatefulIntercepter.class);
			
			if (!method.getReturnType().equals(Message.class) && method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(Message.class)) {
				incoming_definitions.add(new IncomingMessageDefinition(-1, annotation.size(), method.getReturnType(), annotation.value(),
						new ProxyMethodMessageDecorator(method, container.manager().get(method.getDeclaringClass()), container)));
				
				logger.info("stateful interceptor method - state:{}, method:{}.{}, produces:{}",
						annotation.value(), method.getDeclaringClass().getName(), method.getName(), method.getReturnType().getName());
			}
			else {
				throw new IllegalArgumentException("intercepter methods must not return Message or Void and should have only Message as parameter - "
						+ method.getDeclaringClass().getName() + "." + method.getName());
			}
		});
		
		/*
		 * Look for all methods annotated with Serializer and make a new outgoing message definition for them
		 */
		reflections.getMethodsAnnotatedWith(Serializer.class).forEach(method -> {
			Serializer annotation = method.getDeclaredAnnotation(Serializer.class);
			
			if (method.getReturnType().equals(Message.class) && method.getParameterCount() == 1 && !method.getParameterTypes()[0].equals(Message.class)) {
				outgoing_definitions.add(new OutgoingMessageDefinition(-1, annotation.variableSize() ? -1 : 0, method.getParameterTypes()[0],
						ConnectionState.GAME, new ProxyMethodMessageSerializer(method, container.manager().get(method.getDeclaringClass()), container)));
				
				logger.info("serializer method - model:{}, method:{}.{}, produces:{}",
						method.getReturnType(), method.getDeclaringClass().getName(), method.getName(), -1);
			}
			else {
				throw new IllegalArgumentException("Serializer methods must only return Message and should have at least 1 parameter that is not of type Message - "
						+ method.getDeclaringClass().getName() + "." + method.getName());
			}
		});
	}

	/**
	 * Loads a class without throwing an error
	 *
	 * @param className
	 * @return
	 */
	private Class<?> loadClass(String className) {
		try {
			return ClassLoader.getSystemClassLoader().loadClass(className);
		} catch (Exception ex) {
			throw new NullPointerException("could not load class " + className);
		}
	}

	/**
	 * Gets an attribute's value or returns a default value
	 *
	 * @param element
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	private String attribute(Element element, String name) {
		if (!element.hasAttribute(name)){
			throw new IllegalArgumentException("attribute " + name + " not defined");
		}
		return element.getAttribute(name);
	}

}
