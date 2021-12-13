package com.simple.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 11:57
 **/
public class ServerSocketChannelTest {
    public static final String GREETING = "Hello java nio.\r\n";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 定义端口
        int port = 1234;
        // 通过Wrap把数据放入到buffer中
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes(StandardCharsets.UTF_8));
        // 打开SSC链接
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 绑定套接字信息
        ssc.bind(new InetSocketAddress(port));
        // 配置成非阻塞
        ssc.configureBlocking(false);
        // 这里一直等待连接
        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel accept = ssc.accept();
            if (accept == null) {
                System.out.println("暂无连接，继续等待...");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " +
                        accept.socket().getRemoteSocketAddress());
                buffer.rewind();
                accept.write(buffer);
                accept.close();
            }
        }

    }
}
