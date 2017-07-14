package org.bdc.dcm.modbus.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

public interface ModbusPduDecoder<T> {

	T decodePdu(byte modbusAddr ,byte[] macBytes,ByteBuf pduBytebuf) throws DecoderException;

}
