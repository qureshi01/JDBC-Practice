import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "Harry@01");
            Statement st = con.createStatement();
            int x = st.executeUpdate("CREATE TABLE employee1 (eid INT, ename VARCHAR(40), dept VARCHAR(40), sal DECIMAL(10,2))");
            System.out.println(x);
            System.out.println("New Table created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
