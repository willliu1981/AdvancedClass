package com.ReentrantReadWriteLock.control;

import java.util.Date;

import com.ReentrantReadWriteLock.model.Stock;
import com.ReentrantReadWriteLock.model.Supplier;

public class ReentrantThread {
	public static void query(Reentrant rent, Supplier supplier) {
		new Thread() {
			public void run() {
				Stock stock = rent.query();
				System.out.format("reentrant thread query** %s ,%d\n",supplier.getName(),new Date().getTime());
				if (stock != null) {
					supplier.setProduce(0);
					update(rent, stock);
				}
				supplier.setAction(false);
			}
		}.start();
	}

	public static void update(Reentrant rent, Stock stock) {
		new Thread() {
			public void run() {
				System.out.println("reentrant thread update**");
				rent.update(stock);
			}
		}.start();
	}
}
