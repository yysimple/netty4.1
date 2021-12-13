package com.simple.nio.channel.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 17:39
 **/
public class AsynchronousFileChannelTest {

    @Test
    public void testAFile() {
        Path path = Paths.get("d:\\1.txt");
        try {
            AsynchronousFileChannel fileChannel =
                    AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * （1）创建了一个 AsynchronousFileChannel，
     * （2）创建一个 ByteBuffer，它被传递给 read()方法作为参数，以及一个 0 的位置。
     * （3）在调用 read()之后，循环，直到返回的 isDone()方法返回 true。
     * （4）读取操作完成后，数据读取到 ByteBuffer 中，然后打印到 System.out 中。
     */
    @Test
    public void testReadByFuture() {
        Path path = Paths.get("D:\\3.txt");
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        Future<Integer> operation = fileChannel.read(buffer, position);
        while (!operation.isDone()) {
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            System.out.println(new String(data));
            buffer.clear();
        }
    }

    /**
     * （1）读取操作完成，将调用 CompletionHandler 的 completed()方法。
     * （2）对于 completed()方法的参数传递一个整数，它告诉我们读取了多少字节，以及
     * 传递给 read()方法的“附件”。“附件”是 read()方法的第三个参数。在本代码中，
     * 它是 ByteBuffer，数据也被读取。
     * （3）如果读取操作失败，则将调用 CompletionHandler
     */
    @Test
    public void testReadHandler() {
        Path path = Paths.get("d:\\1.txt");
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result = " + result);
                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
            }
        });
    }

    /**
     * 首先，AsynchronousFileChannel 以写模式打开。然后创建一个 ByteBuffer，并将
     * 一些数据写入其中。然后，ByteBuffer 中的数据被写入到文件中。最后，示例检查返
     * 回的 Future，以查看写操作完成时的情况。
     * 注意，文件必须已经存在。如果该文件不存在，那么 write()方法将抛出一个
     * java.nio.file.NoSuchFileException
     */
    @Test
    public void testWriteFuture() {
        Path path = Paths.get("d:\\4.txt");
        AsynchronousFileChannel fileChannel = null;
        try {
            // 这里当文件不存在的时候，会抛出异常 ： java.nio.file.NoSuchFileException: d:\5.txt
            fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        buffer.put("test future write data".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        Future<Integer> operation = fileChannel.write(buffer, position);
        buffer.clear();
        while (!operation.isDone()) {
        }
        System.out.println("Write over");
    }

    /**
     * 当写操作完成时，将会调用 CompletionHandler 的 completed()方法。如果写失败，
     * 则会调用 failed()方法
     */
    @Test
    public void testWriteByHandler() {
        Path path = Paths.get("d:\\4.txt");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        buffer.put("test handler write data".getBytes());
        buffer.flip();
        fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Write failed");
                exc.printStackTrace();
            }
        });
    }
}
