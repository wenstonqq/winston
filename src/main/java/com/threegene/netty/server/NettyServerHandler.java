package com.threegene.netty.server;

import com.alibaba.fastjson.JSON;
import com.threegene.netty.enums.MsgType;
import com.threegene.netty.params.ReplyClientBody;
import com.threegene.netty.params.ReplyServerBody;
import com.threegene.netty.share.AskMsg;
import com.threegene.netty.share.BaseMsg;
import com.threegene.netty.share.LoginMsg;
import com.threegene.netty.share.PingMsg;
import com.threegene.netty.share.ReplyMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// channel失效，从Map中移除
		NettyChannelMap.remove((SocketChannel) ctx.channel());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
		if (MsgType.LOGIN.equals(baseMsg.getType())) {
			LoginMsg loginMsg = (LoginMsg) baseMsg;
			System.out.println(JSON.toJSONString(loginMsg));
			if ("winston".equals(loginMsg.getUserName()) && "123456".equals(loginMsg.getPassword())) {
				// 登录成功，将channel保存到服务端的map中
				NettyChannelMap.add(loginMsg.getClientId(), (SocketChannel) channelHandlerContext.channel());
				System.out.println("client:" + loginMsg.getClientId() + ",登录成功");
			}

		} else {
			if (NettyChannelMap.get(baseMsg.getClientId()) == null) {
				// 说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
				LoginMsg loginMsg = new LoginMsg();
				channelHandlerContext.channel().writeAndFlush(loginMsg);
			}

		}
		switch (baseMsg.getType()) {
		case PING: {
			PingMsg pingMsg = (PingMsg) baseMsg;
			PingMsg replyPing = new PingMsg();
			NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);
		}
			break;
		case ASK: {
			// 收到客户端的请求
			AskMsg askMsg = (AskMsg) baseMsg;
			if ("authToken".equals(askMsg.getParams().getAuth())) {
				ReplyServerBody replyServerBody = new ReplyServerBody("server info $$$ !!!");
				ReplyMsg replyMsg = new ReplyMsg();
				replyMsg.setBody(replyServerBody);
				NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(replyMsg);
			}
		}
			break;
		case REPLY: {
			// 收到客户端回复
			ReplyMsg replyMsg = (ReplyMsg) baseMsg;
			ReplyClientBody clientBody = (ReplyClientBody) replyMsg.getBody();
			System.out.println("receive client message:" + clientBody.getClientInfo());
		}
			break;
		default:
			break;
		}
		ReferenceCountUtil.release(baseMsg);
	}

}
