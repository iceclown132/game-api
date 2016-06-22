package org.spartan.net.message.attribute;

import io.netty.buffer.ByteBuf;

public enum AttributeType {
	BYTE(buffer -> buffer.readUnsignedByte(), (buffer, value) -> buffer.writeByte((int) value)),
	SHORT(buffer -> buffer.readUnsignedShort(), (buffer, value) -> buffer.writeShort((int) value)),
	MEDIUM(buffer -> buffer.readUnsignedMedium(), (buffer, value) -> buffer.writeMedium((int) value)),
	INTEGER(buffer -> buffer.readUnsignedInt(), (buffer, value) -> buffer.writeInt((int) value)),
	LONG(buffer -> buffer.readLong(), (buffer, value) -> buffer.writeLong((long) value)),
	STRING(buffer -> buffer.readUnsignedByte(), (buffer, value) -> buffer.writeByte(0)),
	CONSTANT(buffer -> 0, (buffer, value) -> buffer.writeByte(0));
	
	/**
	 * 
	 */
	private Extractor extractor;
	
	/**
	 * 
	 */
	private Inserter inserter;
	
	/**
	 * @param extractor
	 * @param inserter
	 */
	private AttributeType(Extractor extractor, Inserter inserter) {
		this.extractor = extractor;
		this.inserter = inserter;
	}

	/**
	 * 
	 * @param buffer
	 * @return
	 */
	public Object extract(ByteBuf buffer) {
		return extractor.extract(buffer);
	}
	
	public void insert(ByteBuf buffer, Object value) {
		inserter.insert(buffer, value);
	}

	/**
	 * 
	 * @author brock
	 *
	 */
	private static interface Extractor {
		Object extract(ByteBuf buffer);
	}

	/**
	 * 
	 * @author brock
	 *
	 */
	private static interface Inserter {
		void insert(ByteBuf buffer, Object value);
	}

}
