package org.bdc.dcm.data.coder.intf;

import java.util.Optional;

import io.netty.buffer.ByteBuf;

/**

 *	<table>
 *		<tr>
 *			<td>Header(2)</td>
 *			<td>Type(1)</td>
 *			<td>Len(1)</td>
 *			<td>Payload(N)</td>
 *			<td>CRC(1)</td>
 *		</tr>
 *		<tr>
 *			<td>FEH A5H</td>
 *			<td>
 *				<p>&nbsp;&nbsp;&nbsp;00H:&nbsp;命令格式</p>
 *				<p>&nbsp;&nbsp;&nbsp;01H:&nbsp;数据格式</p>
 *				<p>&nbsp;&nbsp;&nbsp;FFH:&nbsp;ACK</p>
 *			</td>
 *			<td>1个字节长度,表示payload(数据)的长度</td>
 *			<td>N个字节的数据</td>
 *			<td>1个字节校验码,  从type 开始到校验码之前的所有字节累加,不计超过 FFH的溢出值</td>
 *		</tr>
 *	</table>
 * @author 李哲弘
 *
 */
public interface CustomPackIntf<T> {

	/**
	 * 解包 出 playLoad
	 * @param msg
	 * @return
	 */
	public ByteBuf unPackToPayload(ByteBuf msg);
	/**
	 * 解包 出完整 Modbus 
	 * @param Payload
	 * @return Modbus ByteBuf
	 */
	public ByteBuf payloadToModbusBuf(ByteBuf payload,Optional<NotModbusCallBack<T>> callback);
	
	/**
	 * 
	 * @param msg
	 * @param callback
	 * @return
	 */
	public ByteBuf unPackToModbus(ByteBuf msg,NotModbusCallBack<T> callback);
}
