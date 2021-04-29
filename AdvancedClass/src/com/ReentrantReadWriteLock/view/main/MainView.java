package com.ReentrantReadWriteLock.view.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.ReentrantReadWriteLock.control.GameControl;
import com.ReentrantReadWriteLock.control.Reentrant;
import com.ReentrantReadWriteLock.control.ReentrantThread;
import com.ReentrantReadWriteLock.control.Session;
import com.ReentrantReadWriteLock.model.Stock;

public class MainView extends JFrame {
	static class Task extends TimerTask {
		@Override
		public void run() {
			Reentrant rent = (Reentrant) session.getAttribute("rent");
			GameControl.getSupliers().forEach(x -> {
				x.setProduce(x.getProduce() + x.getSpeed());
				if (x.getProducePercent() >= 100) {
					ReentrantThread.query(rent);
					x.setProduce(0);
				}
			});

			JPanel panel_map = (JPanel) session.getAttribute("panel_map");
			panel_map.repaint();
		}
	}

	public static final Session session = new Session();

	public static void main(String s[]) {
		MainView v = new MainView();
		v.setVisible(true);
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setBounds(100, 100, 800, 600);

		Timer timer = new Timer();
		timer.schedule(new Task(), 0, 200);

	}

	public MainView() {
		GameControl.initialize();
		Reentrant rent = new Reentrant(GameControl.getSupliers(), GameControl.getStocks());
		session.setAttribute("rent", rent);

		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));

		JPanel panel_map = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Rectangle rect = getRect(this);
				int ux = rect.width / GameControl.mapSize.width;
				int uy = rect.height / GameControl.mapSize.height;

				MainView.setFont(g, 16);

				/*
				 * supplier
				 */
				GameControl.getSupliers().forEach(x -> {
					g.setColor(Color.BLUE);
					g.drawRect(x.getPosition().x * ux, x.getPosition().y * uy, ux, uy);
					g.drawString(String.format("%s (%d %s)", x.getName(), x.getProducePercent(), "%"),
							x.getPosition().x * ux, x.getPosition().y * uy + uy * 3);

				});

				/*
				 * stock
				 */

				GameControl.getStocks().forEach(x -> {
					g.setColor(Color.red);
					g.fillRect(x.getPosition().x * ux, x.getPosition().y * uy, ux * 2, uy * 2);
					g.drawString(x.getName(), x.getPosition().x * ux, x.getPosition().y * uy + uy * 4);
					g.drawString(String.format("%d / %d", x.getQuantity(), x.getStocklimit()), x.getPosition().x * ux,
							x.getPosition().y * uy + uy * 6);
				});

			}
		};

		session.setAttribute("panel_map", panel_map);

		panel.add(panel_map, "name_36000268324400");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setPreferredSize(new Dimension(10, 100));
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JPanel panel_supplier = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Rectangle rect = getRect(this);
				setDefaultFont(g);
				g.drawString("supplier", (int) (rect.getWidth() / 2) - 20, (int) (rect.getHeight() / 2) + 10);
			}
		};
		panel_supplier.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.add(panel_supplier);

		JPanel panel_stock = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Rectangle rect = getRect(this);
				setDefaultFont(g);
				g.drawString("stock", (int) (rect.getWidth() / 2) - 10, (int) (rect.getHeight() / 2) + 10);
			}
		};
		panel_stock.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.add(panel_stock);

		JPanel panel_consumer = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Rectangle rect = getRect(this);
				setDefaultFont(g);
				g.drawString("comsumer", (int) (rect.getWidth() / 2) - 20, (int) (rect.getHeight() / 2) + 10);
			}
		};
		panel_consumer.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.add(panel_consumer);

	}

	static private Rectangle getRect(Component comp) {
		Rectangle rect = null;
		JPanel jp = ((JPanel) comp);
		Insets ins = jp.getInsets();
		int w = jp.getWidth() - (ins.left + ins.right);
		int h = jp.getHeight() - (ins.top + ins.bottom);
		rect = new Rectangle(0, 0, w, h);
		return rect;
	}

	static private void setDefaultFont(Graphics g) {
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
	}

	static private void setFont(Graphics g, int size) {
		g.setFont(new Font(Font.SERIF, Font.PLAIN, size));
	}

}
