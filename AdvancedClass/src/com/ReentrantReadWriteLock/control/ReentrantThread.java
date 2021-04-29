package com.ReentrantReadWriteLock.control;

import com.ReentrantReadWriteLock.model.Stock;

public class ReentrantThread {
	public static void query(Reentrant rent) {
		new Thread() {
			public void run() {
				Stock stock = rent.query();
				System.out.println("reentrant thread**"+stock);
				if (stock != null) {
					update(rent, stock);
				}
			}
		}.start();

	}

	public static void update(Reentrant rent, Stock stock) {
		new Thread() {
			public void run() {
				rent.update(stock);
			}
		}.start();
	}
}
