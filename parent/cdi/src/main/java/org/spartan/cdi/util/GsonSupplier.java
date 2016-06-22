package org.spartan.cdi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSupplier {

	/**
	 * 
	 * @return
	 */
	public static Gson supply() {
		return new GsonBuilder().create();
	}

}
