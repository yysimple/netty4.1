package com.simple.nio.demo;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 功能描述: 客户端
 *
 * @author: WuChengXing
 * @create: 2021-12-14 08:26
 **/
public class ChatClient {
    /**
     * 启动客户端方法
     *
     * @param name
     * @throws IOException
     */
    public void startClient(String name) throws IOException {
        // 连接服务端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8000));
        // 接收服务端响应数据
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 创建线程
        new Thread(new ClientThread(selector)).start();
        // 向服务器端发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            if (msg.length() > 0) {
                socketChannel.write(StandardCharsets.UTF_8.encode(name + " : " + msg));
            }
        }
    }
}

class ClientThread implements Runnable {
    private Selector selector;

    public ClientThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                // 获取 channel 数量
                int readChannels = selector.select();
                if (readChannels == 0) {
                    continue;
                }
                // 获取可用的 channel
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 遍历集合
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    // 移除 set 集合当前 selectionKey
                    iterator.remove();
                    // 如果可读状态
                    if (selectionKey.isReadable()) {
                        readOperator(selector, selectionKey);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 处理可读状态操作
     *
     * @param selector
     * @param selectionKey
     * @throws IOException
     */
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        // 1 从 SelectionKey 获取到已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        // 2 创建 buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 3 循环读取客户端消息
        int readLength = socketChannel.read(byteBuffer);
        String message = "";
        if (readLength > 0) {
            // 切换读模式
            byteBuffer.flip();
            // 读取内容
            message += StandardCharsets.UTF_8.decode(byteBuffer);
        }
        // 4 将 channel 再次注册到选择器上，监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 5 把客户端发送消息，广播到其他客户端
        if (message.length() > 0) {
            // 广播给其他客户端
            System.out.println(message);
        }
    }
}
