package com.simple.nio.demo;

import java.io.IOException;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 10:29
 **/
public class ClientTestTwo {
    public static void main(String[] args) throws IOException {
        new ChatClient().startClient("李四");
    }
}
