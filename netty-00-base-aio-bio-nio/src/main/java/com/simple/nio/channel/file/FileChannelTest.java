package com.simple.nio.channel.file;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

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
     * <p>
     * 6. 循环读完”一个大小的缓存“之后，调用clear清空”缓存“：
     * position = 0（数据大小）；limit = capacity = bufferSzie（缓存定义的大小） = 20
     * <p>
     * 7. 一直循环读，知道没有数据为止；最后关闭文件流
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
    public void fileChannelWriteTest() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("D:\\2.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        String dataC = "我就是测试一下啊,可能乱码";
        // 这里如果缓存空间没有数据大，也即如果缓存放不下数据，则抛出异常
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        // 把数据方法缓存中 StandardCharsets.UTF_8
        buffer.put(dataC.getBytes());

        // 切换模式，现在是从缓存中获取数据
        buffer.flip();
        // 开始写，只要缓存中还有数据，就一直写
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        channel.close();
        accessFile.close();
    }

    @Test
    public void channelOtherMethodTest() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("D:\\2.txt", "rw");
        String dataC = "我就是测试一下啊,可能乱码";
        System.out.println("测试数据的长度：" + dataC.getBytes(StandardCharsets.UTF_8).length);
        FileChannel channel = accessFile.getChannel();
        long position = channel.position();
        System.out.println(position + 10);
        System.out.println("通道里面的数据大小：" + channel.size());
        ByteBuffer buffer = ByteBuffer.allocate(48);
        // 截取文件时，文件将中指定长度后面的部分将被删除,这个操作是危险的，就算你在读的时候，这个文件也会被修改
        channel.truncate(5);
        int read = channel.read(buffer);
        while (read != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println("读取出来的数据：" + (char) buffer.get());
            }
            // 用完之后一定要清空
            buffer.clear();
            read = channel.read(buffer);
        }
        channel.close();
        accessFile.close();
    }

    @Test
    public void transferFromTest() throws IOException {
        RandomAccessFile from = new RandomAccessFile("D:\\2.txt", "rw");
        FileChannel channelFrom = from.getChannel();

        // 不存在这个文件会帮你创建该文件
        RandomAccessFile to = new RandomAccessFile("D:\\3.txt", "rw");
        FileChannel channelTo = to.getChannel();

        long position = 0;
        long count = channelFrom.size();
        channelTo.transferFrom(channelFrom, position, count);

        channelFrom.close();
        channelTo.close();
        from.close();
        to.close();

    }

    @Test
    public void transferToTest() throws IOException {
        RandomAccessFile from = new RandomAccessFile("D:\\4.txt", "rw");
        FileChannel channelFrom = from.getChannel();

        // 不存在这个文件会帮你创建该文件
        RandomAccessFile to = new RandomAccessFile("D:\\3.txt", "rw");
        FileChannel channelTo = to.getChannel();

        long position = 0;
        long count = channelTo.size();
        channelTo.transferTo(position, count, channelFrom);

        channelTo.close();
        channelFrom.close();
        to.close();
        from.close();
    }

    @Test
    public void testScatteringReads() throws IOException {
        RandomAccessFile from = new RandomAccessFile("D:\\4.txt", "rw");
        FileChannel channel = from.getChannel();
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArrayR = {header, body};
        /**
         * 注意 buffer 首先被插入到数组，然后再将数组作为 channel.read() 的输入参数。
         * read()方法按照 buffer 在数组中的顺序将从 channel 中读取的数据写入到 buffer，当
         * 一个 buffer 被写满后，channel 紧接着向另一个 buffer 中写。
         * Scattering Reads 在移动下一个 buffer 前，必须填满当前的 buffer，这也意味着它
         * 不适用于动态消息(译者注：消息大小不固定)。换句话说，如果存在消息头和消息体，
         * 消息头必须完成填充（例如 128byte），Scattering Reads 才能正常工作
         */
        long read = channel.read(bufferArrayR);

        ByteBuffer[] bufferArrayW = { header, body };
        /**
         * buffers 数组是 write()方法的入参，write()方法会按照 buffer 在数组中的顺序，将数
         * 据写入到 channel，注意只有 position 和 limit 之间的数据才会被写入。因此，如果
         * 一个 buffer 的容量为 128byte，但是仅仅包含 58byte 的数据，那么这 58byte 的数
         * 据将被写入到 channel 中。因此与 Scattering Reads 相反，Gathering Writes 能较
         * 好的处理动态消息。
         */
        channel.write(bufferArrayW);
    }
}
