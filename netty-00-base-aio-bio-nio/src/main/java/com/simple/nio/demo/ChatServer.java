package com.simple.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 18:08
 **/
public class ChatServer {
    /**
     * 服务器端启动的方法
     *
     * @throws IOException
     */
    public void startServer() throws IOException {
        // 1 创建 Selector 选择器
        Selector selector = Selector.open();
        // 2 创建 ServerSocketChannel 通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 3 为 channel 通道绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8000));
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 4 把 channel 通道注册到 selector 选择器；以接收事件注册
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经启动成功了");
        // 5 循环，等待有新链接接入
        //while(true)
        for (; ; ) {
            // 获取 channel 数量
            int readChannels = selector.select();
            // 没有新的连接就一直等待
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
                // 6 根据就绪状态，调用对应方法实现具体业务操作
                // 6.1 如果 accept 状态
                if (selectionKey.isAcceptable()) {
                    // 处理接受状态
                    acceptOperator(serverSocketChannel, selector);
                }
                // 6.2 如果可读状态
                if (selectionKey.isReadable()) {
                    // 处理可读
                    readOperator(selector, selectionKey);
                }
            }
        }
    }

    /**
     * 这里拿到消息后，会先自己读取，然后进行广播
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
            //切换读模式
            byteBuffer.flip();
            //读取内容
            message += StandardCharsets.UTF_8.decode(byteBuffer);
        }
        // 4 将 channel 再次注册到选择器上，监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 5 把客户端发送消息，广播到其他客户端
        if (message.length() > 0) {
            // 广播给其他客户端
            System.out.println(message);
            castOtherClient(message, selector, socketChannel);
        }
    }

    /**
     * 服务端将某个客户端发送的消息广播出去
     *
     * @param message
     * @param selector
     * @param socketChannel
     * @throws IOException
     */
    private void castOtherClient(String message, Selector selector, SocketChannel socketChannel) throws IOException {
        // 1 获取所有已经接入 channel
        Set<SelectionKey> selectionKeySet = selector.keys();
        // 2 循环想所有 channel 广播消息
        for (SelectionKey selectionKey : selectionKeySet) {
            // 获取每个 channel
            Channel tarChannel = selectionKey.channel();
            // 不需要给自己发送
            if (tarChannel instanceof SocketChannel && tarChannel != socketChannel) {
                ((SocketChannel) tarChannel).write(StandardCharsets.UTF_8.encode(message));
            }
        }
    }

    /**
     * 首先第一步是接收到连接，接收到连接之后，先欢迎，给每个连接上的客户端都发送消息；然后将改通道注册到“read”事件上；
     * 这样这个客户端在写的时候，这里就能监听到其发送的信息
     *
     * @param serverSocketChannel
     * @param selector
     * @throws IOException
     */
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        // 1 接入状态，创建 socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 2 把 socketChannel 设置非阻塞模式
        socketChannel.configureBlocking(false);
        // 3 把 channel 注册到 selector 选择器上，监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 4 客户端回复信息
        socketChannel.write(StandardCharsets.UTF_8.encode("欢迎进入聊天室，请注意隐私安全"));
    }

    public static void main(String[] args) {
        try {
            new ChatServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
