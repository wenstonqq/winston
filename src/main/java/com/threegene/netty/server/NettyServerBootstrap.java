package com.threegene.netty.server;

import java.util.concurrent.TimeUnit;

import com.threegene.netty.share.AskMsg;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyServerBootstrap {
	private int port;
	@SuppressWarnings("unused")
	private SocketChannel socketChannel;

	public NettyServerBootstrap(int port) throws InterruptedException {
		this.port = port;
		bind();
	}

	private void bind() throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, worker);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		// 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		// 保持长连接状态
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				ChannelPipeline p = socketChannel.pipeline();
				p.addLast(new ObjectEncoder());
				p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
				p.addLast(new NettyServerHandler());

			}
		});
		ChannelFuture f = bootstrap.bind(port).sync();
		if (f.isSuccess()) {
			System.out.println("server start-----------------------------");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		@SuppressWarnings("unused")
		NettyServerBootstrap bootstrap = new NettyServerBootstrap(9999);
		while (true) {
			SocketChannel channel = (SocketChannel) NettyChannelMap.get("001");
			if (channel != null) {
				AskMsg askMsg = new AskMsg();
				channel.writeAndFlush(askMsg);
			}
			TimeUnit.SECONDS.sleep(5);
		}
	}

}
