package com.simple.nio.pipe;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 17:09
 **/
public class PipeTest {

    @Test
    public void testPipe() throws IOException {
        // 1、获取通道
        Pipe pipe = Pipe.open();
        // 2、获取 sink 管道，用来传送数据
        Pipe.SinkChannel sinkChannel = pipe.sink();
        // 3、申请一定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("测试信息".getBytes());
        byteBuffer.flip();
        // 4、sink 发送数据
        sinkChannel.write(byteBuffer);

        /**
         * -----------------------------------
         */

        // 5、创建接收 pipe 数据的 source 管道
        Pipe.SourceChannel sourceChannel = pipe.source();
        // 6、接收数据，并保存到缓冲区中
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
        int length = sourceChannel.read(byteBuffer2);
        System.out.println(new String(byteBuffer2.array(), 0, length));
        sourceChannel.close();
        sinkChannel.close();
    }

    @Test
    @SuppressWarnings("all")
    public void multiTaskPipeTest() throws IOException {
        // 共用管道
        Pipe pipe = Pipe.open();
        // 通过sink发送
        new Thread(() -> {
            Pipe.SinkChannel sink = pipe.sink();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("测试信息".getBytes());
            byteBuffer.flip();
            try {
                sink.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // 通过source接收数据
        new Thread(() -> {
            Pipe.SourceChannel sourceChannel = pipe.source();
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
            int length = 0;
            try {
                length = sourceChannel.read(byteBuffer2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(byteBuffer2.array(), 0, length));
        }).start();
    }
}
