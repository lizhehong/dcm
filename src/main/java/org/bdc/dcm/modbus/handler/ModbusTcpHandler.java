package org.bdc.dcm.modbus.handler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.bdc.dcm.modbus.requests.ModbusTcpServiceRequest;
import org.bdc.dcm.netty.NettyBoot;
import org.bdc.dcm.netty.handler.DataHandler;
import org.bdc.dcm.vo.DataPack;

import com.digitalpetri.modbus.ModbusPdu;

import io.netty.channel.ChannelHandlerContext;

public class ModbusTcpHandler extends DataHandler{

	public static final String SAVEKEY = "pdu";
	
	private final AtomicReference<ServiceRequestHandler> requestHandler =
	            new AtomicReference<>(new ServiceRequestHandler() {});
	
	public ModbusTcpHandler(NettyBoot nettyBoot) {
		super(nettyBoot);
	}
	public void setRequestHandler(ServiceRequestHandler requestHandler) {
        this.requestHandler.set(requestHandler);
    }
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DataPack msg) throws Exception {
		super.messageReceived(ctx, msg);
		if(msg.getData() !=null && msg.getData().keySet().size() > 0)
			onChannelRead(ctx,msg);
	}
	private void onChannelRead(ChannelHandlerContext ctx, DataPack dataPack) {
		 	Map<String,Object> data = dataPack.getData();
		 	ModbusPdu pdu = (ModbusPdu)data.get(SAVEKEY);
	        ServiceRequestHandler handler = requestHandler.get();
	        if (handler == null) return;

	        switch (pdu.getFunctionCode()) {
	            case ReadCoils:
	                handler.onReadCoils(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case ReadDiscreteInputs:
	                handler.onReadDiscreteInputs(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case ReadHoldingRegisters:
	                handler.onReadHoldingRegisters(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case ReadInputRegisters:
	                handler.onReadInputRegisters(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case WriteSingleCoil:
	                handler.onWriteSingleCoil(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case WriteSingleRegister:
	                handler.onWriteSingleRegister(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case WriteMultipleCoils:
	                handler.onWriteMultipleCoils(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case WriteMultipleRegisters:
	                handler.onWriteMultipleRegisters(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            case MaskWriteRegister:
	                handler.onMaskWriteRegister(ModbusTcpServiceRequest.of(pdu, ctx.channel()));
	                break;

	            default:
	                break;
	        }
	    }
}
