package Database_Managment.Listener.Dialogs.Options_AddWindow;

import Database_Managment.Global;
import Database_Managment.SQL.LiteSQL;
import Database_Managment.Standard.Standard_Dialog;
import Database_Managment.Standard.Standard_Label;
import Database_Managment.Standard.Standard_Panel;
import Database_Managment.Standard.Standard_TextField;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddRow extends Standard_Dialog {

    public AddRow(Frame owner) {
        super(owner);
        setTitle("Add a row");

        try {
            PreparedStatement st = LiteSQL.prepareStatement("SELECT * FROM pragma_table_info('" + Global.selected + "') WHERE pk <> 1 order by cid");
            ResultSet rs = st.executeQuery();
            int columnCount = LiteSQL.onQuery("SELECT COUNT(*) FROM pragma_table_info('" + Global.selected + "') WHERE pk <> 1").getInt(1);
            String[] columnNames = new String[columnCount];
            String[] columnType = new String[columnCount];
            String[] isNotNull = new String[columnCount];
            String[] isPrimaryKey = new String[columnCount];
            int i = 0;
            while (rs.next()) {
                columnNames[i] = rs.getString("name");
                columnType[i] = rs.getString("type");
                if (rs.getInt("notnull") == 1) {
                    isNotNull[i] = ", not Null";
                } else {
                    isNotNull[i] = "";
                }
                if (rs.getInt("pk") > 1) {
                    isPrimaryKey[i] = ", primary key";
                } else {
                    isPrimaryKey[i] = "";
                }
                i++;
            }
            Standard_Panel center = new Standard_Panel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            for (int j = 0; j < i; j++) {
                Standard_Panel panel = new Standard_Panel(new FlowLayout());
                Standard_Label label = new Standard_Label(columnNames[j] + " (" + columnType[j].toLowerCase() + isNotNull[j] + isPrimaryKey[j] + ") : ");
                Standard_TextField textField = new Standard_TextField();
                label.setPreferredSize(new Dimension(260, 40));
                textField.setPreferredSize(new Dimension(100, 40));
                panel.add(label);
                panel.add(textField);
                center.add(panel, gbc);
                gbc.gridy++;
            }

            add(center);
            pack();
            setVisible(true);

        } catch (SQLException sqlException) {
            System.out.println("Line 28 AddRow.java");
            sqlException.printStackTrace();
        }
    }
}
