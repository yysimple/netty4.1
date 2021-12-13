package com.simple.nio.client;

import java.io.IOException;
import java.net.Socket;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 12:12
 **/
public class ClientTest {
    public static void main(String[] args) throws IOException {
        while (true) {
            Socket socket = new Socket("127.0.0.1", 1234);
            System.out.println(socket.getInetAddress());
        }
    }
}
