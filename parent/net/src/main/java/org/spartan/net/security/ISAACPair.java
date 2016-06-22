package org.spartan.net.security;

import io.netty.util.AttributeKey;

/**
 * 
 * @author brock
 *
 */
public class ISAACPair {
	
	/**
	 * The attribute key used for storing and retrieving the session's ISAAC pair
	 */
	public static final AttributeKey<ISAACPair> ATTRIBUTE_KEY = AttributeKey.valueOf("isaac_pair");

	/**
	 * The cipher for decoding message opcodes
	 */
	private final ISAAC decodingCipher;
	
	/**
	 * The cipher for encoding message opcodes
	 */
	private final ISAAC encodingCipher;
	
	/**
	 * 
	 * @param server_key
	 * @param client_key
	 */
	public ISAACPair(int[] key) {
		this.decodingCipher = new ISAAC(key);
		for(int i = 0; i < key.length; i++) {
			key[i] += 50;
		}
		this.encodingCipher = new ISAAC(key);
	}

	/**
	 * @return the decodingCipher
	 */
	public ISAAC getDecodingCipher() {
		return decodingCipher;
	}

	/**
	 * @return the encodingCipher
	 */
	public ISAAC getEncodingCipher() {
		return encodingCipher;
	}

}
