package org.bdc.dcm.modbus.decoder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bdc.dcm.modbus.handler.ModbusTcpHandler;
import org.bdc.dcm.modbus.requests.ReadFullReadHoldingRegistersRequest;
import org.bdc.dcm.utils.CommTypeConvert;
import org.bdc.dcm.vo.DataTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digitalpetri.modbus.FunctionCode;
import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.UnsupportedPdu;
import com.digitalpetri.modbus.requests.MaskWriteRegisterRequest;
import com.digitalpetri.modbus.requests.ReadCoilsRequest;
import com.digitalpetri.modbus.requests.ReadDiscreteInputsRequest;
import com.digitalpetri.modbus.requests.ReadInputRegistersRequest;
import com.digitalpetri.modbus.requests.WriteMultipleCoilsRequest;
import com.digitalpetri.modbus.requests.WriteMultipleRegistersRequest;
import com.digitalpetri.modbus.requests.WriteSingleCoilRequest;
import com.digitalpetri.modbus.requests.WriteSingleRegisterRequest;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

/**
 * 为了 框架的继承性结构 这里请用于组合
 * @author 李哲弘
 *
 */
public abstract class ModbusRequestDecoder<T> implements ModbusPduDecoder<T> {

	private Logger logger =LoggerFactory.getLogger(this.getClass());
	
	private List<DataTab> dataTabs = new ArrayList<>();
	
	private CommTypeConvert convert;
	
	public ModbusRequestDecoder(List<DataTab> dataTabs,CommTypeConvert convert) {
		this.dataTabs = dataTabs;
		this.convert = convert;
	}
	/**
	 * pdu解析
	 */
	@Override
    public T decodePdu(byte modbusAddr ,byte[] macBytes,ByteBuf pduBytebuf) throws DecoderException {
        int code = pduBytebuf.readByte();

        FunctionCode functionCode = FunctionCode
                .fromCode(code)
                .orElseThrow(() -> new DecoderException("invalid function code: " + code));

        ModbusPdu pdu = decodeResponse(functionCode, pduBytebuf);
        
        return convert(pdu, modbusAddr, macBytes,ModbusTcpHandler.SAVEKEY);
    }
	/**
	 * 将 pdu 转为统一的 返回值
	 * @param modbusPdu
	 * @param modbusAddr
	 * @param macBytes
	 * @oaram dataKey 设置数据的key
	 * @return
	 */
	protected abstract T convert(ModbusPdu modbusPdu,byte modbusAddr ,byte[] macBytes,String dataKey);
	
    private ModbusPdu decodeResponse(FunctionCode functionCode, ByteBuf buffer) throws DecoderException {
        switch (functionCode) {
            case ReadCoils:
                return decodeReadCoils(buffer);

            case ReadDiscreteInputs:
                return decodeReadDiscreteInputs(buffer);

            case ReadHoldingRegisters:
                return decodeReadHoldingRegisters(buffer);

            case ReadInputRegisters:
                return decodeReadInputRegisters(buffer);

            case WriteSingleCoil:
                return decodeWriteSingleCoil(buffer);

            case WriteSingleRegister:
                return decodeWriteSingleRegister(buffer);

            case WriteMultipleCoils:
                return decodeWriteMultipleCoils(buffer);

            case WriteMultipleRegisters:
                return decodeWriteMultipleRegisters(buffer);

            case MaskWriteRegister:
                return decodeMaskWriteRegister(buffer);

            default:
                return new UnsupportedPdu(functionCode);
        }
    }

    private ReadCoilsRequest decodeReadCoils(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        int quantity = buffer.readUnsignedShort();

        return new ReadCoilsRequest(address, quantity);
    }

    private ReadDiscreteInputsRequest decodeReadDiscreteInputs(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        int quantity = buffer.readUnsignedShort();

        return new ReadDiscreteInputsRequest(address, quantity);
    }

    private ReadFullReadHoldingRegistersRequest decodeReadHoldingRegisters(ByteBuf buffer) {
    	buffer.readByte();//长度标识
    	Map<String,Object> map = new HashMap<>();
    	DataTab tabFirst = null;
    	int baseAddr = -1;
    	for(DataTab tab:dataTabs){
    		Object val = convert.convertByteBuf2TypeValue(tab, buffer);
    		if(val != null){
    			map.put(tab.getId()+"", val);
    			tabFirst = tab;
    		}
    	}
    	if(tabFirst != null)
    		baseAddr = tabFirst.getId();
    	if(logger.isDebugEnabled())
    		logger.debug("{}",map);
        return new ReadFullReadHoldingRegistersRequest(baseAddr, map);
    }

    private ReadInputRegistersRequest decodeReadInputRegisters(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        int quantity = buffer.readUnsignedShort();

        return new ReadInputRegistersRequest(address, quantity);
    }

    private WriteSingleCoilRequest decodeWriteSingleCoil(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        boolean value = buffer.readUnsignedShort() == 0xFF00;

        return new WriteSingleCoilRequest(address, value);
    }

    private WriteSingleRegisterRequest decodeWriteSingleRegister(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        int value = buffer.readUnsignedShort();

        return new WriteSingleRegisterRequest(address, value);
    }

    private WriteMultipleCoilsRequest decodeWriteMultipleCoils(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        int quantity = buffer.readUnsignedShort();
        int byteCount = buffer.readUnsignedByte();
        ByteBuf values = buffer.readSlice(byteCount).retain();

        return new WriteMultipleCoilsRequest(address, quantity, values);
    }

    private WriteMultipleRegistersRequest decodeWriteMultipleRegisters(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        int quantity = buffer.readUnsignedShort();
        int byteCount = buffer.readByte();
        ByteBuf values = buffer.readSlice(byteCount).retain();

        return new WriteMultipleRegistersRequest(address, quantity, values);
    }

    private MaskWriteRegisterRequest decodeMaskWriteRegister(ByteBuf buffer) {
        int address = buffer.readUnsignedShort();
        int andMask = buffer.readUnsignedShort();
        int orMask = buffer.readUnsignedShort();

        return new MaskWriteRegisterRequest(address, andMask, orMask);
    }
}
