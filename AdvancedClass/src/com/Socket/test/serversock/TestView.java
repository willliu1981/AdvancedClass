package com.Socket.test.serversock;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.Socket.control.Session;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Dimension;

public class TestView extends JFrame {

	public static final Session session = new Session();
	public static final ExecutorService es = Executors.newCachedThreadPool();
	public static final Map<TestSocketBean, Future> callables = new HashMap<>();
	private TestSocketBean bean;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestView frame = new TestView();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 823, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JButton btnNewButton_test1 = new JButton("test1");
		btnNewButton_test1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try (Socket sock = new Socket(bean.host, bean.port);
						Scanner scanner = new Scanner(sock.getInputStream())) {
					System.out.println(" test start");
					PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
					pw.println("client_sent_,name_=" + bean.name);
					System.out.println(" test print finish");
					bean.feedback = "";
					System.out.println(" test scan start");
					while (scanner.hasNext()) {
						String str = scanner.next();
						bean.feedback += str + " ";
					}
					System.out.println(" test scan finish");

					JLabel lblNewLabel_info = (JLabel) session.getAttribute("lblNewLabel_info");
					lblNewLabel_info.setText(String.format("Call1 %s : %d ,and get feedback = %s\n", bean.host,
							bean.port, bean.feedback));
					pw.close();

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JPanel panel_top = new JPanel();
		panel.add(panel_top);
		panel_top.setLayout(new BoxLayout(panel_top, BoxLayout.Y_AXIS));

		JCheckBox chckbxNewCheckBox_master = new JCheckBox("master");
		this.session.setAttribute("chckbxNewCheckBox_master", chckbxNewCheckBox_master);
		panel_top.add(chckbxNewCheckBox_master);

		JTextField textField_name = new JTextField();
		this.session.setAttribute("textField_name", textField_name);
		panel_top.add(textField_name);
		textField_name.setColumns(10);

		JTextField txtLocalhost = new JTextField();
		txtLocalhost.setText("localhost");
		this.session.setAttribute("textField_host", txtLocalhost);
		txtLocalhost.setFont(new Font("新細明體", Font.PLAIN, 16));
		panel_top.add(txtLocalhost);
		txtLocalhost.setColumns(10);

		JButton btnNewButton_start = new JButton("start");
		btnNewButton_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox chckbxNewCheckBox_master = (JCheckBox) session.getAttribute("chckbxNewCheckBox_master");
				JTextField textField_host = (JTextField) session.getAttribute("textField_host");
				JTextField textField_port = (JTextField) session.getAttribute("textField_port");
				JTextField textField_name = (JTextField) session.getAttribute("textField_name");
				bean = new TestSocketBean(textField_host.getText(), Integer.valueOf(textField_port.getText()));
				bean.name = textField_name.getText();
				if (chckbxNewCheckBox_master.isSelected()) {
					TestSocketServer testServer = new TestSocketServer2(bean.port);
					Thread t = new Thread(testServer);
					t.start();
				}
			}
		});

		JTextField textField_port = new JTextField();
		this.session.setAttribute("textField_port", textField_port);
		textField_port.setFont(new Font("新細明體", Font.PLAIN, 16));
		textField_port.setColumns(10);
		panel_top.add(textField_port);
		panel_top.add(btnNewButton_start);
		panel.add(btnNewButton_test1);

		JButton btnNewButton_test2 = new JButton("test2");
		btnNewButton_test2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JLabel lblNewLabel_info=(JLabel) session.getAttribute("lblNewLabel_info");

				TestSocketClientCallable callable=new TestSocketClientCallable(bean);
				Future<TestSocketBean> future=es.submit(callable);
				callables.put(bean, future);
				
				//es.shutdown();
				try {
					es.awaitTermination(5, TimeUnit.SECONDS);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
				Future<TestSocketBean> fu=callables.get(bean);
				try {
					bean=  fu.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				lblNewLabel_info.setText((String.format("Call1 %s : %d ,and get feedback = %s\n", bean.host,
						bean.port, bean.feedback)));
			}
		});
		panel.add(btnNewButton_test2);

		JLabel lblNewLabel_info = new JLabel("New label");
		this.session.setAttribute("lblNewLabel_info", lblNewLabel_info);
		lblNewLabel_info.setFont(new Font("新細明體", Font.PLAIN, 16));
		panel.add(lblNewLabel_info);
	}

}
