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

import main.Main;
import DBconnection.Dbconnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;

public class Customer_Update extends JFrame {

	private JPanel contentPane;
	private JTextField id_tx;
	private JTextField name_tx;
	private JTextField newname_tx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer_Update frame = new Customer_Update();
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
	public Customer_Update() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("회원 정보수정");
		label.setFont(new Font("함초롬돋움", Font.BOLD, 18));
		label.setBounds(175, 10, 129, 64);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("전화번호 :");
		lblNewLabel.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel.setBounds(59, 87, 84, 18);
		contentPane.add(lblNewLabel);
		
		id_tx = new JTextField();
		id_tx.setBounds(155, 84, 149, 21);
		contentPane.add(id_tx);
		id_tx.setColumns(10);
		id_tx.addKeyListener(new KeyAdapter() {
		
			public void keyReleased(KeyEvent arg0) {
				Connection con = null;
	            con = Dbconnection.Db(); //sql 연결하기
	            try {
	               String sql = "SELECT * FROM customer where phone=?"; //join1테이블에 아이디가 있는지 비교하기
	               PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
	               pst.setString(1,id_tx.getText()); //아이디 입력
	               ResultSet rs = pst.executeQuery();
	               
	               while(rs.next())
	               {
	                  name_tx.setText(rs.getString("Name")); //비밀번호 출력
	               }
	               pst.close();
	               }
	               catch(Exception ex) {
	                  ex.printStackTrace();
	               }
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("기존 이름:");
		lblNewLabel_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel_1.setBounds(59, 127, 84, 21);
		contentPane.add(lblNewLabel_1);
		
		name_tx = new JTextField();
		name_tx.setBounds(155, 127, 149, 21);
		contentPane.add(name_tx);
		name_tx.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("새 이름:");
		lblNewLabel_2.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		lblNewLabel_2.setBounds(69, 172, 84, 18);
		contentPane.add(lblNewLabel_2);
		
		newname_tx = new JTextField();
		newname_tx.setBounds(155, 174, 149, 21);
		contentPane.add(newname_tx);
		newname_tx.setColumns(10);
		
		JButton btnNewButton = new JButton("수정");
		btnNewButton.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String new_name = newname_tx.getText(); 
				String phone_num = id_tx.getText();
				
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
		            String sql = "UPDATE customer set Name = ? where phone = ?";
		             
		            pstmt = conn.prepareStatement(sql);
		            // 4. 데이터 binding
		            pstmt.setString(1, new_name);
		            pstmt.setString(2, phone_num);
		           
		            // 5. 쿼리 실행 및 결과 처리
		            int count = pstmt.executeUpdate();
		            
		            if( count == 0 ){
		                System.out.println("데이터 입력 실패");
		                
		            }
		            else{
		                System.out.println("데이터 입력 성공");
		                JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
		                Main main = new Main();
		                main.setVisible(true);
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
		btnNewButton.setBounds(110, 215, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("뒤로");
		btnNewButton_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer_Manage manage = new Customer_Manage();
				manage.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(232, 215, 97, 23);
		contentPane.add(btnNewButton_1);
	}

}
