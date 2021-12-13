package com.simple.nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-13 14:15
 **/
public class BufferSliceTest {

    /**
     * 在 NIO 中，除了可以分配或者包装一个缓冲区对象外，还可以根据现有的缓冲区对象
     * 来创建一个子缓冲区，即在现有缓冲区上切出一片来作为一个新的缓冲区，但现有的
     * 缓冲区与创建的子缓冲区在底层数组层面上是数据共享的，也就是说，子缓冲区相当
     * 于是现有缓冲区的一个视图窗口。调用 slice()方法可以创建一个子缓冲区。
     * <p>
     * 子缓存区，这里其实也就是一个缓存分区而已。类似于字符串的截取；
     * 这里也就只是操作了一个区间的数据，或者换种说法就是，只选取了某个区间数据来进行加工
     */
    @Test
    public void testSlice() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 缓冲区中的数据 0-9
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }
        // 创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();
        // 改变子缓冲区的内容
        for (int i = 0; i < slice.capacity(); ++i) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i, b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }

    /**
     * 如果尝试修改只读缓冲区的内容，则会报 ReadOnlyBufferException 异常。只读缓冲
     * 区对于保护数据很有用。在将缓冲区传递给某个 对象的方法时，无法知道这个方法是
     * 否会修改缓冲区中的数据。创建一个只读的缓冲区可以保证该缓冲区不会被修改。只
     * 可以把常规缓冲区转换为只读缓冲区，而不能将只读的缓冲区转换为可写的缓冲区。
     */
    @Test
    public void testOnlyReadBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 缓冲区中的数据 0-9
        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }
        // 创建只读缓冲区
        ByteBuffer readonly = buffer.asReadOnlyBuffer();
        // 改变原缓冲区的内容
        for (int i = 0; i < buffer.capacity(); ++i) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }
        for (int i = 0; i < buffer.capacity(); ++i) {
            byte b = buffer.get(i);
            b *= 10;
            // 尝试修改只读的缓存区，会报出这个异常：java.nio.ReadOnlyBufferException
//            readonly.put(i, b);
        }
        readonly.position(0);
        readonly.limit(buffer.capacity());
        // 只读缓冲区的内容也随之改变
        while (readonly.remaining() > 0) {
            System.out.println(readonly.get());
        }
    }
}
