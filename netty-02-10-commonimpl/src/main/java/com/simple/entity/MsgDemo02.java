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
public class MsgDemo02 extends Packet {

    private String channelId;
    private String demo02;

    public MsgDemo02(String channelId, String demo02) {
        this.channelId = channelId;
        this.demo02 = demo02;
    }

    @Override
    public Byte getCommand() {
        return Command.Demo02;
    }
}
