package com.ReentrantReadWriteLock.control;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.ReentrantReadWriteLock.model.Stock;
import com.ReentrantReadWriteLock.model.Supplier;

public class GameControl {
	public final static Dimension mapSize = new Dimension(120, 60);
	private static List<Supplier> supliers = new ArrayList<>();
	private static List<Stock> stocks = new ArrayList<>();

	public static void initialize() {
		Stock.setStocklimit(100);
		Stock.setStacklimit(5);

		createSuplier();
		createStock();
	}

	private static void createStock() {
		Stock.setStockQuantity(3);
		for (int i = 0; i < Stock.getStockQuantity(); i++) {
			Stock s = new Stock();
			s.setPosition(new Point(60, i * (mapSize.height/Stock.getStockQuantity()) + 2));
			s.setName("stock-"+i);
			s.setStocklimit(25);
			stocks.add(s);

		}
		
	}

	private static void createSuplier() {

		Supplier.setSupplierQuantity(10);
		for (int i = 0; i < Supplier.getSupplierQuantity(); i++) {
			Supplier s = new Supplier();
			s.setPosition(new Point(2, i * (mapSize.height/Supplier.getSupplierQuantity()) + 2));
			s.setSpeed(500);
			s.setName("sup-"+i);
			supliers.add(s);

		}
		
		
	}

	public static List<Supplier> getSupliers() {
		return supliers;
	}



	public static List<Stock> getStocks() {
		return stocks;
	}


	
	

}
