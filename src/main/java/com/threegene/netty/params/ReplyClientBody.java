package com.threegene.netty.params;

public class ReplyClientBody extends ReplyBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7842327657095564471L;

	private String clientInfo;

	public ReplyClientBody(String clientInfo) {
		this.clientInfo = clientInfo;
	}

	public String getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}

}
