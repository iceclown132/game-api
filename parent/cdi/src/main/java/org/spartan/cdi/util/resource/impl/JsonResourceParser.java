package org.spartan.cdi.util.resource.impl;

import java.nio.file.Files;
import java.nio.file.Path;

import org.spartan.cdi.util.resource.ResourceParser;

import com.google.gson.Gson;

public class JsonResourceParser<T> implements ResourceParser<T> {

	/**
	 * The gson object
	 */
	private final Gson gson = new Gson();

	/**
	 * the type of return object
	 */
	private final Class<T> type;

	/**
	 * @param type
	 */
	public JsonResourceParser(Class<T> type) {
		this.type = type;
	}

	@Override
	public T parse(Path path) throws Exception {
		return gson.fromJson(Files.newBufferedReader(path), type);
	}

}
