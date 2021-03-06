package com.Socket.test.serversock;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JLabel;

public class TestSocketServer implements Runnable {
	protected int port;
	static private int count;

	public TestSocketServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		JLabel lblNewLabel_info = (JLabel) TestView.session.getAttribute("lblNewLabel_info");
		lblNewLabel_info.setText("Server listening, port: " + port);
		while (true) {
			try {
				System.out.println("xxx1");
				ServerSocket serverSock = new ServerSocket(port);
				Socket clientSock = serverSock.accept();
				System.out.println("xxx accept");
				Thread.sleep(3000);
				System.out.println("xxx after sleep");
				PrintWriter pw = new PrintWriter(clientSock.getOutputStream(), true);
				Scanner scanner = new Scanner(clientSock.getInputStream());
				String getStr = "";
				while (scanner.hasNext()) {
					System.out.println("xxx has next  ");
					String str = scanner.next();
					System.out.println("xxx scanner : " + str);
					getStr += str + " ";
					System.out.println("xxx scanner end ");
					break;
				}
				System.out.println("xxx getStr = " + getStr);
				pw.println("name=" + getStr + "Feedback count " + count++ + " ,time =" + new Date());
				serverSock.close();
				clientSock.close();
				scanner.close();
				pw.close();
			}catch(BindException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
