package com.simple.server;

import com.simple.entity.MsgAgreement;
import com.simple.entity.UserChannelInfo;
import com.simple.service.ExtServerService;
import com.simple.util.CacheUtil;
import com.simple.util.MsgUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 22:12
 **/
public class NettyServerChannelHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(NettyServerChannelHandler.class);

    private final ExtServerService extServerService;

    public NettyServerChannelHandler(ExtServerService extServerService) {
        this.extServerService = extServerService;
    }

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("有一客户端链接到本服务端。channelId：" + channel.id());
        System.out.println("远程IP:" + channel.remoteAddress().getHostString());
        System.out.println("远程Port:" + channel.remoteAddress().getPort());

        //保存用户信息
        UserChannelInfo userChannelInfo = new UserChannelInfo(channel.localAddress().getHostString(), channel.localAddress().getPort(), channel.id().toString(), new Date());
        // 将此次连接的信息存入到redis中
        extServerService.getRedisUtil().pushObj(userChannelInfo);
        // 这里是在缓存里面再存一份
        CacheUtil.cacheChannel.put(channel.id().toString(), channel);
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " - " + channel.remoteAddress().getHostString() + "\r\n";
        ctx.writeAndFlush(MsgUtil.buildMsg(channel.id().toString(), str));

    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开链接" + ctx.channel().localAddress().toString());
        // 客户端下线是，移除缓存和redis中的记录
        extServerService.getRedisUtil().remove(ctx.channel().id().toString());
        CacheUtil.cacheChannel.remove(ctx.channel().id().toString(), ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsgJsonStr) throws Exception {
        // 接收msg消息
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss -").format(new Date()) + " 接收到消息内容：" + objMsgJsonStr);
        // 拿到客户端发送过来的消息，并解析
        MsgAgreement msgAgreement = MsgUtil.json2Obj(objMsgJsonStr.toString());
        // 拿到当前客户端的id
        String toChannelId = msgAgreement.getToChannelId();
        // 判断接收消息用户是否在本服务端
        Channel channel = CacheUtil.cacheChannel.get(toChannelId);
        if (null != channel) {
            channel.writeAndFlush(MsgUtil.obj2Json(msgAgreement));
            return;
        }
        // 如果为NULL则接收消息的用户不在本服务端，需要push消息给全局
        logger.info("接收消息的用户不在本服务端，PUSH！");
        extServerService.push(msgAgreement);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        extServerService.getRedisUtil().remove(ctx.channel().id().toString());
        CacheUtil.cacheChannel.remove(ctx.channel().id().toString(), ctx.channel());
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}
