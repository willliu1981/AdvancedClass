package com.Socket.test.serversock;

public class TestSocketBean {
	public String name;
	public String host;
	public int port;
	public String feedback="";
	
	public TestSocketBean(String host,int port) {
		this.host=host;
		this.port=port;
	}
}
