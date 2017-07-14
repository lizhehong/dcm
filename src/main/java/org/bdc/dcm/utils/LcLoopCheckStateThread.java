package org.bdc.dcm.utils;

import java.util.Optional;

import org.bdc.dcm.conf.IntfConf;
import org.bdc.dcm.vo.Server;
import org.bdc.dcm.vo.e.DataType;

import com.util.tools.Public;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class LcLoopCheckStateThread implements Runnable {

	private int delaySendingTime;
	
	private String mac;
	
	private boolean isRun;
	
	private ChannelHandlerContext ctx;
	
	public LcLoopCheckStateThread() {
		super();
		this.isRun = false;
		Optional<Server> optional =  IntfConf
										.getServerConf()
										.getServerConf()
										.stream()
										.filter(item->{
											
											boolean flag1 = item.getDataType().equals(DataType.Lcmdb);
											boolean flag2 = item.getName().equals("TestTcpServer");
											
											return flag1 & flag2;
										})
										.findFirst();
		if(optional.isPresent()){
			Server server = optional.get();
			delaySendingTime = server.getDelaySendingTime();
		}
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public void run() {
		this.isRun = true;
		try {
			while(this.isRun){
				for(byte i=1;i<21 && this.isRun;i++){
					if(delaySendingTime > 0)
						Thread.sleep(delaySendingTime * 1000);
					byte[] cmd = Public.hexString2bytes("FE A5 01 12 16 "+ mac +" 00 ");
					byte[] modbus = Public.hexString2bytes(Public.byte2hex_ex(i)+" 03 00 80 00 0F");
					byte[] crc16 = Public.crc16_A001(modbus);
					int sum=0;
					for(int j=2;j<cmd.length;j++){
						sum+=(cmd[j] & 0xff);
					}
					for(int j=0;j<modbus.length;j++){
						sum+=(modbus[j] & 0xff);
					}
					for(int j=0;j<crc16.length;j++){
						sum+=(crc16[j] & 0xff);
					}
					byte crcSumByte = ((byte) (sum & 0xff));
					ByteBuf bu = ctx.alloc().buffer();
					bu.writeBytes(cmd);
					bu.writeBytes(modbus);
					bu.writeByte(crc16[1]);
					bu.writeByte(crc16[0]);
					bu.writeByte(crcSumByte);
					ctx.channel().writeAndFlush(bu);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}