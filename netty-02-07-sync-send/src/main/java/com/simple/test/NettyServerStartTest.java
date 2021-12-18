package com.simple.test;

import com.simple.server.NettyServer;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 11:40
 **/
public class NettyServerStartTest {
    public static void main(String[] args) {
        new Thread(new NettyServer()).start();
        System.out.println("服务端一直在线！！");
    }
}
