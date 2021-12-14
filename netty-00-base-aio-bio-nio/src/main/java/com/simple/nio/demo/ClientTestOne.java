package com.simple.nio.demo;

import java.io.IOException;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 10:29
 **/
public class ClientTestOne {
    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();
        chatClient.startClient("张三");
    }
}
