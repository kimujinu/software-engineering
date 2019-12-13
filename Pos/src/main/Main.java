package main;

import handover.Handover;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import DBconnection.Dbconnection;
import Inventory.Inventory_manage;
import customer.Customer_Join;
import customer.Customer_Manage;
import customer.Customer_point;

public class Main extends JFrame {
	public static JTable table1;
	public static JTextField totalprice;
	public static DefaultTableModel model1 = new DefaultTableModel();
	private DefaultTableModel model2 = new DefaultTableModel();
	public static double price;
	private JTable table_1;
	public static String newLineChar = System.getProperty("line.separator");
	public static int Receipt_Txt_num = 1;  
	public static String Receipt_Txt_Value = 
	                  "   영 수 증" + newLineChar 
	                  + "    상호: 부경 Cafe(부경대점)" + newLineChar
	                  + "    주소: 부산 남구" + newLineChar
	                  + "    전화번호: 051-0000-0000" + newLineChar
	                  + "==========================" + newLineChar
	                  + "    물품 & 가격" + newLineChar
	                  + "==========================" + newLineChar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		getContentPane().setLayout(null);
		setBounds(200, 200, 500, 400);
		
		model1.addColumn("이름");
		model1.addColumn("가격");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 49, 263, 241);
		getContentPane().add(scrollPane);
		table1 = new JTable(model1);
		scrollPane.setViewportView(table1);
		
		model2.addColumn("이름");
		model2.addColumn("가격");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(307, 50, 154, 129);
		getContentPane().add(scrollPane_1);
		table_1 = new JTable(model2);
		model2.addRow(new Object[] {"&","0"});
		scrollPane_1.setViewportView(table_1);
		
		Dbconnection db = new Dbconnection();
        Connection conn = null;
	     PreparedStatement pstmt = null;
	     try {
	        	conn = db.Db();
	        	String sql = "select Name,Price from inventory";
	        	PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
		        ResultSet rs = pst.executeQuery();
		        while(rs.next()) {
		               model2.addRow(new Object[]{rs.getString("Name"),rs.getString("Price")});
		        }
		        
	            }catch(Exception ex) {
		               System.out.println(ex.getMessage());
		            }
	     table_1.setCellSelectionEnabled(true);
	     table_1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getValueIsAdjusting()){ 
					Object[] object = new Object[] {table_1.getValueAt(table_1.getSelectedRow(),0).toString(),table_1.getValueAt(table_1.getSelectedRow(),1).toString()};
		            Object[] check = new Object[] {"&","0"};
		            if(Objects.deepEquals(object, check) == true) {
		               return; }
		            else {
		            model1.addRow(object);
		            price += Double.parseDouble(table_1.getValueAt(table_1.getSelectedRow(),1).toString());
		            totalprice.setText("총 가격은 : "+ price);
		            Receipt_Txt_Value += Receipt_Txt_num + table_1.getValueAt(table_1.getSelectedRow(),0).toString() + table_1.getValueAt(table_1.getSelectedRow(),1).toString() + newLineChar;
		            Receipt_Txt_num++;
		            return;
		            }
				
				}
			}
	     });
		           
		JButton btnOrder = new JButton("주문");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					Customer_point point_acc = new Customer_point();
					point_acc.setVisible(true);
			}
		});
		btnOrder.setBounds(307, 189, 121, 23);
		getContentPane().add(btnOrder);
		
		JButton btnRemove = new JButton("포인트사용");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					Point point_use = new Point();
					point_use.setVisible(true);
			}
		});
		btnRemove.setBounds(307, 222, 121, 23);
		getContentPane().add(btnRemove);
		
		JButton btnInit = new JButton("초기화");
		btnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model1 = (DefaultTableModel) table1.getModel();
				int rowCount = model1.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
				    model1.removeRow(i);
				}
				price = 0;
				totalprice.setText("총 주문금액 : " + price);
	            //Receipt_Txt_Value 초기화
	                Receipt_Txt_Value = null;
	                Receipt_Txt_Value = 
	                   "   영 수 증" + newLineChar 
	                   + "    상호: 부경 Cafe(부경대점)" + newLineChar
	                   + "    주소: 부산 남구" + newLineChar
	                   + "    전화번호: 051-0000-0000" + newLineChar
	                   + "==========================" + newLineChar
	                   + "    물품 & 가격" + newLineChar
	                   + "==========================" + newLineChar;
	                Receipt_Txt_num = 1;
			}
		});
		btnInit.setBounds(307, 255, 121, 23);
		getContentPane().add(btnInit);
		
		totalprice = new JTextField();
		totalprice.setBounds(12, 300, 263, 29);
		getContentPane().add(totalprice);
		totalprice.setColumns(10);
		
		JButton btnNewButton = new JButton("상품관리");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				   Inventory_manage manage = new Inventory_manage();
 				   manage.setVisible(true);
 				   
			}
		});
		btnNewButton.setBounds(12, 16, 97, 23);
		getContentPane().add(btnNewButton);
		
		JButton button = new JButton("회원관리");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer_Manage manage = new Customer_Manage();
				manage.setVisible(true);
				
			}
		});
		button.setBounds(121, 16, 97, 23);
		getContentPane().add(button);
		
		JButton button_2 = new JButton("인수인계");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Handover over = new Handover();
				over.setVisible(true);
				
			}
		});
		button_2.setBounds(230, 16, 97, 23);
		getContentPane().add(button_2);
		
		JButton button_1 = new JButton("영수증 조회");
	      button_1.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            ReceiptRead read = new ReceiptRead();
	            read.setVisible(true);
	                   
	         }
	      });
	      button_1.setFont(new Font("함초롬돋움", Font.BOLD, 11));
	      button_1.setBounds(307, 321, 121, 23);
	      getContentPane().add(button_1);
	      
	      JButton button_3 = new JButton("새로고침");
	      button_3.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		model2 = (DefaultTableModel) table_1.getModel();
				int rowCount = model2.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
				    model2.removeRow(i);
				}
				model2.addRow(new Object[] {"&","0"});
				 Dbconnection db = new Dbconnection();
		         Connection conn = null;
			     PreparedStatement pstmt = null;
			     try {
			        	conn = db.Db();
			        	String sql = "select Name,Price from inventory";
			        	PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
				        ResultSet rs = pst.executeQuery();
				        while(rs.next()) {
				               model2.addRow(new Object[]{rs.getString("Name"),rs.getString("Price")});
				        }
				        
			            }catch(Exception ex) {
				               System.out.println(ex.getMessage());
				            }
				            }

				});
	      button_3.setBounds(307, 288, 121, 23);
	      getContentPane().add(button_3);
		
	}
}
