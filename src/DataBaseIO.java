import java.sql.*;
import java.util.ArrayList;

public class DataBaseIO {

    TextUI ui = new TextUI();

    // database URL
    static final String DB_URL = "jdbc:mysql://localhost/my_streaming";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "ugV37ajk!";

    public ArrayList<User> readUserData(String path) {
        return null;
    }

    public void saveUserData(String newName, String newPassword) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO USER (Username, password) VALUES ( ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setString(2, newPassword);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ui.displayMessage("Brugeren er gemt");
            } else {
                ui.displayMessage("Mislykkes at gemme brugeren");
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readMovieData(String path, int length) {
        return null;
    }

    public ArrayList<String> readSeriesData(String path, int length) {
        return null;
    }

    public ArrayList<String> readUsernameData() {

        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<String> usernameData = new ArrayList<>();

        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT Username FROM user";
            stmt = conn.prepareStatement(sql);

            // Assigning the ResultSet to the local variable
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("Username");
                usernameData.add(name);
            }

            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return usernameData;
    }
}
