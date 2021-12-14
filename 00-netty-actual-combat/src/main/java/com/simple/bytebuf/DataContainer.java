package com.simple.bytebuf;

import io.netty.buffer.*;
import io.netty.channel.PreferHeapByteBufAllocator;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-14 15:05
 **/
public class DataContainer {
    public static void main(String[] args) {
        // 存储数据的容器，也即缓存区
        ByteBuf byteBuf = null;
        // 这个是包装了一层 byteBuf， 另外提供了一些其他的操作
        ByteBufHolder holder = null;
        // 这个是用来分配ByteBuf的空间的，可以拿到一个 ByteBuf对象；这个是池化分配
        ByteBufAllocator allocator = new PooledByteBufAllocator();
        ByteBuf allocByteBuf = allocator.buffer(10);
        // 返回一个未池化的基于堆内存存储的 ByteBuf
        ByteBuf heapUnpooledByteBuf = Unpooled.buffer(10);

    }

}
