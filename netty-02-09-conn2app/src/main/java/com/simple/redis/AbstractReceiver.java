package com.simple.redis;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 22:09
 **/
public abstract class AbstractReceiver {
    /**
     * 接受消息
     *
     * @param message
     */
    public abstract void receiveMessage(Object message);
}
