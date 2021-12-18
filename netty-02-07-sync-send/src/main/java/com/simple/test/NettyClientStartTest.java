package com.simple.test;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.simple.client.NettyClient;
import com.simple.future.SyncWrite;
import com.simple.msg.Request;
import com.simple.msg.Response;
import io.netty.channel.ChannelFuture;

import java.util.Objects;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 11:40
 **/
public class NettyClientStartTest {
    private static ChannelFuture future;

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        // 启动客户端去发送给请求
        Thread thread = new Thread(client);
        thread.start();

        while (true) {
            try {
                // 获取future，线程有等待处理时间
                if (null == future) {
                    future = client.getFuture();
                    Thread.sleep(500);
                    continue;
                }
                //构建发送参数
                Request request = new Request();
                request.setResult("调用getMoney接口");
                SyncWrite s = new SyncWrite();
                Response response = s.writeAndSync(future.channel(), request, 1000);
                System.out.println("调用结果：" + JSON.toJSON(response));
                Thread.sleep(1000);
                if (!Objects.isNull(response) && !StrUtil.hasBlank(response.getRequestId())) {
                    System.out.println("此次调用结束...");
                    thread.interrupt();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
