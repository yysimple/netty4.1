package simple.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 14:03
 **/
public class ServerReceiveMsgHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 这里就是只要通道连接成功，且通道保持连接状态，这里就会有反馈
        System.out.println("我活着");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 如果（两次）输入是这样的：
         * 我就来试试我能不能发送消息，我要输入换行符了
         *
         * 输入了
         *
         * 那么接收到的（注意第二个消息，我们能发现换行之后的消息，能在下一次发送接收到）：
         * 2021-12-16 15:33:27 -  接收到消息：我就来试试我能不能发送消息，我要输入换行符了
         * 2021-12-16 15:33:27 -  接收到消息：
         * 2021-12-16 15:33:31 -  接收到消息：输入了我就来试试我能不能发送消息，我要输入换行符了
         * 2021-12-16 15:33:31 -  接收到消息：
         *
         */
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss - ").format(new Date()) + " 接收到消息：" + msg);
    }
}
