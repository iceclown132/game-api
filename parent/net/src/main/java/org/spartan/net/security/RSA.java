package org.spartan.net.security;

import java.math.BigInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class RSA {

	/**
	 * The modulus key
	 */
	private final BigInteger modulus;
	
	/**
	 * The decryption key
	 */
	private final BigInteger decryptionKey;
	
	/**
	 * The encryption key
	 */
	private final BigInteger encryptionKey;

	/**
	 * 
	 * @param modulus
	 * @param exponent
	 * @return
	 */
	public static RSA decryptor(BigInteger modulus, BigInteger exponent) {
		return new RSA(modulus, exponent, null);
	}

	/**
	 * 
	 * @param modulus
	 * @param exponent
	 * @return
	 */
	public static RSA encryptor(BigInteger modulus, BigInteger exponent) {
		return new RSA(modulus, null, exponent);
	}

	/**
	 * @param modulus
	 * @param decryptionKey
	 * @param encryptionKey
	 */
	RSA(BigInteger modulus, BigInteger d, BigInteger e) {
		this.modulus = modulus;
		this.decryptionKey = d;
		this.encryptionKey = e;
	}

	/**
	 * Encrypts a block of data
	 * 
	 * @param data
	 * @param length
	 * @return
	 */
	public ByteBuf encrypt(ByteBuf data, int length) {
		if (encryptionKey == null) {
			throw new UnsupportedOperationException("no encryption key present");
		}
		byte[] byte_array = new byte[length];
		data.readBytes(byte_array);
		return Unpooled.wrappedBuffer(new BigInteger(byte_array).modPow(encryptionKey, modulus).toByteArray());
	}

	/**
	 * Decrypts a block of data
	 * 
	 * @param data
	 * @param length
	 * @return
	 */
	public ByteBuf decrypt(ByteBuf data, int length) {
		if (decryptionKey == null) {
			throw new UnsupportedOperationException("no decryption key present");
		}
		byte[] byte_array = new byte[length];
		data.readBytes(byte_array);
		return Unpooled.wrappedBuffer(new BigInteger(byte_array).modPow(decryptionKey, modulus).toByteArray());
	}

}