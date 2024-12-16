package databaseproject;

/**
 * @author ethanshim
 */

public interface JFramelogin2 {
    databaseproject.JDBC jdbc = new databaseproject.JDBC();

    class JDBC extends JFrameLogin{
        static String url = "jdbc:mysql://localhost:3306/jdbc";
        static String username = "root";
        static String password = "";
        public String userEmail;
    }

    static void main(String[] args){}
}
