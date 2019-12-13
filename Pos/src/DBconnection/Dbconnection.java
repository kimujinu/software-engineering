package DBconnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Dbconnection {
	   private static Connection con;
	   private static java.sql.Statement st;
	   private ResultSet rs;
	   
	   public static Connection Db(){
	      try{
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	         con = DriverManager.getConnection(url,"root","1234qwer");
	    	 //DB 연동 안내사항 (16번째 줄을 아래 글을 보고 수정하면 회원가입/로그인 DB연동이 됩니다.)
	    	 /* 
	    	  jdbc:mysql://localhost:3306/jap?useUnicode=true&characterEncoding=UTF-8","root","apmsetup"
	    	  localhost:3306 => 그대로 놔둡니다.
	    	  jap => 자신의 데이터베이스이름으로 변경
	    	  root => phpmyadmin의 '아이디'
	    	  apmsetup => phpmyadmin의 '비밀번호'
	    	 */
	         st = con.createStatement();
	         System.out.println("연결성공");
	      }catch(Exception e){
	         System.out.println("Erro ="+e);
	      }
	      return con;
	   }
}
