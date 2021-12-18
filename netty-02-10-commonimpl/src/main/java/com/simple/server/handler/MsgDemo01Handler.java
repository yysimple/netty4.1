package com.simple.server.handler;

import com.simple.entity.MsgDemo01;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 86159
 */
public class MsgDemo01Handler extends SimpleChannelInboundHandler<MsgDemo01> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgDemo01 msg) throws Exception {
        System.out.println("\r\n> msg handler ing ...");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收消息的处理器：" + this.getClass().getName());
        System.out.println("channelId：" + msg.getChannelId());
        System.out.println("消息内容：" + msg.getDemo01());
    }

}
