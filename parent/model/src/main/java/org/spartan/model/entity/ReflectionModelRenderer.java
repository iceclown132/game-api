package org.spartan.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.cdi.Container;
import org.spartan.cdi.inject.annotation.Startup;
import org.spartan.cdi.util.ReflectionsInitializer;
import org.spartan.model.entity.sync.render.Attribute;
import org.spartan.model.entity.sync.render.AttributeRenderer;
import org.spartan.model.entity.sync.render.AttributeRendererMethod;
import org.spartan.model.entity.sync.render.Render;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


/**
 * 
 * @author brock
 *
 */
public class ReflectionModelRenderer implements ModelRenderer {

	/**
	 * The logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(ReflectionModelRenderer.class);

	/**
	 * The collection of attribute renderers
	 */
	private static final Map<Class<? extends DynamicEntity>, Map<Class<? extends Attribute>, AttributeRendererMethod>> renderers = new HashMap<>();

	/**
	 * The type of entity that needs to be bamboozled
	 */
	private final Class<? extends DynamicEntity> entityType;

	/**
	 * @param entityType
	 */
	public ReflectionModelRenderer(Class<? extends DynamicEntity> entityType) {
		this.entityType = entityType;
	}
	
	/**
	 * Initializes all of the renderers
	 * @param container
	 */
	@Startup
	public static void initialize(Container container) {
		ReflectionsInitializer.initialize().getMethodsAnnotatedWith(AttributeRenderer.class).forEach(method -> {
			AttributeRenderer renderer = method.getDeclaredAnnotation(AttributeRenderer.class);
			
			if (!renderers.containsKey(renderer.entity())) {
				renderers.put(renderer.entity(), new HashMap<>());
			}
			AttributeRendererMethod attribute_renderer = new AttributeRendererMethod(container.instance(method.getDeclaringClass()).get(), method, renderer.identifier());
			renderers.get(renderer.entity()).put(method.getParameterTypes()[0].asSubclass(Attribute.class), attribute_renderer);
			
			logger.info("{} rendered by {}.{}", method.getParameterTypes()[0].getName(), method.getDeclaringClass().getName(), method.getName());
		});
	}

	@Override
	public Render render(Model model) {
		ByteBuf buffer = Unpooled.buffer();
		AtomicInteger marker = new AtomicInteger(0);
		
		model.attributes().forEach(attribute -> {
			marker.set(marker.get() | renderers.get(entityType).get(attribute.getClass()).getMask());
			buffer.writeBytes(renderers.get(entityType).get(attribute.getClass()).render(attribute));
		});
		
		return new Render(marker.get(), Unpooled.buffer().writeByte(marker.get()).writeBytes(buffer));
	}

}
