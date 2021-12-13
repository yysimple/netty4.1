package com.simple.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 功能描述: 文件管道
 *
 * @author: WuChengXing
 * @create: 2021-11-28 12:07
 **/
public class FileChannelTest {

    /**
     * 这里的大致步骤：
     * 1. 通过IO拿到文件信息
     * 2. 通过文件信息拿到”通道“
     * 3. 初始化一个用来传输数据的”缓存“
     * 4. 把”通道“里面的数据读取到”缓存“中 -- 该步骤之下：
     * position = dataSize（数据大小）；limit = capacity = bufferSzie（缓存定义的大小） = 20
     * -----
     * 5. 切换模式，使其可以读，此时:
     * position = limit = (dataSize > bufferSzie ? bufferSzie : dataSize),capacity = 20
     *
     * 6. 循环读完”一个大小的缓存“之后，调用clear清空”缓存“：
     * position = 0（数据大小）；limit = capacity = bufferSzie（缓存定义的大小） = 20
     *
     * 7. 一直循环读，知道没有数据为止；最后关闭文件流
     *
     *
     * @throws IOException
     */
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
