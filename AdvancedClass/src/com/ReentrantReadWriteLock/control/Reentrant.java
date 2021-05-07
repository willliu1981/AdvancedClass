package com.ReentrantReadWriteLock.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.ReentrantReadWriteLock.model.Stock;
import com.ReentrantReadWriteLock.model.Supplier;

public class Reentrant {
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private List<Supplier> suppliers = new ArrayList<>();
	private List<Stock> stocks = new ArrayList<>();

	public Reentrant(List<Supplier> suppliers, List<Stock> stocks) {
		this.suppliers = suppliers;
		this.stocks = stocks;
	}

	private static void sleep() {
		sleep(1);
	}

	private static void sleep(int sces) {
		try {
			Thread.sleep(sces * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Stock query() {
		Stock stock = null;
		rwl.readLock().lock();

		stock = stocks.stream().filter(x -> {
			boolean r = x.getQuantity() < x.getStocklimit();
			return r;
		}).findFirst().orElse(null);

		sleep();
		rwl.readLock().unlock();
		return stock;
	}

	public void update(Stock stock) {
		rwl.writeLock().lock();

		stock.setQuantity(stock.getQuantity() + 1);
		sleep();
		rwl.writeLock().unlock();
	}

}
