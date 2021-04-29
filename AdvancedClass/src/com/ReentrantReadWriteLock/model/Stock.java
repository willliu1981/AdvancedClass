package com.ReentrantReadWriteLock.model;

import java.awt.Point;

public class Stock {
	private String name;
	private static int stocklimit;//存能最大限制
	private static int stacklimit;//貨車佇列最大限制
	private static int stockQuantity;//倉庫數量
	private Point position;
	
	private int quantity;//目前存量
	
	private int stack;//貨車等待
	
	private int order;

	public static int getStocklimit() {
		return stocklimit;
	}

	public static void setStocklimit(int stocklimit) {
		Stock.stocklimit = stocklimit;
	}

	public static int getStacklimit() {
		return stacklimit;
	}

	public static void setStacklimit(int stacklimit) {
		Stock.stacklimit = stacklimit;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		
	}

	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public static int getStockQuantity() {
		return stockQuantity;
	}

	public static void setStockQuantity(int stockquantity) {
		Stock.stockQuantity = stockquantity;
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
