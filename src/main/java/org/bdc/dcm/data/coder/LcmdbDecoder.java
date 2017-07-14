package org.bdc.dcm.data.coder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bdc.dcm.data.coder.intf.NotModbusCallBack;
import org.bdc.dcm.data.coder.unpack.LcUnPackIntfImpl;
import org.bdc.dcm.modbus.PackIntfAdapter;
import org.bdc.dcm.modbus.decoder.ModbusDecoderAdapter;
import org.bdc.dcm.modbus.decoder.ModbusRequestDecoder;
import org.bdc.dcm.utils.CommTypeConvert;
import org.bdc.dcm.utils.DataPackUtils;
import org.bdc.dcm.vo.DataPack;
import org.bdc.dcm.vo.DataTab;

import com.digitalpetri.modbus.ModbusPdu;
import com.util.tools.Public;

import io.netty.buffer.ByteBuf;

public class LcmdbDecoder extends ModbusDecoderAdapter{
	
	private List<DataTab> dataTabs;

	private CommTypeConvert convert;
	
	public LcmdbDecoder(List<DataTab> dataTabs,CommTypeConvert convert) {
		this.dataTabs = dataTabs;
		this.convert = convert;
	}
	
	@Override
	public PackIntfAdapter<DataPack> getPackIntfAdapter() {
		return new LcUnPackIntfImpl<>();
	}
	
	@Override
	public ModbusRequestDecoder<DataPack> getDecoder() {
		
		
		return new ModbusRequestDecoder<DataPack>(dataTabs,convert) {
			
			@Override
			protected DataPack convert(ModbusPdu modbusPdu, byte modbusAddr, byte[] macBytes,String dataKey) {
				DataPack dataPack = DataPackUtils.getInitDataPack(Public.byte2hex(macBytes) + " " + Public.byte2hex_ex(modbusAddr));
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(dataKey, modbusPdu);
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
