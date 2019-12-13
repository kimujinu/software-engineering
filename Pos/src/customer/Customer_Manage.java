package customer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;

import main.Main;
import DBconnection.Dbconnection;
import java.awt.Font;

public class Customer_Manage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JLabel lblNewLabel;
	private JButton button;
	private DefaultTableModel model = new DefaultTableModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer_Manage frame = new Customer_Manage();
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
	public Customer_Manage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 49, 263, 241);
		getContentPane().add(scrollPane);
		
		
		model.addColumn("전화번호");
		model.addColumn("이름");
		model.addColumn("포인트 현황");
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("등록");
		btnNewButton.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer_Join join = new Customer_Join();
				join.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(306, 37, 111, 39);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("수정");
		btnNewButton_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer_Update update = new Customer_Update();
				update.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(306, 86, 111, 39);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("삭제");
		btnNewButton_2.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer_Delete delete = new Customer_Delete();
				delete.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setBounds(306, 135, 111, 39);
		contentPane.add(btnNewButton_2);
		
		lblNewLabel = new JLabel("회원 현황");
		lblNewLabel.setFont(new Font("함초롬돋움", Font.BOLD, 18));
		lblNewLabel.setBounds(108, 10, 111, 29);
		contentPane.add(lblNewLabel);
		
		button = new JButton("현황 출력");
		button.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		button.addActionListener(new ActionListener() {
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
			        	String sql = "select phone,Name,Point from customer";
			        	PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
				        ResultSet rs = pst.executeQuery();
				        while(rs.next()) {
				               model.addRow(new Object[]{rs.getString("phone"),rs.getString("Name"),rs.getString("Point")});
				        }
				        
			            }catch(Exception ex) {
				               System.out.println(ex.getMessage());
				            }
				            }

				});
		button.setBounds(306, 184, 111, 39);
		contentPane.add(button);
		
		JButton button_1 = new JButton("뒤로가기");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_1.setFont(new Font("함초롬돋움", Font.BOLD, 15));
		button_1.setBounds(306, 237, 111, 39);
		contentPane.add(button_1);
		
		
		
		
		
	}
}
