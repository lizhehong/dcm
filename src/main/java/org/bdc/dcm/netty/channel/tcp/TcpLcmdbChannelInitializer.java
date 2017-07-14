package org.bdc.dcm.netty.channel.tcp;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.List;

import org.bdc.dcm.conf.IntfConf;
import org.bdc.dcm.netty.channel.AbstractChannelInitializer;
import org.bdc.dcm.netty.coder.tcp.TcpLcmdbDecoder;
import org.bdc.dcm.netty.coder.tcp.TcpLcmdbEncoder;
import org.bdc.dcm.netty.framer.LcmdbFrameDecoder;
import org.bdc.dcm.netty.handler.LcmdbDataHandler;
import org.bdc.dcm.utils.CommTypeConvert;
import org.bdc.dcm.utils.LcTypeConvert;
import org.bdc.dcm.vo.DataTab;
import org.bdc.dcm.vo.e.DataType;

public class TcpLcmdbChannelInitializer extends AbstractChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		super.initChannel(ch);
		
		List<DataTab> dataTabs = IntfConf.getDataTabConf().getDataTabConf(DataType.Lcmdb.name());
		CommTypeConvert convert = new LcTypeConvert();
		
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("framer", new LcmdbFrameDecoder());
		pipeline.addLast("decoder", new TcpLcmdbDecoder(getNettyBoot(), dataTabs, convert));
		pipeline.addLast("encoder", new TcpLcmdbEncoder(getNettyBoot(), dataTabs, convert));
		pipeline.addLast("dataHandler", new LcmdbDataHandler(getNettyBoot()));
	}

}
