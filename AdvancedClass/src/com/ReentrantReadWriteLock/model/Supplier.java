package com.ReentrantReadWriteLock.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Supplier {
	private String name;
	private int speed;// 產能速度
	private Point position;
	private int produce;// 產能進度
	private static final int maxProduce = 10000;// 完成最大值
	private static int supplierQuantity;// 提供者數量
	private List<Freight> freight = new ArrayList<>();// 派出的貨車
	private boolean action=false;//是否執行過派車事件

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getProduce() {
		return produce;
	}

	public void setProduce(int produce) {
		this.produce = produce;
		if(this.produce>=this.getMaxProduce()) {
			this.produce=this.getMaxProduce();
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int getSupplierQuantity() {
		return supplierQuantity;
	}

	public static void setSupplierQuantity(int quantity) {
		Supplier.supplierQuantity = quantity;
	}

	public int getMaxProduce() {
		return maxProduce;
	}

	public List<Freight> getFreight() {
		return freight;
	}

	public void setFreight(List<Freight> freight) {
		this.freight = freight;
	}

	/*
	 * 完成度百分比
	 */
	public int getProducePercent() {
		return (int) (this.getProduce()*100 / this.getMaxProduce());
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

}
