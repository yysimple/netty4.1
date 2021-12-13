package com.simple.nio.selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能描述: selector的测试
 *
 * @author: WuChengXing
 * @create: 2021-12-13 15:05
 **/
public class SelectorServerTest {

    /**
     * 第一步：创建 Selector 选择器
     * 第二步：创建 ServerSocketChannel 通道，并绑定监听端口
     * 第三步：设置 Channel 通道是非阻塞模式
     * 第四步：把 Channel 注册到 Socketor 选择器上，监听连接事件
     * 第五步：调用 Selector 的 select 方法（循环调用），监测通道的就绪状况
     * 第六步：调用 selectKeys 方法获取就绪 channel 集合
     * 第七步：遍历就绪 channel 集合，判断就绪事件类型，实现具体的业务操作
     * 第八步：根据业务，决定是否需要再次注册监听事件，重复执行第三步操作
     */
    @Test
    public void testServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 12345);
        ssc.bind(socketAddress);
        ssc.configureBlocking(false);
        // 接收类型
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer readBuff = ByteBuffer.allocate(1024);
        ByteBuffer writeBuff = ByteBuffer.allocate(128);
        writeBuff.put("received".getBytes());
        writeBuff.flip();

        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                // 处理完之后移除该key
                iterator.remove();
                if (next.isAcceptable()) {
                    // 创建新的连接，并且把连接注册到 selector 上，而且，
                    // 声明这个 channel 只对读操作感兴趣。
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("isAcceptable");
                } else if (next.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    readBuff.clear();
                    socketChannel.read(readBuff);
                    readBuff.flip();
                    System.out.println("received : " + new String(readBuff.array()));
                    next.interestOps(SelectionKey.OP_WRITE);
                } else if (next.isWritable()) {
                    writeBuff.rewind();
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    socketChannel.write(writeBuff);
                    next.interestOps(SelectionKey.OP_READ);
                    System.out.println("写操作，只对读感兴趣");
                }
            }
        }
    }

    @Test
    public void testClient() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 12345));
            ByteBuffer writeBuffer = ByteBuffer.allocate(32);
            ByteBuffer readBuffer = ByteBuffer.allocate(32);
            writeBuffer.put("hello".getBytes());
            writeBuffer.flip();
            while (true) {
                writeBuffer.rewind();
                socketChannel.write(writeBuffer);
                readBuffer.clear();
                socketChannel.read(readBuffer);
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
        }
    }
}
