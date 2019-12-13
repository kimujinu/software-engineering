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

import DBconnection.Dbconnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import login.Login;
import main.Main;
import java.awt.Font;

public class Customer_Join extends JFrame {

	private JPanel contentPane;
	private JTextField phone;
	private JButton btnNewButton;
	private JLabel lblNewLabel_2;
	private JTextField Name;
	private JButton btnNewButton_1;
	private boolean flag=false;
	private JButton btnNewButton_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer_Join frame = new Customer_Join();
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
	public Customer_Join() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("고객 회원가입");
		lblNewLabel.setFont(new Font("함초롬돋움", Font.BOLD, 20));
		lblNewLabel.setBounds(162, 28, 131, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("핸드폰 끝자리 번호 :");
		lblNewLabel_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel_1.setBounds(0, 111, 145, 15);
		contentPane.add(lblNewLabel_1);
		
		phone = new JTextField();
		phone.setBounds(150, 109, 177, 21);
		contentPane.add(phone);
		phone.setColumns(10);
		
		btnNewButton = new JButton("가입");
		btnNewButton.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = Name.getText(); // 이름 입력
		        String num = phone.getText(); //폰번호 입력
		        
		        int count =0;
		         Dbconnection db = new Dbconnection();
		         Connection conn = null;
			     PreparedStatement pstmt = null;
			     
			     try{
			        	if (Name.getText().equals("")) {
		            		JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
		            		return;
		            	}
		            	if (phone.getText().equals("")) {
		            		JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
		            		return;
		            	}
		            	
		            	
			        	
			            // 1. 드라이버 로딩
			            Class.forName("com.mysql.cj.jdbc.Driver");
			
			            // 2. 연결하기
			            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			            conn = DriverManager.getConnection(url, "root", "1234qwer");
			
			            // 3. SQL 쿼리 준비
			            String sql = "INSERT INTO customer VALUES (?, ?, 0)";
			            pstmt = conn.prepareStatement(sql);
			
			            // 4. 데이터 binding
			            pstmt.setString(1, num);
			            pstmt.setString(2, name);
			            
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
				
			
		btnNewButton.setBounds(150, 185, 119, 51);
		contentPane.add(btnNewButton);
		
		lblNewLabel_2 = new JLabel("이름:");
		lblNewLabel_2.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel_2.setBounds(76, 83, 81, 18);
		contentPane.add(lblNewLabel_2);
		
		Name = new JTextField();
		Name.setBounds(150, 83, 179, 21);
		contentPane.add(Name);
		Name.setColumns(10);
		
		btnNewButton_1 = new JButton("중복확인");
		btnNewButton_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String num = phone.getText();
				 
		         Dbconnection db = new Dbconnection();
		         Connection conn = null;
			     PreparedStatement pstmt = null;
			     
			     try{        	
			    	 	if (num.length() != 4) {
		            		JOptionPane.showMessageDialog(null, "전화번호의 끝자리 형식이 아닙니다.");
		            		return;
		            	}
			           // 1. 드라이버 로딩
			            Class.forName("com.mysql.cj.jdbc.Driver");
			            // 2. 연결하기
			            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			            conn = DriverManager.getConnection(url, "root", "1234qwer");
			            // 3. SQL 쿼리 준비
			            String sql = "select * from customer where phone = ?";
			            pstmt = conn.prepareStatement(sql);
			            // 4. 데이터 binding
			            pstmt.setString(1, num);
			            // 5. 쿼리 실행 및 결과 처리
			            ResultSet rs = pstmt.executeQuery();
			            String a = null;
			            while(rs.next()) {
				               a = rs.getString("phone"); //sql에 있는  전화번호
				        }
			            if(num.equals(a)){
			            	JOptionPane.showMessageDialog(null, "존재하는 번호 입니다.");
			            }else {
			            	JOptionPane.showMessageDialog(null, "사용가능한 번호 입니다.");
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
		btnNewButton_1.setBounds(296, 185, 97, 51);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("뒤로가기");
		btnNewButton_2.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer_Manage manage = new Customer_Manage();
				manage.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setBounds(12, 185, 97, 51);
		contentPane.add(btnNewButton_2);
	}

}
