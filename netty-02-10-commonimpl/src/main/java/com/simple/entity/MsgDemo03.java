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
public class MsgDemo03 extends Packet {

    private String channelId;
    private String demo03;

    public MsgDemo03(String channelId, String demo03) {
        this.channelId = channelId;
        this.demo03 = demo03;
    }

    @Override
    public Byte getCommand() {
        return Command.Demo03;
    }
}
