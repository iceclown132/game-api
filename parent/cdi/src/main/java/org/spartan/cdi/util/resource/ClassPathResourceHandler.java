package org.spartan.cdi.util.resource;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.spartan.cdi.inject.Instantiator;
import org.spartan.cdi.inject.reflect.SimpleClassInstantiator;
import org.spartan.cdi.util.ReflectionUtil;

public class ClassPathResourceHandler implements ResourceHandler {

	private final Instantiator instantiator = new SimpleClassInstantiator();

	/**
	 * 
	 * @param string
	 * @param class1
	 * @return
	 */
	public <T> T load(String string, ResourceParser<T> parser) throws Exception {
		return parser.parse(get(string));
	}

	/**
	 * 
	 * @param string
	 * @param class1
	 * @return
	 */
	public <T> T load(String string, Class<? extends ResourceParser<T>> parserClass) throws Exception {
		ResourceParser<T> parser = ReflectionUtil.cast(instantiator.instantiate(parserClass));
		return load(string, parser);
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public Path get(String path) throws Exception {
		URL url = ClassLoader.getSystemClassLoader().getResource(path);
		if (url == null) { // ClassLoader.getSystemResource() does not throw a filenotfound exception, it will return null
			throw new FileNotFoundException(path);
		}
		return Paths.get(url.toURI());
	}

}
