package Inventory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import DBconnection.Dbconnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import login.Login;
import main.Main;
import java.awt.Font;

public class Inventory_manage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtName;
	private JTextField txtPrice;
	private JButton btnRemove;
	private boolean flag=false;
	private DefaultTableModel dtm = new DefaultTableModel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory_manage frame = new Inventory_manage();
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
	public Inventory_manage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 218, 241);
		contentPane.add(scrollPane);
		
		
		dtm.addColumn("이름");
		dtm.addColumn("가격");
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		txtName = new JTextField();
		txtName.setBounds(306, 8, 116, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(306, 39, 116, 21);
		contentPane.add(txtPrice);
		txtPrice.setColumns(10);
		
		JButton btnAdd = new JButton("추가");
		btnAdd.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = txtName.getText();
				String price = txtPrice.getText();  
				
				Connection conn = null;
		        PreparedStatement pstmt = null;
		        PreparedStatement pstmt2 = null;
		        
		        try{
		        	if (txtName.getText().equals("")) {
	            		JOptionPane.showMessageDialog(null, "이름을 입력하세요.");
	            		return;
	            	}
	            	if (txtPrice.getText().equals("")) {
	            		JOptionPane.showMessageDialog(null, "가격를 입력하세요.");
	            		return;
	            	}
		        	
		            // 1. 드라이버 로딩
		            Class.forName("com.mysql.cj.jdbc.Driver");
		
		            // 2. 연결하기
		            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		            conn = DriverManager.getConnection(url, "root", "1234qwer");
		
		            // 3. SQL 쿼리 준비
		            String sql = "INSERT INTO inventory VALUES (?, ?)";
		            pstmt = conn.prepareStatement(sql);
		            String sql2 = "select * from inventory where Name = ?";
		            pstmt2 = conn.prepareStatement(sql2);
		            
		            pstmt2.setString(1, name);
		         
		            ResultSet rs = pstmt2.executeQuery();
		            String a = null;
		
		            // 4. 데이터 binding
		            pstmt.setString(1, name);
		            pstmt.setString(2, price);
		            
		            while(rs.next()) {
			               a = rs.getString("Name"); //sql에 있는  이름
			        }
		            if(name.equals(a)){
		            	JOptionPane.showMessageDialog(null, "이미 존재하는 상품입니다.");
		            }else {
		            	int count = pstmt.executeUpdate();
			            if( count == 0 ){
			                System.out.println("데이터 입력 실패");
			            }
			            else{
			                System.out.println("데이터 입력 성공");
			                dtm = (DefaultTableModel) table.getModel();
							dtm.addRow(new Object[]{name,price});
			                JOptionPane.showMessageDialog(null, "재고 등록이 완료되었습니다.");
			                       
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
		btnAdd.setBounds(283, 81, 109, 23);
		contentPane.add(btnAdd);
		
		JLabel lblName = new JLabel("이름");
		lblName.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblName.setBounds(242, 11, 57, 18);
		contentPane.add(lblName);
		
		JLabel lblPrice = new JLabel("가격");
		lblPrice.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblPrice.setBounds(242, 42, 57, 18);
		contentPane.add(lblPrice);
		
		btnRemove = new JButton("삭제");
		btnRemove.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Connection conn;
					java.sql.Statement st;
					 Class.forName("com.mysql.cj.jdbc.Driver");
			         String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			         conn = DriverManager.getConnection(url,"root","1234qwer");
			         st = conn.createStatement();
					ResultSet res = ((java.sql.Statement) st).executeQuery("SELECT * FROM inventory WHERE Name='"+txtName.getText()+"'");
					if(res.next()) {
						((java.sql.Statement) st).executeUpdate("delete from inventory where Name='"+txtName.getText()+"'"); 
						JOptionPane.showMessageDialog(null, "제거되었습니다.");
						
					} else {
						JOptionPane.showMessageDialog(null, "이름을 정확하게 입력해주세요.!");
					}
					} catch(Exception e1) {
						e1.printStackTrace();
					}
			}
			
			
		});
		btnRemove.setBounds(283, 114, 109, 23);
		contentPane.add(btnRemove);
		
		JButton btnNewButton = new JButton("뒤로");
		btnNewButton.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnNewButton.setBounds(283, 228, 109, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("현황 출력");
		btnNewButton_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dtm = (DefaultTableModel) table.getModel();
				int rowCount = dtm.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
				    dtm.removeRow(i);
				}
				Dbconnection db = new Dbconnection();
		         Connection conn = null;
			     PreparedStatement pstmt = null;
			     try {
			        	conn = db.Db();
			        	String sql = "select Name,Price from inventory";
			        	PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
				        ResultSet rs = pst.executeQuery();
				        while(rs.next()) {
				               dtm.addRow(new Object[]{rs.getString("Name"),rs.getString("Price")});
				        }
				        
			            }catch(Exception ex) {
				               System.out.println(ex.getMessage());
				            }
				            }

				});
		btnNewButton_1.setBounds(283, 188, 109, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("수정");
		btnNewButton_2.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = txtName.getText(); 
				String price = txtPrice.getText();
				
				Connection conn = null;
				Statement stmt = null;
		        PreparedStatement pstmt = null;
		        ResultSet rs = null;
		       
		        int point = 0;
		        try{
		            // 1. 드라이버 로딩
		            Class.forName("com.mysql.cj.jdbc.Driver");

		            // 2. 연결하기
		            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		            conn = DriverManager.getConnection(url, "root", "1234qwer");
		         // 3. 쿼리 수행을 위한 Statement 객체 생성
		            stmt = conn.createStatement();
		            // 3. SQL 쿼리 준비
		            String sql = "UPDATE inventory set Price = ? where Name = ?";
		             
		            pstmt = conn.prepareStatement(sql);
		            // 4. 데이터 binding
		            pstmt.setString(1, price);
		            pstmt.setString(2, name);
		           
		            // 5. 쿼리 실행 및 결과 처리
		            int count = pstmt.executeUpdate();
		            
		            if( count == 0 ){
		                System.out.println("데이터 입력 실패");
		                JOptionPane.showMessageDialog(null, "제품이 존재하지 않습니다.");
		            }
		            else{
		                System.out.println("데이터 입력 성공");
		                JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
		               
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
		btnNewButton_2.setBounds(283, 147, 109, 23);
		contentPane.add(btnNewButton_2);
	}
}
