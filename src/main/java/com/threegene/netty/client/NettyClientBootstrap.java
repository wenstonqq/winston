package com.threegene.netty.client;

import java.util.concurrent.TimeUnit;

import com.threegene.netty.constants.Constants;
import com.threegene.netty.params.AskParams;
import com.threegene.netty.share.AskMsg;
import com.threegene.netty.share.LoginMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class NettyClientBootstrap {
	private int port;
	private String host;
	private SocketChannel socketChannel;
	@SuppressWarnings("unused")
	private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

	public NettyClientBootstrap(int port, String host) throws InterruptedException {
		this.port = port;
		this.host = host;
		start();
	}

	private void start() throws InterruptedException {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.group(eventLoopGroup);
		bootstrap.remoteAddress(host, port);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
				socketChannel.pipeline().addLast(new ObjectEncoder());
				socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
				socketChannel.pipeline().addLast(new NettyClientHandler());
			}
		});
		ChannelFuture future = bootstrap.connect(host, port).sync();
		if (future.isSuccess()) {
			socketChannel = (SocketChannel) future.channel();
			System.out.println("connect server  成功---------");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Constants.setClientId("001");
		NettyClientBootstrap bootstrap = new NettyClientBootstrap(9999, "localhost");
		LoginMsg loginMsg = new LoginMsg();
		loginMsg.setUserName("winston");
		loginMsg.setPassword("123456");
		bootstrap.socketChannel.writeAndFlush(loginMsg);
		while (true) {
			TimeUnit.SECONDS.sleep(3);
			AskMsg askMsg = new AskMsg();
			AskParams askParams = new AskParams();
			askParams.setAuth("authToken");
			askMsg.setParams(askParams);
			bootstrap.socketChannel.writeAndFlush(askMsg);
		}
	}
}
