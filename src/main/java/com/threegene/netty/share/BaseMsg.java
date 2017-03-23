package com.threegene.netty.share;

import java.io.Serializable;

import com.threegene.netty.constants.Constants;
import com.threegene.netty.enums.MsgType;

public class BaseMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5595970403131900936L;

	private MsgType type;
	// 必须唯一，否者会出现channel调用混乱
	private String clientId;

	public BaseMsg() {
		this.clientId = Constants.getClientId();
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
