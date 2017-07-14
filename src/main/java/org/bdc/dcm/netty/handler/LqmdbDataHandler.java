package org.bdc.dcm.netty.handler;

import java.util.Map;

import org.bdc.dcm.modbus.handler.ModbusTcpHandler;
import org.bdc.dcm.modbus.handler.ServiceRequestHandler;
import org.bdc.dcm.modbus.requests.ReadFullReadHoldingRegistersRequest;
import org.bdc.dcm.netty.NettyBoot;

import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;

public class LqmdbDataHandler extends ModbusTcpHandler  {

	public LqmdbDataHandler(NettyBoot nettyBoot) {
		super(nettyBoot,new LqRequestHandler());
	}

}
class LqRequestHandler implements ServiceRequestHandler{

	@Override
	public void onReadHoldingRegisters(
			ServiceRequest<ReadFullReadHoldingRegistersRequest, ReadHoldingRegistersResponse> service) {
		ReadFullReadHoldingRegistersRequest  request = service.getRequest();
		Map<String,Object> quantities = request.getQuantity();
		//TODO  具体业务
	}

}