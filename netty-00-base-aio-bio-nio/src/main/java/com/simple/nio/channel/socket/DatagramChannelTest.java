package com.simple.nio.channel.socket;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: UDP使用的发包工具
 *
 * @author: WuChengXing
 * @create: 2021-12-13 13:37
 **/
public class DatagramChannelTest {

    @Test
    public void sendUDP() throws IOException, InterruptedException {
        DatagramChannel dc = DatagramChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9999);
        while (true) {
            dc.send(ByteBuffer.wrap("send msg".getBytes(StandardCharsets.UTF_8)), socketAddress);
            System.out.println("send waiting...");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    /**
     * 这里是使用bind去绑定对应的套接字，然后与服务器简历连接，这里对应的方法是：
     * send/receive
     *
     * @throws IOException
     */
    @Test
    public void receive() throws IOException {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress("127.0.0.1", 9999);
        receiveChannel.bind(receiveAddress);
        ByteBuffer receiveBuffer = ByteBuffer.allocate(512);
        while (true) {
            receiveBuffer.clear();
            SocketAddress sendAddress = receiveChannel.receive(receiveBuffer);
            receiveBuffer.flip();
            System.out.print(sendAddress.toString() + " ");
            System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
        }
    }

    @Test
    public void sendUDPWrite() throws IOException, InterruptedException {
        DatagramChannel dc = DatagramChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9999);
        dc.bind(socketAddress);
        while (true) {
            dc.write(ByteBuffer.wrap("send msg".getBytes(StandardCharsets.UTF_8)));
            System.out.println("send waiting...");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    @Test
    public void receiveRead() throws IOException {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress("127.0.0.1", 9999);
        receiveChannel.connect(receiveAddress);
        ByteBuffer receiveBuffer = ByteBuffer.allocate(512);
        receiveBuffer.clear();
        int read = receiveChannel.read(receiveBuffer);
        while (read != -1) {
            receiveBuffer.flip();
            while (receiveBuffer.hasRemaining()) {
                System.out.println("读取到的数据：" + (char) receiveBuffer.get());
            }
            receiveBuffer.clear();
            read = receiveChannel.read(receiveBuffer);
        }
    }

    @Test
    public void testConn() throws IOException {
        DatagramChannel connChannel = DatagramChannel.open();
        connChannel.bind(new InetSocketAddress(9998));
        connChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        connChannel.write(ByteBuffer.wrap("发包".getBytes("UTF-8")));
        ByteBuffer readBuffer = ByteBuffer.allocate(512);
        while (true) {
            try {
                readBuffer.clear();
                connChannel.read(readBuffer);
                readBuffer.flip();
                System.out.println(StandardCharsets.UTF_8.decode(readBuffer));
            } catch (Exception e) {
            }
        }
    }

}
