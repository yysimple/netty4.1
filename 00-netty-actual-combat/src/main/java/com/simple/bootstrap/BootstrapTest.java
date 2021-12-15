package com.simple.bootstrap;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.Bootstrap;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 12:21
 **/
public class BootstrapTest {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        BootstrapServer bootstrapServer = new BootstrapServer();
    }
}
