package com.simple.server;

import com.simple.service.ExtServerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:55
 **/
public class NettyServer implements Callable<Channel> {
    private final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final InetSocketAddress address;
    private final ExtServerService extServerService;

    public NettyServer(InetSocketAddress address, ExtServerService extServerService) {
        this.address = address;
        this.extServerService = extServerService;
    }

    /**
     * 配置服务端NIO线程组
     */
    private final EventLoopGroup parentGroup = new NioEventLoopGroup();
    private final EventLoopGroup childGroup = new NioEventLoopGroup();
    private Channel channel;

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new NettyServerInit(extServerService));

            channelFuture = b.bind(address).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                logger.info("服务器启动成功，等待连接...");
            } else {
                logger.error("服务端启动失败...");
            }
        }
        return channel;
    }

    public void destroy() {
        if (null == channel) {
            return;
        }
        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }
}
