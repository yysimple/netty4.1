package com.simple.client;

import com.simple.future.SyncWriteFuture;
import com.simple.future.SyncWriteMap;
import com.simple.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 11:04
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        Response msg = (Response) obj;
        String requestId = msg.getRequestId();
        // 从缓存中拿到此次请求信息
        SyncWriteFuture future = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        if (future != null) {
            // 设置响应信息，此处会释放资源，拿到 get请求拿到结果
            future.setResponse(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
