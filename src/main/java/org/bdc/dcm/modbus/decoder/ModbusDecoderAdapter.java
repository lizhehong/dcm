package org.bdc.dcm.modbus.decoder;

import java.util.Optional;

import org.bdc.dcm.data.coder.intf.DataDecoder;
import org.bdc.dcm.data.coder.intf.NotModbusCallBack;
import org.bdc.dcm.modbus.PackIntfAdapter;
import org.bdc.dcm.vo.DataPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;

public abstract class ModbusDecoderAdapter implements DataDecoder<ByteBuf>{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private boolean inited ;
	
	private NotModbusCallBack<DataPack> notModbusCallBack;
	
	private PackIntfAdapter<DataPack> packIntfAdapter;

	private ModbusRequestDecoder<DataPack> decoder;
	
	public void init(){
		if(!inited){
			inited = true;
			decoder = getDecoder();
			notModbusCallBack = getNotModbusCallBack();
			packIntfAdapter = getPackIntfAdapter();
		}
	}
	@Override
	@SuppressWarnings("unused")
	public DataPack data2Package(ChannelHandlerContext ctx, ByteBuf msg){
		
		try {
			init();
			
			//保证继承者没有复写接口 说明 部分逻辑不需要
			ByteBuf modbus = msg;
			
			if(packIntfAdapter != null)
				modbus = packIntfAdapter.unPackToModbus(msg,notModbusCallBack);
			
			if(modbus != null ){
				byte addr = modbus.readByte();
				ByteBuf pdu = modbus.readBytes(modbus.readableBytes() - 2);
				ByteBuf crcBuf = modbus.readBytes(2);
				
				//这一步一定要写
				if(decoder == null)
					logger.error("请实现 ModbusRequestDecoder<DataPack> getDecoder() !!!! 否则无法通过解码获得pdu buf 转datePack");
				
				byte[] macBytes = null;
				
				if(packIntfAdapter != null)
					macBytes = packIntfAdapter.getMacByte();
				
				return decoder.decodePdu(addr,macBytes,pdu);
			}else{
				
				Optional<DataPack> optional = Optional.empty();
				if(packIntfAdapter != null)
					optional = packIntfAdapter.getCallBackResult();
				
				if(optional.isPresent())
					return optional.get();
				else
					return null;
			}
		} catch (DecoderException e) {
			e.printStackTrace();
		} 
		
		return null;
		
	}

	public static void reverse(byte[] bs){
		byte tmp ;
		for(int i=0;i<bs.length/2;i++){
			tmp = bs[i];
			bs[i] = bs[bs.length - 1 -i];
			bs[bs.length - 1 - i] = tmp;
		}
	}

	/**
	 * <h1><b>一定要写!!!!!</b></h1>
	 * <h4>接触对应业务包的包实现类 </h4>
	 */
	public abstract ModbusRequestDecoder<DataPack> getDecoder();
	/**
	 * 设置 如果不是是遵循modbus 协议 那么需要做的业务
	 */
	public abstract NotModbusCallBack<DataPack> getNotModbusCallBack();
	/**
	 * 适配包接口
	 */
	public abstract PackIntfAdapter<DataPack> getPackIntfAdapter() ;
	
	
}
