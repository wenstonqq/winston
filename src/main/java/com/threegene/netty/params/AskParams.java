package com.threegene.netty.params;

import java.io.Serializable;

public class AskParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7870967112782446608L;

	private String auth;

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
