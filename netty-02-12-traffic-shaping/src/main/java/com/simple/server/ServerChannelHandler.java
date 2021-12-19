package com.simple.server;

import com.simple.server.common.NettyServerCommonHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 14:32
 **/
public class ServerChannelHandler extends NettyServerCommonHandler {

    @Override
    protected void sendData(ChannelHandlerContext ctx) {
        sentFlag = true;
        ctx.writeAndFlush("111111111122222222223333333333\r\n", getChannelProgressivePromise(ctx, new Consumer<ChannelProgressiveFuture>() {
            @Override
            public void accept(ChannelProgressiveFuture channelProgressiveFuture) {
                if (ctx.channel().isWritable() && !sentFlag) {
                    sendData(ctx);
                }
            }
        }));
    }
}
