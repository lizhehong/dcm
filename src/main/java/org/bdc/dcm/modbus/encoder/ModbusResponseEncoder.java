package org.bdc.dcm.modbus.encoder;

import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.responses.MaskWriteRegisterResponse;
import com.digitalpetri.modbus.responses.ReadCoilsResponse;
import com.digitalpetri.modbus.responses.ReadDiscreteInputsResponse;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.digitalpetri.modbus.responses.ReadInputRegistersResponse;
import com.digitalpetri.modbus.responses.WriteMultipleCoilsResponse;
import com.digitalpetri.modbus.responses.WriteMultipleRegistersResponse;
import com.digitalpetri.modbus.responses.WriteSingleCoilResponse;
import com.digitalpetri.modbus.responses.WriteSingleRegisterResponse;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;

/**
 * 为了 框架的继承性结构 这里请用于组合
 * @author 李哲弘
 *
 */
public class ModbusResponseEncoder implements ModbusPduEncoder {
	 
	@Override
	    public ByteBuf encode(ModbusPdu modbusPdu, ByteBuf buffer) throws EncoderException {
	        try {
                switch (modbusPdu.getFunctionCode()) {
                    case ReadCoils:
                        return encodeReadCoils((ReadCoilsResponse) modbusPdu, buffer);

                    case ReadDiscreteInputs:
                        return encodeReadDiscreteInputs((ReadDiscreteInputsResponse) modbusPdu, buffer);

                    case ReadHoldingRegisters:
                        return encodeReadHoldingRegisters((ReadHoldingRegistersResponse) modbusPdu, buffer);

                    case ReadInputRegisters:
                        return encodeReadInputRegisters((ReadInputRegistersResponse) modbusPdu, buffer);

                    case WriteSingleCoil:
                        return encodeWriteSingleCoil((WriteSingleCoilResponse) modbusPdu, buffer);

                    case WriteSingleRegister:
                        return encodeWriteSingleRegister((WriteSingleRegisterResponse) modbusPdu, buffer);

                    case WriteMultipleCoils:
                        return encodeWriteMultipleCoils((WriteMultipleCoilsResponse) modbusPdu, buffer);

                    case WriteMultipleRegisters:
                        return encodeWriteMultipleRegisters((WriteMultipleRegistersResponse) modbusPdu, buffer);

                    case MaskWriteRegister:
                        return encodeMaskWriteRegister((MaskWriteRegisterResponse) modbusPdu, buffer);

                    default:
                        throw new EncoderException("FunctionCode not supported: " + modbusPdu.getFunctionCode());
                }
	        } finally {
	            ReferenceCountUtil.release(modbusPdu);
	        }
	    }


	    private ByteBuf encodeReadCoils(ReadCoilsResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeByte(response.getCoilStatus().readableBytes());
	        buffer.writeBytes(response.getCoilStatus());

	        return buffer;
	    }

	    private ByteBuf encodeReadDiscreteInputs(ReadDiscreteInputsResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeByte(response.getInputStatus().readableBytes());
	        buffer.writeBytes(response.getInputStatus());

	        return buffer;
	    }

	    private ByteBuf encodeReadHoldingRegisters(ReadHoldingRegistersResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeByte(response.getRegisters().readableBytes());
	        buffer.writeBytes(response.getRegisters());

	        return buffer;
	    }

	    private ByteBuf encodeReadInputRegisters(ReadInputRegistersResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeByte(response.getRegisters().readableBytes());
	        buffer.writeBytes(response.getRegisters());

	        return buffer;
	    }

	    private ByteBuf encodeWriteSingleCoil(WriteSingleCoilResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeShort(response.getAddress());
	        buffer.writeShort(response.getValue());

	        return buffer;
	    }

	    private ByteBuf encodeWriteSingleRegister(WriteSingleRegisterResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeShort(response.getAddress());
	        buffer.writeShort(response.getValue());

	        return buffer;
	    }

	    private ByteBuf encodeWriteMultipleCoils(WriteMultipleCoilsResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeShort(response.getAddress());
	        buffer.writeShort(response.getQuantity());

	        return buffer;
	    }

	    private ByteBuf encodeWriteMultipleRegisters(WriteMultipleRegistersResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeShort(response.getAddress());
	        buffer.writeShort(response.getQuantity());

	        return buffer;
	    }

	    private ByteBuf encodeMaskWriteRegister(MaskWriteRegisterResponse response, ByteBuf buffer) {
	        buffer.writeByte(response.getFunctionCode().getCode());
	        buffer.writeShort(response.getAddress());
	        buffer.writeShort(response.getAndMask());
	        buffer.writeShort(response.getOrMask());

	        return buffer;
	    }

}
