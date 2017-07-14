package org.bdc.dcm.data.coder.intf;

import io.netty.buffer.ByteBuf;

public interface NotModbusCallBack<T> {

	public T execute(byte command, ByteBuf payload);
}
