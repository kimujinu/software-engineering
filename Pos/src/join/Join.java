package join;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import DBconnection.Dbconnection;
import customer.Customer_Manage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import login.Login;
import main.Main;

public class Join extends JFrame {

	private JPanel contentPane;
	private JTextField txtId;
	private JPasswordField txtPassword;
	private JTextField txtName;
	private boolean flag=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Join frame = new Join();
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
	public Join() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJoin = new JLabel("회원가입");
		lblJoin.setFont(new Font("함초롬돋움", Font.PLAIN, 20));
		lblJoin.setBounds(172, 34, 82, 27);
		contentPane.add(lblJoin);
		
		JLabel lblId = new JLabel("아이디");
		lblId.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		lblId.setBounds(94, 84, 57, 15);
		contentPane.add(lblId);
		
		txtId = new JTextField();
		txtId.setBounds(190, 84, 116, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		JLabel lblPassword = new JLabel("비밀번호");
		lblPassword.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		lblPassword.setBounds(94, 112, 80, 21);
		contentPane.add(lblPassword);
		
		JLabel lblName = new JLabel("이름");
		lblName.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		lblName.setBounds(94, 143, 72, 21);
		contentPane.add(lblName);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(190, 115, 116, 21);
		contentPane.add(txtPassword);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(190, 146, 116, 21);
		contentPane.add(txtName);
		
		JButton btnJoin = new JButton("회원가입");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int count =0;
				
				String id = txtId.getText();
				String password = txtPassword.getText(); 
				String name = txtName.getText(); 
				
				Connection conn = null;
		        PreparedStatement pstmt = null;
		        
		        
		        try{
		        	if (txtId.getText().equals("")) {
	            		JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
	            		return;
	            	}
	            	if (txtPassword.getText().equals("")) {
	            		JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
	            		return;
	            	}
	            	if (txtName.getText().equals("")) {
	            		JOptionPane.showMessageDialog(null, "이름을 입력하세요.");
	            		return;
	            	}
		        	
		            // 1. 드라이버 로딩
		            Class.forName("com.mysql.cj.jdbc.Driver");
		
		            // 2. 연결하기
		            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		            conn = DriverManager.getConnection(url, "root", "1234qwer");
		
		            // 3. SQL 쿼리 준비
		            String sql = "INSERT INTO users VALUES (?, ?, ?)";
		            pstmt = conn.prepareStatement(sql);
		
		            // 4. 데이터 binding
		            pstmt.setString(1, id);
		            pstmt.setString(2, password);
		            pstmt.setString(3, name);
		
		            // 5. 쿼리 실행 및 결과 처리
		         // 4.5. 중복확인
		            if(flag==false){
		            	JOptionPane.showMessageDialog(null, "중복확인 해주세요.");
		            }
		            else if(flag==true){
		                System.out.println("데이터 입력 성공");
		                JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
		                count = pstmt.executeUpdate();
		                // 5. 쿼리 실행 및 결과 처리
			            if( count == 0 ){
			                System.out.println("데이터 입력 실패");
			            }else {
			            	System.out.println("데이터 입력 성공");
			            	Login login = new Login();
			                login.setVisible(true);
			                dispose();
			            }
		            }
		        }
		        catch( ClassNotFoundException e){
		            System.out.println("드라이버 로딩 실패");
		        }
		        catch( SQLException e){
		            System.out.println("에러 " + e);
		        }
		        finally{
		            try{
		                if( conn != null && !conn.isClosed()){
		                    conn.close();
		                }
		                if( pstmt != null && !pstmt.isClosed()){
		                    pstmt.close();
		                }
		            }
		            catch( SQLException e){
		                e.printStackTrace();
		            }
		        }
				
			}
		});
		btnJoin.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		btnJoin.setBounds(111, 193, 108, 23);
		contentPane.add(btnJoin);
		
		JButton btnNewButton = new JButton("중복확인");
		btnNewButton.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			     String id = txtId.getText();
				 
		         Dbconnection db = new Dbconnection();
		         Connection conn = null;
			     PreparedStatement pstmt = null;
			     
			     try{   
			    	 if (txtId.getText().equals("")) {
		            		JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
		            		return;
		            	}
			           // 1. 드라이버 로딩
			            Class.forName("com.mysql.cj.jdbc.Driver");
			            // 2. 연결하기
			            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			            conn = DriverManager.getConnection(url, "root", "1234qwer");
			            // 3. SQL 쿼리 준비
			            String sql = "select * from users where id = ?";
			            pstmt = conn.prepareStatement(sql);
			            // 4. 데이터 binding
			            pstmt.setString(1, id);
			            // 5. 쿼리 실행 및 결과 처리
			            ResultSet rs = pstmt.executeQuery();
			            String a = null;
			            while(rs.next()) {
				               a = rs.getString("id"); //sql에 있는  전화번호
				        }
			            if(id.equals(a)){
			            	JOptionPane.showMessageDialog(null, "존재하는 아이디 입니다.");
			            }else {
			            	JOptionPane.showMessageDialog(null, "사용가능한 아이디 입니다.");
			            	flag = true;
			            }
			        }
			        catch( ClassNotFoundException e){
			            System.out.println("드라이버 로딩 실패");
			        }
			        catch( SQLException e){
			            System.out.println("에러 " + e);
			        }
			        finally{
			            try{
			                if( conn != null && !conn.isClosed()){
			                    conn.close();
			                }
			                if( pstmt != null && !pstmt.isClosed()){
			                    pstmt.close();
			                }
			            }
			            catch( SQLException e){
			                e.printStackTrace();
			            }
			        }
					
				}
			});
		btnNewButton.setBounds(325, 83, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		button.setBounds(240, 193, 108, 23);
		contentPane.add(button);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		
//		JButton btnIdCheck = new JButton("중복확인");
//		btnIdCheck.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//			}
//		});
//		btnIdCheck.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
//		btnIdCheck.setBounds(314, 82, 108, 23);
//		contentPane.add(btnIdCheck);
	}
}
