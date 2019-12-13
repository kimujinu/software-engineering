package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import java.awt.Font;

public class Point extends JFrame {

	   private JPanel contentPane;
	   private JTextField txt_phone;
	   private JTextField txt_point;
	   private JTextField txt_usingPoint;
	   private int pointing = 0;
	   public static int user_point=0;

	   /**
	    * Launch the application.
	    */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Point frame = new Point();
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
	public Point() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("포인트 사용");
		lblNewLabel.setFont(new Font("함초롬돋움", Font.PLAIN, 18));
		lblNewLabel.setBounds(173, 30, 96, 33);
		contentPane.add(lblNewLabel);
		
		txt_phone = new JTextField();
		txt_phone.setBounds(135, 73, 173, 21);
		contentPane.add(txt_phone);
		txt_phone.setColumns(10);
		
		txt_phone.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent arg0) {
				Connection con = null;
	            con = Dbconnection.Db(); //sql 연결하기
	            try {
	               String sql = "SELECT * FROM customer where phone=?"; //join1테이블에 아이디가 있는지 비교하기
	               PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	               pst.setString(1,txt_phone.getText()); //아이디 입력
	               ResultSet rs = pst.executeQuery();
	               
	               while(rs.next())
	               {
	            	   txt_point.setText(rs.getString("Point")); //비밀번호 출력
	               }
	               pst.close();
	               }
	               catch(Exception ex) {
	                  ex.printStackTrace();
	               }
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("전화번호 :");
		lblNewLabel_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel_1.setBounds(64, 76, 77, 18);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("사용");
		btnNewButton.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user_point = Integer.parseInt(txt_usingPoint.getText());
				String phone_num = txt_phone.getText();
				
				
				Connection conn = null;
				Statement stmt = null;
		        PreparedStatement pstmt = null;
		        PreparedStatement pstmt2 = null;
		        ResultSet rs = null;
		       
		       
		        try{
		            // 1. 드라이버 로딩
		            Class.forName("com.mysql.cj.jdbc.Driver");

		            // 2. 연결하기
		            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		            conn = DriverManager.getConnection(url, "root", "1234qwer");
		         // 3. 쿼리 수행을 위한 Statement 객체 생성
		            stmt = conn.createStatement();
		            // 3. SQL 쿼리 준비
		            String sql = "UPDATE customer set Point = ? where phone = ?";
		            String sql2 = "SELECT Point FROM customer where phone=?"; 
		            
		            pstmt = conn.prepareStatement(sql);
		            pstmt2 = conn.prepareStatement(sql2);
		            
		            pstmt2.setString(1, phone_num);
		            rs = pstmt2.executeQuery();
		            while(rs.next()){
		            	pointing = rs.getInt(1);
		                System.out.println(pointing);
	                }
		            pointing = pointing - user_point;	 
		            // 4. 데이터 binding
		            pstmt.setInt(1, pointing);
		            pstmt.setString(2, phone_num);
		            
		            // 5. 쿼리 실행 및 결과 처리
		            int count = pstmt.executeUpdate();
		            
		            if( count == 0 ){
		                System.out.println("데이터 입력 실패");
		                
		            }
		            else{
		            	System.out.println("데이터 입력 성공");
	                    JOptionPane.showMessageDialog(null, "적용 되었습니다.");
	                    Main.price = Main.price - user_point;
	                    Main.totalprice.setText("할인 적용후 금액 : "+ Main.price);
	                    dispose();
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
		btnNewButton.setBounds(98, 193, 97, 23);
		contentPane.add(btnNewButton);
		
		JLabel label = new JLabel("현 포인트 : ");
		label.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		label.setBounds(55, 107, 77, 27);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("사용할 포인트 :");
		label_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		label_1.setBounds(30, 150, 102, 15);
		contentPane.add(label_1);
		
		txt_point = new JTextField();
		txt_point.setColumns(10);
		txt_point.setBounds(135, 113, 173, 21);
		contentPane.add(txt_point);
		
		txt_usingPoint = new JTextField();
		txt_usingPoint.setColumns(10);
		txt_usingPoint.setBounds(135, 150, 173, 21);
		contentPane.add(txt_usingPoint);
		
		JButton button = new JButton("취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		button.setBounds(218, 193, 97, 23);
		contentPane.add(button);
		
		
	}
}
