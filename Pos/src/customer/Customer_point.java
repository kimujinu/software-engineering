package customer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import main.Main;
import main.Point;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Font;
import java.io.FileWriter;

public class Customer_point extends JFrame {

	private JPanel contentPane;
	private JTextField phone;
	private boolean acc_check=true;
	SimpleDateFormat format1 = new SimpleDateFormat ("yyyy년MM월dd일-HH시mm분ss초");
	   Date time = new Date();
	   String time1 = format1.format(time);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer_point frame = new Customer_point();
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
	public Customer_point() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("포인트 적립");
		lblNewLabel.setFont(new Font("함초롬돋움", Font.BOLD, 18));
		lblNewLabel.setBounds(162, 50, 121, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("핸드폰 번호 :");
		lblNewLabel_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel_1.setBounds(28, 103, 96, 30);
		contentPane.add(lblNewLabel_1);
		
		phone = new JTextField();
		phone.setBounds(130, 111, 202, 21);
		contentPane.add(phone);
		phone.setColumns(10);
		
		JButton btnNewButton = new JButton("적립");
		btnNewButton.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int acc = (int)(Main.price*0.01);
				String number = phone.getText(); 
			
				
				Connection conn = null;
				Statement stmt = null;
		        PreparedStatement pstmt = null;
		        PreparedStatement pstmt2 = null;
		        PreparedStatement pstmt3 = null;
		        
		        ResultSet rs = null;
		        ResultSet rs2 = null;
		        int point = 0;
		        
		        try{
		        	if (number.length() != 4) {
	            		JOptionPane.showMessageDialog(null, "전화번호의 끝자리 형식이 아닙니다.");
	            		return;
	            	}
		            // 1. 드라이버 로딩
		            Class.forName("com.mysql.cj.jdbc.Driver");

		            // 2. 연결하기
		            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		            conn = DriverManager.getConnection(url, "root", "1234qwer");

		         // 3. 쿼리 수행을 위한 Statement 객체 생성
		            stmt = conn.createStatement();

		            // 3. SQL 쿼리 준비
		            String sql = "UPDATE customer set point = ? where phone = ?";
		            String sql2 = "select point from customer where phone = ?";
		            
		            pstmt = conn.prepareStatement(sql);
		            pstmt2 = conn.prepareStatement(sql2);
		            pstmt2.setString(1, number);
		            rs2 = pstmt2.executeQuery();
		            while(rs2.next()){
	                	point = rs2.getInt(1);
		                System.out.println(point);
	                }
		            if(acc_check == false){
		                JOptionPane.showMessageDialog(null, "이미 적립이 완료되었습니다.");  
		            }
		            else{
		            	String sql3 = "select * from customer where phone = ?";
			            pstmt3 = conn.prepareStatement(sql3);
			            // 4. 데이터 binding
			            pstmt3.setString(1, number);
			            // 5. 쿼리 실행 및 결과 처리
			            ResultSet rs3 = pstmt3.executeQuery();
			            String a = null;
			            while(rs3.next()) {
				               a = rs3.getString("phone"); //sql에 있는  전화번호
				        }
			            if(number.equals(a)){
			            	 // 4. 데이터 binding
			            	 pstmt.setInt(1, point+acc); //괄호안에 적립될값 넣기 
					         pstmt.setString(2, number);
					      // 5. 쿼리 실행 및 결과 처리
					         int count = pstmt.executeUpdate();
			                System.out.println("데이터 입력 성공");
			                JOptionPane.showMessageDialog(null, "적립 완료.");
			                acc_check = false;
			            }else {
			            	JOptionPane.showMessageDialog(null, "존재하지 않는 번호 입니다.");
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
		btnNewButton.setBounds(28, 193, 115, 58);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("계산");
		button.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date today = new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				System.out.print(date.format(today));
				double acc = Main.price; 
				
				Connection conn = null;
				Statement stmt = null;
		        PreparedStatement pstmt = null;
		        PreparedStatement pstmt2 = null;
		        ResultSet rs = null;
		        ResultSet rs2 = null;
		        int parttime = 0;
		        
		        try{
		        	
		            // 1. 드라이버 로딩
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            // 2. 연결하기
		            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		            conn = DriverManager.getConnection(url, "root", "1234qwer");
		         // 3. 쿼리 수행을 위한 Statement 객체 생성
		            stmt = conn.createStatement();

		            // 3. SQL 쿼리 준비
		            String sql = "UPDATE property set worktime = ? where date = ?"; 
		            String sql2 = "select worktime from property";
		            
		            pstmt = conn.prepareStatement(sql);
		            pstmt2 = conn.prepareStatement(sql2);
		            
		            rs2 = pstmt2.executeQuery();
		            
		            while(rs2.next()){
	                	parttime = rs2.getInt(1);
		                System.out.println(parttime);
	                }
		            
		            // 4. 데이터 binding
		            pstmt.setDouble(1, parttime+acc); //괄호안에 적립될값 넣기 
		            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
		            

		            // 5. 쿼리 실행 및 결과 처리
		           
		            int count = pstmt.executeUpdate();
		            
		            if( count == 0 ){
		                System.out.println("데이터 입력 실패");
		                
		            }
		            else{
		            	//영수증 정보저장
	                     try {
	                    	 Main.Receipt_Txt_Value += "포인트 사용: " + Point.user_point + " 원"  + Main.newLineChar;
	                         Main.Receipt_Txt_Value += "계산 금액: " + Main.price + "원" + Main.newLineChar + Main.newLineChar;
	                         Main.Receipt_Txt_Value += time1 + Main.newLineChar;
	                          // Txt로 내보내기
	                         // Txt로 내보내기
	                         String path = "C:/Users/김나연/Documents/" + time1 + ".txt";
	                         System.out.println(path);
	                         FileWriter w = new FileWriter(path);
	                           w.write(Main.Receipt_Txt_Value); 
	                           w.close();
	                         //Receipt_Txt_Value 초기화
	                         Main.Receipt_Txt_Value = null;
	                         Main.Receipt_Txt_Value = 
	                            "   영 수 증" + Main.newLineChar 
	                            + "    상호: 부경 Cafe(부경대점)" + Main.newLineChar
	                            + "    주소: 부산 남구" + Main.newLineChar
	                            + "    전화번호: 051-0000-0000" + Main.newLineChar
	                            + "==========================" + Main.newLineChar
	                            + "    물품 & 가격" + Main.newLineChar
	                            + "==========================" + Main.newLineChar;
	                           
	                     } catch (Exception e) {
	                              e.getStackTrace();
	                     }

	                     
		                System.out.println("데이터 입력 성공");
		                JOptionPane.showMessageDialog(null, "계산 완료.");
		                dispose();
		                Main.model1 = (DefaultTableModel) Main.table1.getModel();
						int rowCount = Main.model1.getRowCount();
						for (int i = rowCount - 1; i >= 0; i--) {
						    Main.model1.removeRow(i);
						}
		                Main.price = 0;
		                Main.totalprice.setText("총 가격은 : "+ Main.price);
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
		button.setBounds(307, 193, 115, 58);
		contentPane.add(button);
		
		JLabel lblNewLabel_2 = new JLabel("*적립금액은 총 구매 금액의 0.01% 입니다.*");
		lblNewLabel_2.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel_2.setBounds(12, 10, 320, 30);
		contentPane.add(lblNewLabel_2);
		
		JButton button_1 = new JButton("취소");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		button_1.setBounds(162, 193, 115, 58);
		contentPane.add(button_1);
	}
}
