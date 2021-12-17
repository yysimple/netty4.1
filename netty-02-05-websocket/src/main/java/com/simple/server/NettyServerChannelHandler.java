package com.simple.server;

import com.alibaba.fastjson.JSON;
import com.simple.entity.ClientMsgProtocol;
import com.simple.enums.SendTypeEnum;
import com.simple.util.BatchUser;
import com.simple.util.MsgUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 10:35
 **/
public class NettyServerChannelHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(NettyServerChannelHandler.class);

    private WebSocketServerHandshaker handshaker;

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        logger.info("有一客户端链接到本服务端");
        logger.info("客户端IP:{}", channel.remoteAddress().getHostString());
        logger.info("客户端Port:{}", channel.remoteAddress().getPort());
        BatchUser.USERS_YYDS.add(ctx.channel());
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端断开链接{}", ctx.channel().localAddress().toString());
        // 退出群聊
        BatchUser.USERS_YYDS.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 这里是判断是否是http请求
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            // 连接不成功的状态处理
            if (!httpRequest.decoderResult().isSuccess()) {
                DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                // 连接未成功，返回应答给客户端
                if (httpResponse.status().code() != HttpStatus.OK.value()) {
                    ByteBuf buf = Unpooled.copiedBuffer(httpResponse.status().toString(), CharsetUtil.UTF_8);
                    httpResponse.content().writeBytes(buf);
                    buf.release();
                }
                // 如果是非Keep-Alive，关闭连接
                ChannelFuture f = ctx.channel().writeAndFlush(httpResponse);
                if (httpResponse.status().code() != HttpStatus.OK.value()) {
                    // 连接断开后，关闭该通道
                    f.addListener(ChannelFutureListener.CLOSE);
                }
                return;
            }
            // 升级成ws协议
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel() + "/websocket", null, false);
            handshaker = wsFactory.newHandshaker(httpRequest);
            if (null == handshaker) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), httpRequest);
            }
            return;
        }

        // 判断是否是ws请求
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
            // 如果是关闭请求，关闭此次通道，并将剩余数据清空
            if (webSocketFrame instanceof CloseWebSocketFrame) {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) webSocketFrame.retain());
                return;
            }
            // 如果是ws的ping请求，那么对应的就会响应 PongWebSocketFrame
            // 这里可以类似理解为，request 和 response
            if (webSocketFrame instanceof PingWebSocketFrame) {
                ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
                return;
            }
            // 只支持文本格式，不支持二进制消息：TextWebSocketFrame 是我们唯一真正需要处理的帧类型。为了符合 WebSocket RFC
            if (!(webSocketFrame instanceof TextWebSocketFrame)) {
                throw new Exception("仅支持文本格式");
            }
            // 拿到客户端的请求消息
            String request = ((TextWebSocketFrame) webSocketFrame).text();
            System.out.println("服务端收到：" + request);

            // 解析成对应的数据结构
            ClientMsgProtocol clientMsgProtocol = JSON.parseObject(request, ClientMsgProtocol.class);
            // 链接信息
            if (SendTypeEnum.LINK_SEND.getIndex() == clientMsgProtocol.getType()) {
                // 发送一个channelId，返回链接信息
                ctx.channel().writeAndFlush(MsgUtil.buildMsgOwner(ctx.channel().id().toString()));
                return;
            }
            // 群发消息
            if (SendTypeEnum.BATCH_SEND.getIndex() == clientMsgProtocol.getType()) {
                // 这里就会把客户端A的消息群发出去，让所有的客户端都接收到
                TextWebSocketFrame textWebSocketFrame = MsgUtil.buildMsgAll(ctx.channel().id().toString(), clientMsgProtocol.getMsgInfo());
                BatchUser.USERS_YYDS.writeAndFlush(textWebSocketFrame);
            }

        }

    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.info("异常信息：\r\n" + cause.getMessage());
    }
}
