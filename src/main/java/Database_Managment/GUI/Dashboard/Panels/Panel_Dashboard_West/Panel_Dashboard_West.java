package Database_Managment.GUI.Dashboard.Panels.Panel_Dashboard_West;

import Database_Managment.Global;
import Database_Managment.SQL.LiteSQL;
import Database_Managment.Standard.Standard_Label;
import Database_Managment.Standard.Standard_Panel;
import Database_Managment.Standard.Standard_ScrollPane;
import Database_Managment.Standard.Standard_Table;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Panel_Dashboard_West extends Standard_Panel {

    public Panel_Dashboard_West() {
        try {
           String tableCount = (LiteSQL.onQuery("SELECT COUNT(*) FROM sqlite_schema WHERE " +
                    "type ='table' AND " +
                    "name NOT LIKE 'sqlite_%'").getString(1));

           if (Integer.parseInt(tableCount)==0){
               Standard_Label test = new Standard_Label("No tables created");
               test.setFont(new Font("Sans Serif", Font.BOLD, 48));
               test.setPreferredSize(new Dimension(600,400));
               add(test);
           }else {

               try {
                   PreparedStatement st = LiteSQL.prepareStatement("SELECT name FROM pragma_table_info('" + Global.selected + "') order by cid");
                   ResultSet rs = st.executeQuery();

                   String[] columnNames = new String[LiteSQL.onQuery("SELECT COUNT(*) FROM pragma_table_info('" + Global.selected + "')").getInt(1)];
                   int i = 0;
                   while (rs.next()){
                       columnNames[i] = rs.getString("name");
                       i++;
                   }

                   st = LiteSQL.prepareStatement("SELECT * FROM " + Global.selected);
                   rs = st.executeQuery();

                   Object[][] data = new Object[LiteSQL.onQuery("SELECT COUNT(*) FROM " + Global.selected).getInt(1)][columnNames.length];
                   int iteratorRows = 0;
                   while (rs.next()){
                       int iteratorColumn = 0;
                       for (String column:columnNames) {
                           data[iteratorRows][iteratorColumn] = rs.getString(column);
                           iteratorColumn++;
                       }
                       iteratorRows++;
                   }

                   Standard_Table table = new Standard_Table(data, columnNames);
                   table.setPreferredSize(new Dimension(600,400));
                   Standard_ScrollPane pane = new Standard_ScrollPane(table);
                   add(pane);

               }catch (SQLException e){
                   System.out.println("LINE 56");
                   e.printStackTrace();
               }

           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
