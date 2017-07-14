package org.bdc.dcm.utils;

import org.bdc.dcm.vo.DataPack;
import org.bdc.dcm.vo.e.DataPackType;
import org.bdc.dcm.vo.e.Datalevel;

public class DataPackUtils {

	public static DataPack getInitDataPack(String mac) {
		DataPack dataPack = new DataPack();
		dataPack.setMac(mac);
		dataPack.setOnlineStatus(1);
		dataPack.setDatalevel(Datalevel.NORMAL);
		dataPack.setDataPackType(DataPackType.Info);
		return dataPack;
	}
}
