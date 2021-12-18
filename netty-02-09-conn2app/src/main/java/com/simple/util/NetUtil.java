package com.simple.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:56
 **/
public class NetUtil {
    public static int getPort() {
        int initPort = 9999;
        while (isPortUsing(initPort)) {
            initPort++;
        }
        return initPort;
    }

    public static boolean isPortUsing(int port) {
        boolean flag = false;
        try {
            Socket socket = new Socket("localhost", port);
            socket.close();
            flag = true;
        } catch (IOException e) {

        }
        return flag;
    }

    public static String getHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(getHost());
    }
}
