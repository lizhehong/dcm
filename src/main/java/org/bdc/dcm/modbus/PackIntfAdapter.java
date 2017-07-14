package org.bdc.dcm.modbus;

import java.util.Optional;

import org.bdc.dcm.data.coder.intf.CustomPackIntf;
import org.bdc.dcm.data.coder.intf.NotModbusCallBack;

import io.netty.buffer.ByteBuf;

public abstract class PackIntfAdapter<T> implements CustomPackIntf<T>{

	/**
	 * mac 地址
	 */
	private byte[] macByte;
	/**
	 * 存放 不存在modbus 结果的数据
	 */
	protected Optional<T> callBackResult;
	
	public PackIntfAdapter(byte[] macByte) {
		super();
		this.macByte = macByte;
	}
	

	@Override
	public ByteBuf unPackToModbus(ByteBuf msg, NotModbusCallBack<T> callback) {
		Optional<NotModbusCallBack<T>> optional = Optional.ofNullable(callback);
		ByteBuf payload = unPackToPayload(msg);
		return payloadToModbusBuf(payload,optional);
	}

	public byte[] getMacByte() {
		return macByte;
	}

	public void setMacByte(byte[] macByte) {
		this.macByte = macByte;
	}


	public Optional<T> getCallBackResult() {
		return callBackResult;
	}

	public void setCallBackResult(Optional<T> callBackResult) {
		this.callBackResult = callBackResult;
	}

	@Override
	public abstract ByteBuf unPackToPayload(ByteBuf msg);

	@Override
	public abstract ByteBuf payloadToModbusBuf(ByteBuf payload, Optional<NotModbusCallBack<T>> callback);

	
	
	
}
