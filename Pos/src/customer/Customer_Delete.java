package customer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.mysql.cj.xdevapi.Statement;

import DBconnection.Dbconnection;
import main.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Font;

public class Customer_Delete extends JFrame {

	private JPanel contentPane;
	private JTextField phone_t;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer_Delete frame = new Customer_Delete();
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
	public Customer_Delete() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("회원 삭제");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel.setBounds(176, 26, 116, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("삭제할 전화번호:");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_1.setBounds(12, 111, 138, 15);
		contentPane.add(lblNewLabel_1);
		
		phone_t = new JTextField();
		phone_t.setBounds(162, 108, 151, 21);
		contentPane.add(phone_t);
		phone_t.setColumns(10);
		
		
		
		JButton btnNewButton = new JButton("삭제");
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Connection conn;
					java.sql.Statement st;
					 Class.forName("com.mysql.cj.jdbc.Driver");
			         String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			         conn = DriverManager.getConnection(url,"root","1234qwer");
			         st = conn.createStatement();
					ResultSet res = ((java.sql.Statement) st).executeQuery("SELECT * FROM customer WHERE phone='"+phone_t.getText()+"'");
					if(res.next()) {
						((java.sql.Statement) st).executeUpdate("delete from customer where phone='"+phone_t.getText()+"'"); 
						JOptionPane.showMessageDialog(null, "탈퇴되었습니다.");
						Main main = new Main();
						main.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "전화번호가 일치하지 않습니다.!");
					}
					} catch(Exception e1) {
						e1.printStackTrace();
					}
			}
			
			
		});
		btnNewButton.setBounds(104, 203, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("취소");
		btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer_Manage manage = new Customer_Manage();
				manage.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(243, 203, 97, 23);
		contentPane.add(btnNewButton_1);
	}

}
