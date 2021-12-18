package com.simple.future;

import com.simple.msg.Request;
import com.simple.msg.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 11:10
 **/
public class SyncWrite {

    public Response writeAndSync(final Channel channel, final Request request, final long timeout) throws Exception {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout <= 0");
        }
        // 为该次请求生成一个唯一id
        String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        // 记录此次请求id，并放入到缓存中
        WriteFuture<Response> future = new SyncWriteFuture(request.getRequestId());
        SyncWriteMap.syncKey.put(request.getRequestId(), future);

        Response response = doWriteAndSync(channel, request, timeout, future);
        // 拿到响应值后，此前请求结束，那么可以移除此次请求
        SyncWriteMap.syncKey.remove(request.getRequestId());
        return response;
    }

    private Response doWriteAndSync(final Channel channel, final Request request, final long timeout, final WriteFuture<Response> writeFuture) throws Exception {
        // 这里就不用lambda了，这里就是在channel写出一条数据之后，可以为其添加一个监听时间，也即操作完之后的一个回调方法
        // 每个 Netty 的出站 I/O 操作都将返回一个 ChannelFuture
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // 设置此次请求的状态
                writeFuture.setWriteResult(future.isSuccess());
                // 如果失败，此次的原因
                writeFuture.setCause(future.cause());
                // 失败移除此次请求
                if (!writeFuture.isWriteSuccess()) {
                    SyncWriteMap.syncKey.remove(writeFuture.requestId());
                }
            }
        });

        // 请求完成之后，这里会去模拟等待，get的时候是无法去拿到资源的，这里设置一个等待事件
        Response response = writeFuture.get(timeout, TimeUnit.MILLISECONDS);
        if (response == null) {
            // 已经超时则抛出异常
            if (writeFuture.isTimeout()) {
                throw new TimeoutException();
            } else {
                // write exception
                throw new Exception(writeFuture.cause());
            }
        }
        // 否则返回响应，此次类似 feign的调用，等到请求，过了一段时间还没有拿到结果，则抛出超时异常，否则成功
        return response;
    }
}
