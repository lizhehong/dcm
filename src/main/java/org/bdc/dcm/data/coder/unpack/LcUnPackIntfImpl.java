package org.bdc.dcm.data.coder.unpack;

import java.util.Optional;

import org.bdc.dcm.data.coder.intf.NotModbusCallBack;
import org.bdc.dcm.modbus.PackIntfAdapter;

import io.netty.buffer.ByteBuf;


public class LcUnPackIntfImpl<T> extends PackIntfAdapter<T>{

	private int headerLen = 2;
	
	public LcUnPackIntfImpl() {
		super();
		this.macByte = new byte[8];
	}

	@Override
	public ByteBuf unPackToPayload(ByteBuf msg) {
		
		byte[] headerBytes = new byte[headerLen];
		msg.readBytes(headerBytes);
		//读类型 
		msg.readByte();
		//捕获数据包长度 不包含 crc校验和
		int len = msg.readByte() & 0xff;
		//因为 packlen 不包行crc校验和
		ByteBuf payload =msg.readBytes(len);
		
		msg.readByte();//最后一位 crc 和 保证 读完
		
		return payload;
	}

	@Override
	public ByteBuf payloadToModbusBuf(ByteBuf payload,Optional<NotModbusCallBack<T>> callback) {
		byte command = payload.readByte();
		if(command == 0x17){
			payload.readBytes(macByte);
			payload.readByte();//序号
			return payload.readBytes(payload.readableBytes());
		}else{
			if(callback.isPresent())
				this.callBackResult = Optional.ofNullable(callback.get().execute(command,payload));
			return null;
		}
	}

}
