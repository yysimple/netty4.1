package com.simple.nio.channel.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 17:21
 **/
public class FileLockTest {
    public static void main(String[] args) throws IOException {
        String input = "测试信息";
        System.out.println("输入 :" + input);
        ByteBuffer buf = ByteBuffer.wrap(input.getBytes());
        String fp = "D:\\1.txt";
        Path pt = Paths.get(fp);
        FileChannel channel = FileChannel.open(pt, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        channel.position(channel.size() - 1);
        // 获得锁方法一：lock()，阻塞方法，当文件锁不可用时，当前进程会被挂起
        // lock = channel.lock();// 无参 lock()为独占锁
        // lock = channel.lock(0L, Long.MAX_VALUE, true);//有参 lock()为共享锁，有写操作会报异常
        // 获得锁方法二：trylock()，非阻塞的方法，当文件锁不可用时，tryLock()会得到 null 值
        FileLock lock = channel.tryLock(0, Long.MAX_VALUE, false);
        System.out.println("共享锁 shared: " + lock.isShared());
        channel.write(buf);
        // Releases the Lock
        channel.close();
        System.out.println("写操作完成.");
        //读取数据
        readPrint(fp);
    }

    public static void readPrint(String path) throws IOException {
        FileReader filereader = new FileReader(path);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String tr = bufferedreader.readLine();
        System.out.println("读取内容: ");
        while (tr != null) {
            System.out.println(" " + tr);
            tr = bufferedreader.readLine();
        }
        filereader.close();
        bufferedreader.close();
    }
}

