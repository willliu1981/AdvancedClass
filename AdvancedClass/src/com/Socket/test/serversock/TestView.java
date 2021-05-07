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
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Dimension;

public class TestView extends JFrame {

	public static final Session session = new Session();
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
					bean.feedback="";
					while (scanner.hasNext()) {
						String str = scanner.next();
						bean.feedback += str + " ";
					}

					JLabel lblNewLabel_info = (JLabel) session.getAttribute("lblNewLabel_info");
					System.out.println(String.format("Call1 %s : %d ,and get feedback = %s\n", bean.host, bean.port,
							bean.feedback));
					lblNewLabel_info.setText(String.format("Call1 %s : %d ,and get feedback = %s\n", bean.host,
							bean.port, bean.feedback));

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

		JTextField textField_host = new JTextField();
		this.session.setAttribute("textField_host", textField_host);
		textField_host.setFont(new Font("新細明體", Font.PLAIN, 16));
		panel_top.add(textField_host);
		textField_host.setColumns(10);

		JButton btnNewButton_start = new JButton("start");
		btnNewButton_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox chckbxNewCheckBox_master = (JCheckBox) session.getAttribute("chckbxNewCheckBox_master");
				JTextField textField_host = (JTextField) session.getAttribute("textField_host");
				JTextField textField_port = (JTextField) session.getAttribute("textField_port");
				bean = new TestSocketBean(textField_host.getText(), Integer.valueOf(textField_port.getText()));
				if(chckbxNewCheckBox_master.isSelected()) {
					TestSocketServer testServer = new TestSocketServer(bean.port);
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
				try (Socket sock = new Socket(bean.host, bean.port);
						Scanner scanner = new Scanner(sock.getInputStream())) {
					bean.feedback="";
					while (scanner.hasNext()) {
						String str = scanner.next();
						bean.feedback += str + " ";
					}

					JLabel lblNewLabel_info = (JLabel) session.getAttribute("lblNewLabel_info");
					System.out.println(String.format("Call2 %s : %d ,and get feedback = %s\n", bean.host, bean.port,
							bean.feedback));
					lblNewLabel_info.setText(String.format("Call2 %s : %d ,and get feedback = %s\n", bean.host,
							bean.port, bean.feedback));

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton_test2);

		JLabel lblNewLabel_info = new JLabel("New label");
		this.session.setAttribute("lblNewLabel_info", lblNewLabel_info);
		lblNewLabel_info.setFont(new Font("新細明體", Font.PLAIN, 16));
		panel.add(lblNewLabel_info);
	}

}
