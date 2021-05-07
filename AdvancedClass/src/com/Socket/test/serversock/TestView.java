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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class TestView extends JFrame {

	public static final Session session = new Session();
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

					int port = 1234;
					TestSocketServer testServer = new TestSocketServer(9908);
					Thread t = new Thread(testServer);
					t.start();

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestSocketBean bean = new TestSocketBean("localhost", 9908);
				try (Socket sock = new Socket(bean.host, bean.port);
						Scanner scanner = new Scanner(sock.getInputStream())) {
					bean.feedback = scanner.next();
					JLabel lblNewLabel_info = (JLabel) session.getAttribute("lblNewLabel_info");
					lblNewLabel_info.setText(String.format("Call %s : %d ,and get feedback = %s\n", bean.host,
							bean.port, bean.feedback));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);

		JLabel lblNewLabel_info = new JLabel("New label");
		this.session.setAttribute("lblNewLabel_info", lblNewLabel_info);
		lblNewLabel_info.setFont(new Font("新細明體", Font.PLAIN, 16));
		panel.add(lblNewLabel_info);
	}

}
