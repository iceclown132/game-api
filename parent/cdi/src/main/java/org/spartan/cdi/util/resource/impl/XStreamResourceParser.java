package org.spartan.cdi.util.resource.impl;

import java.nio.file.Files;
import java.nio.file.Path;

import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.cdi.util.resource.ResourceParser;

import com.thoughtworks.xstream.XStream;

public class XStreamResourceParser<T> implements ResourceParser<T> {

	/**
	 * Hello
	 */
	private final XStream xstream = new XStream();

	/**
	 * Adds an alias to the xstream object
	 * @param name
	 * @param type
	 * @return
	 */
	public XStreamResourceParser<T> alias(String name, Class<?> type) {
		xstream.alias(name, type);
		return this;
	}

	@Override
	public T parse(Path path) throws Exception {
		return ReflectionUtil.cast(xstream.fromXML(Files.newBufferedReader(path)));
	}

}
