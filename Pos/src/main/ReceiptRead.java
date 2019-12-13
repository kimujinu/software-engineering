package main;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;

public class ReceiptRead extends JFrame implements ActionListener {
   JTextArea tarea; JButton loadbtn; JFileChooser fchooser;
   
   public ReceiptRead() { super("Lab.X File Reader/Writer");
   JPanel panel = new JPanel();
   loadbtn = new JButton("영수증 찾기");
   loadbtn.addActionListener(this);
   
   panel.add(loadbtn);
   getContentPane().add("North", panel);
   
   JButton btnNewButton = new JButton("뒤로가기");
   btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
         dispose();
      }
   });
   panel.add(btnNewButton);
   JScrollPane spane = new JScrollPane(tarea);
   getContentPane().add(spane,"Center");
   
   tarea = new JTextArea("", 10, 50);
   tarea.setBackground(new Color(220,255,255));
   tarea.setMargin(new Insets(5,5,5,5)); //awt
   getContentPane().add("Center", tarea);
   
   FileNameExtensionFilter fxfilter = new FileNameExtensionFilter("Text Files: txt, java","txt","java");
   fchooser = new JFileChooser();
   fchooser.setFileFilter(fxfilter);
   
   setSize(500,800); setVisible(true);
   }
   public static void main(String[] args) {
      new ReceiptRead();
   }
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == loadbtn) {
         int fc_result = fchooser.showOpenDialog(tarea);
         // fc_result : JFileChooser.APPROVE_OPTION, CANCEL_, ERROR_
         if(fc_result == JFileChooser.APPROVE_OPTION) {
            File file = fchooser.getSelectedFile();
            // System.out.println("file name: " + file.getName());
            try {
               BufferedReader br = new BufferedReader (new FileReader(file));
               tarea.setText(""); //clear up
               String line; //input buffer
               while((line = br.readLine())!=null)
                  tarea.append(line+"\n");
               br.close();
            } catch(IOException ex) {}
         }
      }
      else {
         int fc_result = fchooser.showSaveDialog(tarea);
         if(fc_result == JFileChooser.APPROVE_OPTION) {
            File file = fchooser.getSelectedFile();
            // System.out.println("file name: " + file.getName());
            try {
               BufferedWriter bw = new BufferedWriter (new FileWriter(file));
               bw.write(tarea.getText());
               bw.flush();
               bw.close();
            } catch(IOException ex) {}
         }
         
      }
   }

}