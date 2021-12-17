package com.simple.entity;

import com.google.protobuf.*;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-17 13:18
 **/
public final class MsgInfo {
    private MsgInfo() {
    }

    public static void registerAllExtensions(
            ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            ExtensionRegistry registry) {
        registerAllExtensions(
                (ExtensionRegistryLite) registry);
    }

    static final Descriptors.Descriptor internal_static_com_simple_netty_protobuf_entity_MsgBody_descriptor;
    static final GeneratedMessageV3.FieldAccessorTable internal_static_com_simple_netty_protobuf_entity_MsgBody_fieldAccessorTable;

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    private static Descriptors.FileDescriptor descriptor;

    static {
        java.lang.String[] descriptorData = {
                "\n\rMsgInfo.proto\022\035org.itstack.demo.netty." +
                        "domain\"-\n\007MsgBody\022\021\n\tchannelId\030\001 \001(\t\022\017\n\007" +
                        "msgInfo\030\002 \001(\tB*\n\035org.itstack.demo.netty." +
                        "domainB\007MsgInfoP\001b\006proto3"
        };
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    @Override
                    public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{}, assigner);
        internal_static_com_simple_netty_protobuf_entity_MsgBody_descriptor = getDescriptor().getMessageTypes().get(0);

        internal_static_com_simple_netty_protobuf_entity_MsgBody_fieldAccessorTable
                = new GeneratedMessageV3.FieldAccessorTable(internal_static_com_simple_netty_protobuf_entity_MsgBody_descriptor,
                new String[]{"ChannelId", "MsgInfo",});
    }
}
