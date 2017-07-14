package org.bdc.dcm.netty.coder.udp;

import java.util.List;

import org.bdc.dcm.data.coder.LcmdbDecoder;
import org.bdc.dcm.data.coder.udp.DataUdpDecoder;
import org.bdc.dcm.netty.NettyBoot;
import org.bdc.dcm.utils.CommTypeConvert;
import org.bdc.dcm.vo.DataTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpLcmdbDecoder extends UdpDecoder {

	final static Logger logger = LoggerFactory.getLogger(UdpLcmdbDecoder.class);
	
	public UdpLcmdbDecoder(NettyBoot nettyBoot, List<DataTab> dataTabs, CommTypeConvert convert) {
		super(logger, nettyBoot, new DataUdpDecoder(new LcmdbDecoder(dataTabs, convert)));
	}
	
}
