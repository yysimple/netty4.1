package com.simple.entity.protocol;


import com.simple.entity.MsgDemo01;
import com.simple.entity.MsgDemo02;
import com.simple.entity.MsgDemo03;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 86159
 */
public class PacketClazzMap {

    public final static Map<Byte, Class<? extends Packet>> packetTypeMap = new ConcurrentHashMap<>();

    static {
        packetTypeMap.put(Command.Demo01, MsgDemo01.class);
        packetTypeMap.put(Command.Demo02, MsgDemo02.class);
        packetTypeMap.put(Command.Demo03, MsgDemo03.class);
    }

}
