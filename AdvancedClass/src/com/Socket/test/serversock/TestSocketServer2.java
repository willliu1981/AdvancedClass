package com.Socket.test.serversock;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JLabel;

public class TestSocketServer2 extends TestSocketServer {
	static private int count;
	
	public TestSocketServer2(int port) {
		super(port);
	}

	@Override
	public void run() {
		JLabel lblNewLabel_info = (JLabel) TestView.session.getAttribute("lblNewLabel_info");
		lblNewLabel_info.setText("Server listening, port: " + port);
		try {
			ServerSocket serverSock = new ServerSocket(port);
			while (true) {
				Socket clientSock = serverSock.accept();
				PrintWriter pw = new PrintWriter(clientSock.getOutputStream(), true);
				Scanner scanner = new Scanner(clientSock.getInputStream());
				Thread.sleep(3000);
				String getStr = "";
				while (scanner.hasNext()) {
					String str = scanner.next();
					getStr += str + " ";
					break;
				}
				pw.println("name=" + getStr + "Feedback count " + count++ + " ,time =" + new Date());
				pw.close();
				scanner.close();
				//clientSock.close();
				//serverSock.close();
			}
		} catch (BindException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
