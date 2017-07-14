package org.bdc.dcm.modbus.requests;

import java.util.Map;

import com.digitalpetri.modbus.FunctionCode;
import com.digitalpetri.modbus.requests.SimpleModbusRequest;

public class ReadFullReadHoldingRegistersRequest extends SimpleModbusRequest {

	
	private final int address;
    private final Map<String,Object> quantity;

    /**
     * @param address  0x0000 to 0xFFFF (0 to 65535)
     * @param quantity 无限长
     */
    public ReadFullReadHoldingRegistersRequest(int address, Map<String,Object> quantity) {
        super(FunctionCode.ReadHoldingRegisters);

        this.address = address;
        this.quantity = quantity;
    }

    public int getAddress() {
        return address;
    }

    public Map<String,Object> getQuantity() {
        return quantity;
    }

	@Override
	public String toString() {
		return "ReadFullReadHoldingRegistersRequest [address=" + address + ", quantity=" + quantity + "]";
	}
	
}
