package com.simple.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能描述: 文件管道
 *
 * @author: WuChengXing
 * @create: 2021-11-28 12:07
 **/
public class FileChannelTest {

    @Test
    public void fileChannelReadTest() throws IOException {
        // 1. 借助io流拿到文件
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\1.txt", "rw");
        // 2. 拿到NIO的文件管道
        FileChannel channel = randomAccessFile.getChannel();
        // 3. 分配内存，这里分配的是堆内存(https://www.wolai.com/6YfMhBTNbDy1zbwkjgTZ8a)
        ByteBuffer buffer = ByteBuffer.allocate(20);
        // 4. 从管道里面读取数据，并写道缓存中
        int read = channel.read(buffer);
        // 数据没有读取完，继续读取
        while (read != -1) {
            // 这里读取的数据其实是buffer的大小，如果数据大于buffer，那么这里就是20，小于，那么数据是多少就是多少
            System.out.println("读取的数据：" + read);
            // 反转缓存空间模式，将写改成读，其实这里涉及到三个概念（这里应该也只会从写切换成读）
            buffer.flip();
            // 防止有未读完的数据，继续读,缓存中还存在数据
            while (buffer.hasRemaining()) {
                System.out.println("继续读剩余的数据：" + (char) buffer.get());
            }
            // 清空缓存，这里也类似切换，模式，数据为空，时刻准备接收数据
            buffer.clear();
            // 缓存中还存在数据，就一直读
            read = channel.read(buffer);
        }
        randomAccessFile.close();
        System.out.println("数据读取完毕");
    }

    @Test
    public void fileChannelWriteTest() {

    }
}
