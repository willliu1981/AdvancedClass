package com.Socket.control;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private Map<String, Object> session = new HashMap<>();

	public void setAttribute(String name, Object obj) {
		this.session.put(name, obj);
	}

	public Object getAttribute(String name) {
		return this.session.get(name);
	}

}
