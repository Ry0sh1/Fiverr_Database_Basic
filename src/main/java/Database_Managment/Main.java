package Database_Managment;

import Database_Managment.GUI.Dashboard.Frame_Dashboard;
import Database_Managment.SQL.LiteSQL;

public class Main {

    public static void main(String[] args) {
        LiteSQL.connect();
        new Frame_Dashboard();
    }

}
