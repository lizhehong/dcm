package org.bdc.dcm.netty.channel.tcp;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.List;

import org.bdc.dcm.conf.IntfConf;
import org.bdc.dcm.netty.channel.AbstractChannelInitializer;
import org.bdc.dcm.netty.coder.tcp.TcpLqmdbDecoder;
import org.bdc.dcm.netty.coder.tcp.TcpLqmdbEncoder;
import org.bdc.dcm.netty.framer.LqmdbFrameDecoder;
import org.bdc.dcm.netty.handler.LqmdbDataHandler;
import org.bdc.dcm.utils.CommTypeConvert;
import org.bdc.dcm.utils.LcTypeConvert;
import org.bdc.dcm.vo.DataTab;
import org.bdc.dcm.vo.e.DataType;

public class TcpLqmdbChannelInitializer extends AbstractChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		super.initChannel(ch);
		
		List<DataTab> dataTabs = IntfConf.getDataTabConf().getDataTabConf(DataType.Lqmdb.name());
		CommTypeConvert convert = new LcTypeConvert();
		
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("framer", new LqmdbFrameDecoder());
		pipeline.addLast("decoder", new TcpLqmdbDecoder(getNettyBoot(), dataTabs, convert));
		pipeline.addLast("encoder", new TcpLqmdbEncoder(getNettyBoot(), dataTabs, convert));
		pipeline.addLast("dataHandler", new LqmdbDataHandler(getNettyBoot()));
	}

}
