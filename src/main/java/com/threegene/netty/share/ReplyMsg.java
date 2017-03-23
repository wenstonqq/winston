package com.threegene.netty.share;

import com.threegene.netty.enums.MsgType;
import com.threegene.netty.params.ReplyBody;

public class ReplyMsg extends BaseMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6060819729540904666L;

	private ReplyBody body;

	public ReplyMsg() {
		super();
		setType(MsgType.REPLY);
	}

	public ReplyBody getBody() {
		return body;
	}

	public void setBody(ReplyBody body) {
		this.body = body;
	}

}
