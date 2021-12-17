package com.simple.test;

import com.simple.client.NettyClient;
import com.simple.entity.FileTransferProtocol;
import com.simple.util.MsgUtil;
import io.netty.channel.ChannelFuture;

import java.io.File;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 16:27
 **/
public class FileUploadTest {
    public static void main(String[] args) {

        //启动客户端
        ChannelFuture channelFuture = new NettyClient().connect("127.0.0.1", 9999);

        //文件信息{文件大于1024kb方便测试断点续传}
        File file = new File("E:\\study-resource\\download\\书籍-pdf\\java\\netty\\Netty4专题.zip");
        FileTransferProtocol fileTransferProtocol = MsgUtil.buildRequestTransferFile(file.getAbsolutePath(), file.getName(), file.length());

        //发送信息；请求传输文件
        channelFuture.channel().writeAndFlush(fileTransferProtocol);

    }

}
