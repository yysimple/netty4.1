package com.simple.coder;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-15 18:39
 **/
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        //
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}

