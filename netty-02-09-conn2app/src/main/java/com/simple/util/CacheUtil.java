package com.simple.util;

import com.simple.entity.ServerConfig;
import com.simple.server.NettyServer;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 21:54
 **/
public class CacheUtil {

    /**
     * 缓存channel
     */
    public static Map<String, Channel> cacheChannel = new ConcurrentHashMap<>();

    /**
     * 缓存服务信息
     */
    public static Map<Integer, ServerConfig> serverInfoMap = new ConcurrentHashMap<>();

    /**
     * 缓存服务端
     */
    public static Map<Integer, NettyServer> serverMap = new ConcurrentHashMap<>();
}
