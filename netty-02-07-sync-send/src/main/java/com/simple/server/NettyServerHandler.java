package com.simple.server;

import com.simple.msg.Request;
import com.simple.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 11:33
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        // 客户端发送请求
        Request msg = (Request) obj;
        // 反馈
        Response response = new Response();
        response.setRequestId(msg.getRequestId());
        response.setParam(msg.getResult() + " 请求成功，我现在给数据给你");
        ctx.writeAndFlush(response);
        // 释放消息，不在往下一个handler中传
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 读取完成的时候，会将pipeline中的消息冲刷出去
        ctx.flush();
    }


}
