import java.sql.*;

public class TechsageDB {
    
    private Connection conn;
    private Statement stmt;

    public void connectDB() {
        try {
          //  Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mpr", "root", "shubham1332");
            System.err.println("connected");
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}