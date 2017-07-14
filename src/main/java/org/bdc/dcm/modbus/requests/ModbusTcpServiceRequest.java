package org.bdc.dcm.modbus.requests;

import org.bdc.dcm.modbus.handler.ServiceRequestHandler;

import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.requests.ModbusRequest;
import com.digitalpetri.modbus.responses.ModbusResponse;

import io.netty.channel.Channel;

public class ModbusTcpServiceRequest<Request extends ModbusRequest, Response extends ModbusResponse>
		implements ServiceRequestHandler.ServiceRequest<Request, Response> {

	private final Request request;
	private final Channel channel;

	private ModbusTcpServiceRequest(Request request, Channel channel) {
		this.request = request;
		this.channel = channel;
	}

	 @SuppressWarnings("unchecked")
     public static <Request extends ModbusRequest, Response extends ModbusResponse>
     ModbusTcpServiceRequest<Request, Response> of(ModbusPdu pdu, Channel channel) {

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
	public void sendResponse(Response response) {
		channel.writeAndFlush(response);
	}

}
