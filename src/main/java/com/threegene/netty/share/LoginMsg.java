package com.threegene.netty.share;

import com.threegene.netty.enums.MsgType;

public class LoginMsg extends BaseMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4611379790960120610L;

	private String userName;
	private String password;

	public LoginMsg() {
		super();
		setType(MsgType.LOGIN);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
