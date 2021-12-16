package com.simple.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 14:03
 **/
public class ServerReceiveMsgHandler extends ChannelInboundHandlerAdapter {

    /**
     * 既然这里又使用到这个回调方法了，那就得说说Channel的生命周期了；其实也就是状态：
     * - ChannelUnregistered：Channel 已经被创建，但还未注册到 EventLoop
     * - ChannelRegistered：Channel 已经被注册到了 EventLoop
     * - ChannelActive：Channel 处于活动状态（已经连接到它的远程节点）。它现在可以接收和发送数据了
     * - ChannelInactive：Channel 没有连接到远程节点
     * <p>
     * 这些channel的状态其实是由 ChannelPipeline进行监听的，然后会通过这里几个回调函数传给handler去处理，这里是每个handler
     * 只要在“责任链”上的handler都能接收到接收到这些状态；那么又来了，那ChannelHandler有没有生命周期函数呢？？？有的
     * <p>
     * --------------------------
     * <p>
     * ChannelHandler相关：
     * - handlerAdded：当把 ChannelHandler 添加到 ChannelPipeline 中时被调用
     * - handlerRemoved：当从 ChannelPipeline 中移除 ChannelHandler 时被调用
     * - exceptionCaught：当处理过程中在 ChannelPipeline 中有错误产生时被调用
     * <p>
     * ------------------------------
     * 那对应的 ChannelPipeline也应该由很多内置的这种方法？？是的，但是他不应该出现在handler里面，那么关系就乱了。
     * handler一般都是由pipeline来维护的；一般都放在 ChannelInitializer.initChannel里面去使用；
     * <p>
     * ------------------------------
     * <p>
     * 那对应的 ChannelHandlerContext理论上也有很多API咯？？当然是咯，而且他是维护 ChannelPipeline和ChannelHandler的一个
     * 中间关键“人物”，所以他的功能很多都是触发handler的，比如：
     * - fireChannelActive：触发对下一个 ChannelInboundHandler 上的channelActive()方法（已连接）的调用
     * - pipeline：返回这个实例所关联的 ChannelPipeline
     * - write：通过这个实例写入消息并经过 ChannelPipeline
     * - writeAndFlush：通过这个实例写入并冲刷消息并经过 ChannelPipeline
     * <p>
     * 所以咯，我们在使用 ChannelHandlerContext的相关API的时候也可以有以下结论：
     *  ChannelHandlerContext 和 ChannelHandler 之间的关联（绑定）是永远不会改变的，所以缓存对它的引用是安全的；
     *  相对于其他类的同名方法，ChannelHandler Context的方法将产生更短的事件流，应该尽可能地利用这个特性来获得最大的性能
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        // 这里就是只要通道连接成功，且通道保持连接状态，这里就会有反馈
        System.out.println("我活着");
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + channel.localAddress().getHostString() + "\r\n";
        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
        buf.writeBytes(str.getBytes("GBK"));
        /**
         * 写消息有几种不同的api，来看看：
         * 首先对比的是 ctx 中的 write/writeAndFlush
         *  - write：将消息写入 Channel。这将调用 ChannelPipeline
         *  中的下一个 ChannelOutboundHandler的write(ChannelHandlerContext, Object msg, ChannelPromise)方法。
         *  注意：这并不会将消息写入底层的 Socket，而只会将它放入队列中。
         *  要将它写入 Socket，需要调用 flush()或者 writeAndFlush()方法，其实就是消息还在队列中，并没有发送出去
         *  而此时使用 write 客户端是接受不到消息的；
         *  - flush：冲刷Channel所有挂起的写入。这将调用ChannelPipeline中的下一个ChannelOutboundHandler 的 flush(ChannelHandlerContext)方法
         *
         * 所以 writeAndFlush 方法是两者的结合，可以直接将消息发送给客户端
         *
         * ChannelHandlerContext中的 writeAndFlush 指的是从当前handler直接将消息发送出去；
         * 而ServerChannel中的 writeAndFlush 是从pipeline的最尾部开始传输数据的；
         * 但是这里一般通信来说，最好还是使用ctx的，使用channel的可能会出现死循环：
         * 一般：客户端往服务端传数据 叫 入站，服务端往客户端传数据叫 出站，那么这两种对应的方式是什么呢？？
         * 入站：数据从 ChannelPipeline的左到右（头到尾）
         * 出站：数据从 ChannelPipeline的右到左（尾到头）
         * --> 1        2       3
         *         6        5       4<--
         * 而使用 ChannelHandlerContext 的时候，就是从但比如此时handler是6，那么只需要把数据直接交给pipeline在到socket就行
         * 而使用 ServerChannel 的时候，就是从尾部开始，先从4开始，如果4调用的也是这个方法，又把自己送到尾部，就成了死循环了；
         *
         * 所以还是用 ctx的更好；
         *
         */
        ctx.writeAndFlush(buf);
//        channel.writeAndFlush(buf);
//        ctx.write(buf);
//        channel.write(buf);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().remoteAddress().toString());
    }

    /**
     * 这里有个小细节需要注意，因为我们在init那里配置了处理换行符的handler，如果没有识别到换行符（win上回车键）
     * 那么消息就会一直堆积在pipeline的缓存中，是刷不出去了，所以要注意发消息最后面带上 回车键
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss - ").format(new Date()) + " 接收到消息：" + msg);
        // 通知客户端链消息发送成功
        String str = "服务端收到：" + new Date() + " " + msg + "\r\n";
        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
        buf.writeBytes(str.getBytes("GBK"));
        ctx.writeAndFlush(buf);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}
