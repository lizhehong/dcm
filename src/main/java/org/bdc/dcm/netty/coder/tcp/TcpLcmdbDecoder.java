package org.bdc.dcm.netty.coder.tcp;

import io.netty.buffer.ByteBuf;

import java.util.List;

import org.bdc.dcm.data.coder.LcmdbDecoder;
import org.bdc.dcm.data.coder.tcp.DataTcpDecoder;
import org.bdc.dcm.netty.NettyBoot;
import org.bdc.dcm.utils.CommTypeConvert;
import org.bdc.dcm.vo.DataTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpLcmdbDecoder extends TcpDecoder {

	final static Logger logger = LoggerFactory.getLogger(TcpLcmdbDecoder.class);
	
	public TcpLcmdbDecoder(NettyBoot nettyBoot, List<DataTab> dataTabs, CommTypeConvert convert) {
		super(logger, nettyBoot, new DataTcpDecoder<ByteBuf>(new LcmdbDecoder(dataTabs, convert)));
	}

}
