package com.Socket.test.serversock;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class TestSocketClientCallable implements Callable<TestSocketBean> {

	private TestSocketBean bean;

	public TestSocketClientCallable(TestSocketBean bean) {
		this.bean = bean;
	}

	@Override
	public TestSocketBean call() throws Exception {
		try (Socket sock = new Socket(bean.host, bean.port); Scanner scanner = new Scanner(sock.getInputStream())) {
			PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
			pw.println("client_sent_,name_=" + bean.name);
			bean.feedback = "";
			while (scanner.hasNext()) {
				String str = scanner.next();
				bean.feedback += str + " ";
			}

			pw.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bean;
	}

}
