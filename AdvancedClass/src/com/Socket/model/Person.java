package com.Socket.model;

import java.awt.Point;

public class Person {
	private String name;
	private Point position;

	public Person() {
	}

	public Person(Point spawn) {
		this.position = spawn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	
	
}
