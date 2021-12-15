package com.simple.handler;

import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;

/**
 * 功能描述: 消息出站的异常处理
 *
 * @author: WuChengXing
 * @create: 2021-12-15 10:28
 **/
public class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        // 这里释放资源后，接下来的 handler就无法拿到消息了
        ReferenceCountUtil.release(msg);
        // 这里是将“把消息消费释放了”这个通知告诉ChannelPromise，然后在ChannelFutureListener中就能监听到
        promise.setSuccess();
        Channel channel = ctx.channel();
        ChannelFuture channelFuture = channel.write(msg);
        // ChannelFutureListener，它将打印栈跟踪信息并且随后关闭 Channel
        channelFuture.addListener(((ChannelFutureListener) f -> {
            if (!f.isSuccess()) {
                f.cause().printStackTrace();
                f.channel().close();
            }
        }));
        // 将 ChannelFutureListener 添加到即将作为参数传递给 ChannelOutboundHandler 的方法的 ChannelPromise。
        promise.addListener((ChannelFutureListener) f -> {
            if (!f.isSuccess()) {
                f.cause().printStackTrace();
                f.channel().close();
            }
        });
    }
}
