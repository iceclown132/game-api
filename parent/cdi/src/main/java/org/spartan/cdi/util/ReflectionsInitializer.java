package org.spartan.cdi.util;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;

public class ReflectionsInitializer {

	/**
	 * Initializes a new {@link Reflections} object
	 * @return
	 */
	public static Reflections initialize() {
		return new Reflections("", new FieldAnnotationsScanner(), new MethodAnnotationsScanner(),
				new MethodParameterScanner(), new ResourcesScanner(), new SubTypesScanner(false),
				new TypeAnnotationsScanner(), new TypeElementsScanner());
	}

}
