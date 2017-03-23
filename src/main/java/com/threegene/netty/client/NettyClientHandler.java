package com.threegene.netty.client;

import com.threegene.netty.enums.MsgType;
import com.threegene.netty.params.ReplyClientBody;
import com.threegene.netty.params.ReplyServerBody;
import com.threegene.netty.share.BaseMsg;
import com.threegene.netty.share.LoginMsg;
import com.threegene.netty.share.PingMsg;
import com.threegene.netty.share.ReplyMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {
	// 利用写空闲发送心跳检测消息
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			switch (e.state()) {
			case WRITER_IDLE:
				PingMsg pingMsg = new PingMsg();
				ctx.writeAndFlush(pingMsg);
				System.out.println("send ping to server----------");
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
		MsgType msgType = baseMsg.getType();
		switch (msgType) {
		case LOGIN: {
			// 向服务器发起登录
			LoginMsg loginMsg = new LoginMsg();
			loginMsg.setPassword("winston");
			loginMsg.setUserName("123456");
			channelHandlerContext.writeAndFlush(loginMsg);
		}
			break;
		case PING: {
			System.out.println("receive ping from server----------");
		}
			break;
		case ASK: {
			ReplyClientBody replyClientBody = new ReplyClientBody("client info **** !!!");
			ReplyMsg replyMsg = new ReplyMsg();
			replyMsg.setBody(replyClientBody);
			channelHandlerContext.writeAndFlush(replyMsg);
		}
			break;
		case REPLY: {
			ReplyMsg replyMsg = (ReplyMsg) baseMsg;
			ReplyServerBody replyServerBody = (ReplyServerBody) replyMsg.getBody();
			System.out.println("receive server msg: " + replyServerBody.getServerInfo());
		}
		default:
			break;
		}
		ReferenceCountUtil.release(msgType);
	}

}
