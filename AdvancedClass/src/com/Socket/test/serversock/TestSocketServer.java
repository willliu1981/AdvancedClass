package com.Socket.test.serversock;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JLabel;

public class TestSocketServer implements Runnable {
	private int port;
	static private int count;

	public TestSocketServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		JLabel lblNewLabel_info=(JLabel) TestView.session.getAttribute("lblNewLabel_info");
		lblNewLabel_info.setText("Server listening, port: " + port);
		while (true) {
			try {
				ServerSocket serverSock = new ServerSocket(port);
				Socket clientSock = serverSock.accept();
				Thread.sleep(1000);
				PrintWriter pw = new PrintWriter(clientSock.getOutputStream(), true);
				pw.println("Feedback count " + count+++" ,time ="+ new Date());
				serverSock.close();
				clientSock.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
