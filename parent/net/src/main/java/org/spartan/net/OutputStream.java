package org.spartan.net;

public interface OutputStream {

	/**
	 * Writes
	 * 
	 * @param object
	 */
	void write(Object object);
	
	/**
	 * Flushes
	 */
	void flush();
	
	/**
	 * Writes and flushes
	 */
	void writeAndFlush(Object object);
	
	/**
	 * closes the output stream
	 */
	void close();

}
