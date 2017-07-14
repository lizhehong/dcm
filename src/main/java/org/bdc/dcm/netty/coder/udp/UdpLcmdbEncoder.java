package org.bdc.dcm.netty.coder.udp;

import java.net.SocketAddress;
import java.util.List;

import org.bdc.dcm.data.coder.LcmdbEncoder;
import org.bdc.dcm.data.coder.udp.DataUdpEncoder;
import org.bdc.dcm.netty.NettyBoot;
import org.bdc.dcm.utils.CommTypeConvert;
import org.bdc.dcm.vo.DataTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpLcmdbEncoder extends UdpEncoder {
	
	final static Logger logger = LoggerFactory.getLogger(UdpLcmdbEncoder.class);
	
	public UdpLcmdbEncoder(SocketAddress remoteAddress, NettyBoot nettyBoot, List<DataTab> dataTabs, CommTypeConvert convert) {
		super(logger, nettyBoot, new DataUdpEncoder(new LcmdbEncoder(dataTabs, convert)));
		setRemoteAddress(remoteAddress);
	}

}
