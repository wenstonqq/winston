package com.threegene.netty.params;

public class ReplyServerBody extends ReplyBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6434881292730959522L;

	private String serverInfo;

	public ReplyServerBody(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	public String getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

}
