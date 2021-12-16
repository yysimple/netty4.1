package com.simple.init;

import com.simple.handler.ServerReceiveMsgHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 10:45
 **/
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        System.out.println("有一客户端链接到本服务端：");
        System.out.println("客户端地址:" + channel.remoteAddress().getHostString());
        System.out.println("客户端端口:" + channel.remoteAddress().getPort());

        // 想ChannelPipeline中注册handler事件，这里的关系是，handler里面会存在一个ChannelHandlerContext参数
        // 这个参数就是用来绑定 ChannelPipeline和对应handler的;
        // 然后在 pipeline中支持很多api去添加 handler，所以这也表明 handler有个特性，那就是责任链形式
        // 由于存在 头尾 概念，所以这也能表示其是存在方向的，一般定义 出站数据从尾部开始，入站顺序从头部开始？？怎么区分出站入站？
        // 入站：ChannelInboundHandlerAdapter：一般继承这个类的子类handler就能拿到入站信息，下面传进来的消息直接在readChannel中就能拿到
        // 出站：ChannelOutboundHandlerAdapter：这个一般都是出站信息需要去继承，然后添加到客户端的pipeline中，然后是客户端发送的消息会被服务端接收到
        // 那这里就还会有另外的问题？？？那是不是编解码就发生在这里？？怎么实现？？怎么保证数据安全性？？
        // 上面也是了，责任链，所以我们也能想到 pipeline中肯定支持动态的增删 handler，所以所以，是不是加加加加加加加handler就可以？？
        // 答案是的，不过安全协议必须得放在第一个handler处，之后就是安全协议，这里又又又有一个问题？？
        // http协议怎么实现https协议呢？就是根据前面是否加上了安全协议 SslContext ，就可以使用https协议，但是需要浏览器支持
        // 当然这里 handler channelPipeline channelHandlerContext三者之间得关系还有挺多讨论点得！！
        // 这个例子就是将自己定义的 handler 加入到 pipeline 中
        channel.pipeline().addFirst(new ServerReceiveMsgHandler());
    }
}
