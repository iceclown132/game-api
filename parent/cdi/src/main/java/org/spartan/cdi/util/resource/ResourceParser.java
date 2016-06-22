package org.spartan.cdi.util.resource;

import java.nio.file.Path;


public interface ResourceParser<T> {

	/**
	 * 
	 * @param path
	 * @return
	 */
	T parse(Path path) throws Exception;

}
