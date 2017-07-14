package org.bdc.dcm.netty.handler;

import java.util.Map;

import org.bdc.dcm.modbus.handler.ModbusTcpHandler;
import org.bdc.dcm.modbus.handler.ServiceRequestHandler;
import org.bdc.dcm.modbus.requests.ReadFullReadHoldingRegistersRequest;
import org.bdc.dcm.netty.NettyBoot;
import org.bdc.dcm.utils.LcLoopCheckStateThread;
import org.bdc.dcm.vo.DataPack;

import com.digitalpetri.modbus.responses.ModbusResponse;
import com.digitalpetri.modbus.responses.ReadCoilsResponse;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class LcmdbDataHandler extends ModbusTcpHandler {

	private LcLoopCheckStateThread loopThread = new LcLoopCheckStateThread();
	 
	public LcmdbDataHandler(NettyBoot nettyBoot) {
		super(nettyBoot,new LcRequestHandler());
	}

	@Override
	public void beforeOnChannelRead(ChannelHandlerContext ctx, DataPack dataPack) {
		if(!loopThread.isRun()){
			loopThread.setCtx(ctx);
			loopThread.setMac(dataPack.getMac());
			new Thread(loopThread).start();
		}else{//第二笔数据来
			//TODO --------测试代码
			
		}
	}
	
	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		super.close(ctx, promise);
		loopThread.setRun(false);
	}

}
class LcRequestHandler implements ServiceRequestHandler{

	@Override
	public void onReadHoldingRegisters(
			ServiceRequest<ReadFullReadHoldingRegistersRequest> service) {
		//ServiceRequestHandler.super.onReadHoldingRegisters(service);
		ReadFullReadHoldingRegistersRequest  request = service.getRequest();
		Map<String,Object> quantities = request.getQuantity();
		
		
		ModbusResponse response = new ReadCoilsResponse(coilStatus);
		//TODO  具体业务
		service.sendResponse(response );
	}

}