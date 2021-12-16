package com.simple.init;

import com.simple.coder.decoder.MyDecoder;
import com.simple.coder.encoder.MyEncoder;
import com.simple.handler.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 18:14
 **/
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 测试三组数据：
     * 1.
     * 02 34 68 69 68 69 03
     *
     * 2.
     * 02 34 68 69 68 69
     * 03
     *
     * 3.
     * 02 34 68 69 68 69 03 02 34
     * 68 69 68 69 03
     * @param channel
     */
    @Override
    protected void initChannel(SocketChannel channel) {
        // 00-netty-actual-combat/src/main/java/com/simple/coder下面很多中自定义方式
        //自定义解码器
        channel.pipeline().addLast(new MyDecoder());
        //自定义编码器
        channel.pipeline().addLast(new MyEncoder());
        //在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }
}
