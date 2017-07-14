package org.bdc.dcm.data.coder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bdc.dcm.conf.IntfConf;
import org.bdc.dcm.data.coder.intf.NotModbusCallBack;
import org.bdc.dcm.data.coder.unpack.LcUnPackIntfImpl;
import org.bdc.dcm.intf.DataTabConf;
import org.bdc.dcm.modbus.PackIntfAdapter;
import org.bdc.dcm.modbus.decoder.ModbusDecoderAdapter;
import org.bdc.dcm.modbus.decoder.ModbusRequestDecoder;
import org.bdc.dcm.modbus.handler.ModbusTcpHandler;
import org.bdc.dcm.utils.DataPackUtils;
import org.bdc.dcm.vo.DataPack;
import org.bdc.dcm.vo.DataTab;
import org.bdc.dcm.vo.e.DataType;

import com.digitalpetri.modbus.ModbusPdu;
import com.util.tools.Public;

import io.netty.buffer.ByteBuf;

public class LcmdbDecoder extends ModbusDecoderAdapter{
	
	private final DataTabConf dataTabConf;

	public LcmdbDecoder() {
		this.dataTabConf = IntfConf.getDataTabConf();
	}
	
	@Override
	public PackIntfAdapter<DataPack> getPackIntfAdapter() {
		return new LcUnPackIntfImpl<>();
	}
	
	@Override
	public ModbusRequestDecoder<DataPack> getDecoder() {
		
		List<DataTab> dataTabs = dataTabConf.getDataTabConf(DataType.Lcmdb.name());
		
		
		return new ModbusRequestDecoder<DataPack>(dataTabs) {
			
			@Override
			protected DataPack convert(ModbusPdu modbusPdu, byte modbusAddr, byte[] macBytes) {
				DataPack dataPack = DataPackUtils.getInitDataPack(Public.byte2hex(macBytes) + " " + Public.byte2hex_ex(modbusAddr));
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(ModbusTcpHandler.SAVEKEY, modbusPdu);
				dataPack.setData(data);
				return dataPack;
			}
		};		
	}

	@Override
	public NotModbusCallBack<DataPack> getNotModbusCallBack() {
		return new NotModbusCallBack<DataPack>() {
			
			@Override
			public DataPack execute(byte command, ByteBuf payload) {
				DataPack datapack = new DataPack();
				if(command == 0x0C){
					byte[] bs = new byte[8];
					payload.readBytes(bs);
					String mac = Public.byte2hex(bs);
					datapack.setMac(mac);
					datapack.setData(new HashMap<>());
					return datapack;
				}else
					return null;
				
			}
		};
		
	}
	
}
