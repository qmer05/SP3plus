import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        StreamingService streamingService = new StreamingService();
        streamingService.startMenu();
        /*
        DataValidator dv = new DataValidator();
        DataBaseIO db = new DataBaseIO();
        TextUI ui = new TextUI();

        ArrayList<String> usernameData = db.readUsernameData();

        String userinput = ui.getInput("Please enter a new username: ");

        dv.checkRegisterUsername(usernameData,userinput);

         */

    }
}