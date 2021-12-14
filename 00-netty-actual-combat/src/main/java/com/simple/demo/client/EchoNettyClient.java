package com.simple.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 12:03
 **/
public class EchoNettyClient {
    /**
     * 用来连接服务器的地址和端口
     */
    private final String host;
    private final int port;

    public EchoNettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        // 客户端，同样是使用NIO作为连接事件处理
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    // 这里跟之前的服务端还是有一点不一样的，这里服务端的地址，也就是远程的意思
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new EchoNettyClientHandler());
                        }
                    });
            // 同步连接,未完成连接依旧是会阻塞
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 9999;
        new EchoNettyClient(host, port).start();
    }
}
