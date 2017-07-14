package org.bdc.dcm.modbus.encoder;

import com.digitalpetri.modbus.ModbusPdu;

import io.netty.buffer.ByteBuf;

public interface ModbusPduEncoder {

	 ByteBuf encode(ModbusPdu modbusPdu, ByteBuf buffer);
}
