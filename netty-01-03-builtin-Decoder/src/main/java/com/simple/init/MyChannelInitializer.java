package com.simple.init;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import com.simple.handler.ServerReceiveMsgHandler;

import java.nio.charset.Charset;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 10:45
 **/
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        System.out.println("有一客户端链接到本服务端：");
        System.out.println("客户端地址:" + channel.remoteAddress().getHostString());
        System.out.println("客户端端口:" + channel.remoteAddress().getPort());

        /**
         * === 添加netty自己内置的编解码器，此处的解码器是基于分隔符的协议 ====
         * 这里就要继续讲讲了，除此之外还有
         * 基于长度的协议：LengthFieldBasedFrameDecoder(64 * 1024, 2, 10)，解码前比如前2位是无效数据，后10位是有效数据
         * 使用该协议就可以拿到指定数据；然后还有：HttpObjectDecoder 一个 HTTP 数据的解码器。
         * 在 io.netty.handler.codec 子包下面，你将会发现更多用于特定用例的编码器和解码器实现
         *
         * ---------------------
         *
         * 其实应该说说编解码框架的：可以理解为需要我们自定义的编解码类，比如netty提供的**解码器**：
         *  - ByteToMessageDecoder：将字节解码为消息（或者另一个字节序列）是一项如此常见的任务，以至于 Netty 为它提供了一个
         * 抽象的基类：ByteToMessageDecoder。由于你不可能知道远程节点是否会一次性地发送一个完整
         * 的消息，所以这个类会对入站数据进行缓冲，直到它准备好处理
         *
         *  - ReplayingDecoder：ReplayingDecoder扩展了ByteToMessageDecoder类，使
         * 得我们不必调用 readableBytes()方法。它通过使用一个自定义的ByteBuf实现 ，
         * ReplayingDecoderByteBuf，包装传入的ByteBuf实现了这一点，其将在内部执行该调用
         *
         *  - MessageToMessageDecoder：在两个消息格式之间进行转换（例如，从一种 POJO 类型转换为另一种）
         *
         * 除此之外，还有**编码器**：这个其实就是用来做逆向的事情
         *
         *  - 当然还有：ByteToMessageCodec：抽象类，让我们来研究这样的一个场景：我们需要将字节解码为某种形式的消息，可能是 POJO，随
         * 后再次对它进行编码。ByteToMessageCodec 将为我们处理好这一切，因为它结合了
         * ByteToMessageDecoder 以及它的逆向——MessageToByteEncoder。
         *
         *  - CombinedChannelDuplexHandler：正如我们前面所提到的，结合一个解码器和编码器可能会对可重用性造成影响。但是，有一
         * 种方法既能够避免这种惩罚，又不会牺牲将一个解码器和一个编码器作为一个单独的单元部署所
         * 带来的便利性。CombinedChannelDuplexHandler 提供了这个解决方案
         *
         * 除此之外就还有很多内置的编解码器，就类似该代码里面使用的这种类型；
         */
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 这里记得将两个 解码器 放在前面，要不然不会生效
        channel.pipeline().addLast(new ServerReceiveMsgHandler());
    }
}
