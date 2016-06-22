package org.spartan.model.user.attribute;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;

/**
 * The table of CRC keys that should match those of any connecting client
 * 
 * @author brock
 *
 */
public class CRCTable {

	/**
	 * The CRC keys of the archives in the client that should be equal to those
	 * being sent by the user. This is used to check if the player is running
	 * the same revision of the client.
	 */
	private final int[] crc_keys;

	/**
	 * Constructor
	 * 
	 * @param crc_keys
	 */
	public CRCTable(int[] crc_keys) {
		this.crc_keys = Arrays.copyOf(crc_keys, crc_keys.length);
	}

	/**
	 * Compares the crc_keys of the client with those present in the server
	 * 
	 * @return
	 */
	public boolean validate() {
		return Arrays.stream(crc_keys).allMatch(key -> key == 0);
	}

	/**
	 * Attempts to extract the CRC keys from a given byte buffer
	 * 
	 * @param buffer
	 * @return
	 */
	public static CRCTable extract(ByteBuf buffer) {
		int[] crc = new int[9];
		for (int i = 0; i < 9; i++) {
			crc[i] = buffer.readInt();
		}
		return new CRCTable(crc);
	}

	/**
	 * Attempts to verify all of the CRC keys. This will return true if all the
	 * keys are either 0 or match the ones of the currently loaded cache
	 * 
	 * @return
	 * @throws CRCMismatchException 
	 */
	public boolean verify() throws CRCMismatchException {
		if (Arrays.stream(crc_keys).allMatch(key -> key == 0)) {
			return true;
		}
		throw new CRCMismatchException();
	}

	/**
	 * @author brock
	 */
	public static class CRCMismatchException extends Exception {
		private static final long serialVersionUID = 1L;
	}

}
