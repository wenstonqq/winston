package com.threegene.netty.share;

import com.threegene.netty.enums.MsgType;

public class PingMsg extends BaseMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5627125408805153071L;

	public PingMsg() {
		super();
		setType(MsgType.PING);
	}
}
