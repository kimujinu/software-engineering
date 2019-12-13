package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Insert {
	public static void main(String[] args) {
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		System.out.print(date.format(today));
        //insert(1, "김진우");
    }

    public static void insert(int id, String name){
    	
    	Connection conn = null;
        PreparedStatement pstmt = null;
        
        
        try{
            // 1. 드라이버 로딩
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "1234qwer");


            // 3. SQL 쿼리 준비
            String sql = "INSERT INTO testing VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);


            // 4. 데이터 binding
            pstmt.setInt(1, id);
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));

            // 5. 쿼리 실행 및 결과 처리
            int count = pstmt.executeUpdate();
            if( count == 0 ){
                System.out.println("데이터 입력 실패");
            }
            else{
                System.out.println("데이터 입력 성공");
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
}
