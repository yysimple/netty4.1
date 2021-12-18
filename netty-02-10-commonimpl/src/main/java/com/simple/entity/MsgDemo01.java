package com.simple.entity;

import com.simple.entity.protocol.Command;
import com.simple.entity.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86159
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MsgDemo01 extends Packet {

    private String channelId;
    private String demo01;

    public MsgDemo01(String channelId, String demo01) {
        this.channelId = channelId;
        this.demo01 = demo01;
    }

    @Override
    public Byte getCommand() {
        return Command.Demo01;
    }

}
