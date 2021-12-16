package com.simple.coderutil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-16 10:21
 **/
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    /**
     * 如果你正在使用的是 JDK 6 或者更早的版本，那么你需要将 JZlib（www.jcraft.com/jzlib/）添加到
     * CLASSPATH 中以支持压缩功能。
     * 对于 Maven，请添加以下依赖项
     * <dependency>
     * <groupId>com.jcraft</groupId>
     * <artifactId>jzlib</artifactId>
     * <version>1.1.3</version>
     * </dependency>
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient) {
            // 如果是客户端，则添加 HttpClientCodec
            pipeline.addLast("codec", new HttpClientCodec());
            // 如果是客户端，则添加HttpContentDecompressor 以处理来自服务器的压缩内容
            pipeline.addLast("decompressor", new HttpContentDecompressor());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
            pipeline.addLast("compressor", new HttpContentCompressor());
        }
    }
}
