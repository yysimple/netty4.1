package com.simple.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 11:43
 **/
public class EchoNettyServer {
    /**
     * 定义需要绑定的端口
     */
    private final int port;

    public EchoNettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        // 这里就先写死端口
        int port = 9999;
        new EchoNettyServer(port).start();
    }

    public void start() throws Exception {
        final EchoNettyServerHandler serverHandler = new EchoNettyServerHandler();
        // 这里打算用NIO的通道来进行通信，所以这里使用NIO的事件循环组来处理新的连接等
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 这里是指定NIO传输的服务端的channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 回调到handler里面处理，这里直接放在匿名内部类中了，一般独立出来自己写一个类去实现
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            // EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例同样的实例
                            // 当一个新的连接被接受时，一个新的子 Channel 将会被创建，而 ChannelInitializer 将会把一个你的
                            // EchoServerHandler 的实例添加到该 Channel 的 ChannelPipeline 中。
                            // 这个 ChannelHandler 将会收到有关入站消息的通知
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            // 获取 Channel 的CloseFuture，并且阻塞当前线程直到其完成
            f.channel().closeFuture().sync();
        } finally {
            // 释放资源
            group.shutdownGracefully().sync();
        }
    }
}
