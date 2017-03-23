package com.threegene.netty.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

public class NettyChannelMap {

	private static Map<String, SocketChannel> map = new ConcurrentHashMap<>();

	public static void add(String clientId, SocketChannel socketChannel) {
		map.put(clientId, socketChannel);
	}

	public static Channel get(String clientId) {
		return map.get(clientId);
	}

	public static void remove(SocketChannel socketChannel) {
		for (Map.Entry<String, SocketChannel> entry : map.entrySet()) {
			if (entry.getValue() == socketChannel) {
				map.remove(entry.getKey());
			}
		}
	}
}
