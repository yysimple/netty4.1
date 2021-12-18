package com.simple.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 13:14
 **/
public class ClientChannelFutureListener implements ChannelFutureListener {

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        // 这里会有是否成功的判断
        if (channelFuture.isSuccess()) {
            System.out.println("这里代表连接成功，不用处理");
            return;
        }
        final EventLoop loop = channelFuture.channel().eventLoop();
        // 连接失败的情况下，每隔一秒重新发起连接
        loop.schedule(() -> {
            try {
                new NettyClient().connect("127.0.0.1", 9999);
                System.out.println("客户端连接失败的时候，重新发起连接...");
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("重新发起连接失败...");
            }
        }, 1L, TimeUnit.SECONDS);
    }
}
