package handover;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import main.Main;
import DBconnection.Dbconnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;

public class Handover extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JPasswordField password;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Handover frame = new Handover();
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
	public Handover() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("인수인계");
		lblNewLabel.setBounds(89, 10, 71, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("아이디:");
		lblNewLabel_1.setBounds(249, 83, 57, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("비밀번호:");
		lblNewLabel_2.setBounds(249, 143, 57, 15);
		contentPane.add(lblNewLabel_2);
		
		id = new JTextField();
		id.setBounds(306, 80, 116, 21);
		contentPane.add(id);
		id.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(306, 140, 116, 21);
		contentPane.add(password);
		password.setColumns(10);
		
		JButton btnNewButton = new JButton("인수인계");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int parttime = 0;
				int totalprice = 0;
				
				String id1 = id.getText(); // 아이디 입력
		        String pwd = password.getText(); //비밀번호 입력
		         
		         
		         Dbconnection db = new Dbconnection();
		         Connection conn = null;
			     PreparedStatement pstmt = null;
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
			               b = rs.getString("password"); //sql에 있는 비밀번호 출력   //인수인계 버튼클릭시 worktime은 0으로 바뀜 
			        }
			        if(id1.equals(a)) { //아이디 확인하기
			               if(pwd.equals(b)) { //비밀번호 확인하기
			            	   
			            	   
			            	   PreparedStatement pstmt2 = null;
			            	   PreparedStatement pstmt3 = null;
			            	   PreparedStatement pstmt4 = null;
			            	   PreparedStatement pstmt5 = null;
			   		           ResultSet rs2 = null;
			   		           ResultSet rs3 = null;
			   		           ResultSet rs4 = null;
			   		           ResultSet rs5 = null;
			            	   
			            	    String sql2 = "UPDATE property set total = ? where date = ?"; 
					            String sql3 = "select worktime from property";
					            String sql4 = "select total from property";
					            String sql5 = "UPDATE property set worktime = ? where date = ?"; 
					            
					            pstmt2 = conn.prepareStatement(sql2);
					            pstmt3 = conn.prepareStatement(sql3);
					            pstmt4 = conn.prepareStatement(sql4);
					            pstmt5 = conn.prepareStatement(sql5);
					            
					            rs3 = pstmt3.executeQuery();
					            rs4 = pstmt4.executeQuery();
					            
					            while(rs3.next()){
				                	parttime = rs3.getInt(1);
					                System.out.println(parttime);
				                }
					            
					            while(rs4.next()){
					            	totalprice = rs4.getInt(1);
					            	System.out.println(totalprice);
					            }
					            
					            // 4. 데이터 binding
					            pstmt2.setInt(1, parttime+totalprice); //괄호안에 적립될값 넣기 
					            pstmt2.setDate(2, new java.sql.Date(System.currentTimeMillis()));
					            pstmt5.setInt(1, 0);
					            pstmt5.setDate(2, new java.sql.Date(System.currentTimeMillis()));

					            // 5. 쿼리 실행 및 결과 처리
					           
					            int count = pstmt2.executeUpdate();
					            int count2 = pstmt5.executeUpdate();
					            
					            if( count == 0 && count2 == 0){
					                System.out.println("데이터 입력 실패");
					                
					            }
					            else{
					                System.out.println("데이터 입력 성공");
					                JOptionPane.showMessageDialog(null, "인수인계 완료.");
					                dispose();
					            }
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
		btnNewButton.setBounds(249, 209, 97, 23);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 35, 225, 186);
		getContentPane().add(scrollPane);
		
		
		model.addColumn("총 수익");
		model.addColumn("할당 시간 수익");
		model.addColumn("날짜");
		
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_1 = new JButton("수익 현황 출력");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = (DefaultTableModel) table.getModel();
				int rowCount = model.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
				    model.removeRow(i);
				}
				Dbconnection db = new Dbconnection();
		         Connection conn = null;
			     PreparedStatement pstmt = null;
			     try {
			        	conn = db.Db();
			        	String sql = "select total,worktime,date from property";
			        	PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
				        ResultSet rs = pst.executeQuery();
				        while(rs.next()) {
				               model.addRow(new Object[]{rs.getString("total"),rs.getString("worktime"),rs.getString("date")});
				        }
				        
			            }catch(Exception ex) {
				               System.out.println(ex.getMessage());
				            }
				            }

				});
		
		btnNewButton_1.setBounds(50, 228, 129, 23);
		contentPane.add(btnNewButton_1);
		
		JButton button = new JButton("뒤로가기");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		button.setBounds(370, 209, 97, 23);
		contentPane.add(button);
	}
}
