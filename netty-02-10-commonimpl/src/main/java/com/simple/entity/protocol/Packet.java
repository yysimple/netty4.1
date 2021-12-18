package com.simple.entity.protocol;

/**
 * @author 86159
 */
public abstract class Packet {

    /**
     * 获取协议指令
     * @return 返回指令值
     */
    public abstract Byte getCommand();

}
