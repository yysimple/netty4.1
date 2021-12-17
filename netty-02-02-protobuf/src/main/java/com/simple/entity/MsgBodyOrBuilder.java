package com.simple.entity;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:15
 **/
public interface MsgBodyOrBuilder extends MessageOrBuilder {

    /**
     * <code>string channelId = 1;</code>
     */
    String getChannelId();

    /**
     * <code>string channelId = 1;</code>
     */
    ByteString getChannelIdBytes();

    /**
     * <code>string msgInfo = 2;</code>
     */
    String getMsgInfo();

    /**
     * <code>string msgInfo = 2;</code>
     */
    ByteString getMsgInfoBytes();
}
