package org.spartan.cdi.util.resource;

import java.nio.file.Path;

public interface ResourceHandler {

	/**
	 * 
	 * @param string
	 * @param class1
	 * @return
	 */
	<T> T load(String string, ResourceParser<T> parser) throws Exception;

	/**
	 * 
	 * @param string
	 * @param class1
	 * @return
	 */
	<T> T load(String string, Class<? extends ResourceParser<T>> parserClass) throws Exception;

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	Path get(String path) throws Exception;

}
