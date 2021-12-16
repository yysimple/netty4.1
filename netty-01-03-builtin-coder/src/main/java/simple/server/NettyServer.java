package simple.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import simple.init.MyChannelInitializer;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 13:58
 **/
public class NettyServer {
    public static void main(String[] args) {
        new NettyServer().bing(7397);
    }

    private void bing(int port) {
        // 配置服务端NIO线程组,这里其实只有一个即可，而且一个线程组里面会去分配多个EventLoop去处理对应的channel相关的io
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup)
                    // 这里是去初始化一个用来跟客户端进行通信的 “子” 通道
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());
            ChannelFuture f = b.bind(port).sync();
            System.out.println("测试服务端接收消息");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
        }

    }
}
