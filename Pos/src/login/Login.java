package login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import DBconnection.Dbconnection;
import main.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import join.Join;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JPasswordField password;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("로그인");
		lblLogin.setFont(new Font("함초롬돋움", Font.PLAIN, 20));
		lblLogin.setBounds(188, 31, 65, 27);
		contentPane.add(lblLogin);
		
		JLabel lblId = new JLabel("아이디");
		lblId.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		lblId.setBounds(118, 83, 57, 15);
		contentPane.add(lblId);
		
		id = new JTextField();
		id.setBounds(199, 83, 116, 21);
		contentPane.add(id);
		id.setColumns(10);
		
		JLabel lblPassword = new JLabel("비밀번호");
		lblPassword.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		lblPassword.setBounds(118, 120, 80, 21);
		contentPane.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(199, 123, 116, 21);
		contentPane.add(password);
		password.setColumns(10);
		
		JButton btnLogin = new JButton("로그인");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id1 = id.getText(); // 아이디 입력
		        String pwd = password.getText(); //비밀번호 입력
		         
		         
		         Dbconnection db = new Dbconnection();
		         Connection conn = null;
			   
			     PreparedStatement pstmt2 = null;
		        try {
		        	conn = db.Db();
		        	String sql = "select * from users  where id = ?"; //테이블에 아이디가 있는지 비교하기
		        	PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
			        pst.setString(1, id1);
			        ResultSet rs = pst.executeQuery();
			        String a = null;
			        String b = null;
			        while(rs.next()) {
			               a = rs.getString("id"); //sql에 있는 아이디 출력
			               b = rs.getString("password"); //sql에 있는 비밀번호 출력
			        }
			        if(id1.equals(a)) { //아이디 확인하기
			               if(pwd.equals(b)) { //비밀번호 확인하기
			            	    String sql2 = "INSERT INTO property(date) VALUES(?)"; 
					            pstmt2 = conn.prepareStatement(sql2);
					
					            // 4. 데이터 binding
					            pstmt2.setDate(1, new java.sql.Date(System.currentTimeMillis()));
					            int rs2 = pstmt2.executeUpdate();
					            if( rs2 == 0 ){
					                System.out.println("데이터 입력 실패");
					            }else {
					            	System.out.println("데이터 입력 성공");
					            }
			            	   Main main = new Main();
			   				   main.setVisible(true);
			   				   dispose();
			                  
			               	}
			               else {
			            	   JOptionPane.showMessageDialog(null, "비밀번호가 잘못 입력되었습니다.");
				               }
			        }
		            else {
		               JOptionPane.showMessageDialog(null, "아이디가 잘못 입력되었습니다.");
		            }
		            }catch(Exception ex) {
			               System.out.println(ex.getMessage());
			            }
			            }

			});
		btnLogin.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		btnLogin.setBounds(174, 169, 108, 23);
		contentPane.add(btnLogin);
		
		JButton btnJoin = new JButton("회원가입");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Join join = new Join();
				join.setVisible(true);
				dispose();
			}
		});
		btnJoin.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		btnJoin.setBounds(174, 202, 108, 23);
		contentPane.add(btnJoin);
	}
}
