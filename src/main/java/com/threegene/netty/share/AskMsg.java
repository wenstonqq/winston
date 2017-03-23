package com.threegene.netty.share;

import com.threegene.netty.enums.MsgType;
import com.threegene.netty.params.AskParams;

public class AskMsg extends BaseMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1942594218778860081L;

	private AskParams params;

	public AskMsg() {
		super();
		setType(MsgType.ASK);
	}

	public AskParams getParams() {
		return params;
	}

	public void setParams(AskParams params) {
		this.params = params;
	}

}
