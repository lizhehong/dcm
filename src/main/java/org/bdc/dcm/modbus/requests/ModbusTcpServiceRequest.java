package org.bdc.dcm.modbus.requests;

import org.bdc.dcm.modbus.encoder.ModbusPduEncoder;
import org.bdc.dcm.modbus.encoder.ModbusResponseEncoder;
import org.bdc.dcm.modbus.handler.ServiceRequestHandler;
import org.bdc.dcm.vo.DataPack;

import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.requests.ModbusRequest;
import com.digitalpetri.modbus.responses.ModbusResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class ModbusTcpServiceRequest<Request extends ModbusRequest>
		implements ServiceRequestHandler.ServiceRequest<Request> {

	private final Request request;
	private final Channel channel;
	private final ModbusPduEncoder encoder = new ModbusResponseEncoder();
	
	private ModbusTcpServiceRequest(Request request, Channel channel) {
		this.request = request;
		this.channel = channel;
	}

	 @SuppressWarnings("unchecked")
     public static <Request extends ModbusRequest>
     ModbusTcpServiceRequest<Request> of(ModbusPdu pdu, Channel channel) {

         return new ModbusTcpServiceRequest<>(
                 (Request) pdu,
                 channel
         );
     }
	
	@Override
	public Request getRequest() {
		return request;
	}

	@Override
	public void sendResponse(ModbusResponse response,byte addr) {
		ByteBuf msg = encoder.encode(response, channel.alloc().buffer());
		channel.writeAndFlush(msg);
	}

}
