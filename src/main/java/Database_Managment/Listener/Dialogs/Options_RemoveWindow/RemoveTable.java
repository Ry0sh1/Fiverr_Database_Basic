package Database_Managment.Listener.Dialogs.Options_RemoveWindow;

import Database_Managment.GUI.Dashboard.Frame_Dashboard;
import Database_Managment.Global;
import Database_Managment.SQL.LiteSQL;
import Database_Managment.Standard.Standard_Button;
import Database_Managment.Standard.Standard_Dialog;
import Database_Managment.Standard.Standard_Label;
import Database_Managment.Standard.Standard_Panel;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveTable extends Standard_Dialog {

    private JComboBox<String> input;

    public RemoveTable(Frame owner) {
        super(owner);
        setTitle("Remove Column");

        Standard_Panel contentPanel = new Standard_Panel(new BorderLayout());

        Standard_Panel headerPanel = new Standard_Panel(new FlowLayout());
        Standard_Label header = new Standard_Label("Select Table!");
        header.setPreferredSize(new Dimension(160,40));

        try {
            int tableCount = LiteSQL.onQuery("SELECT COUNT(*) FROM sqlite_schema WHERE type='table' AND name NOT LIKE 'sqlite_%'").getInt(1);
            String[] allTables = new String[tableCount];
            PreparedStatement statement = LiteSQL.prepareStatement("SELECT name FROM sqlite_schema WHERE type='table' AND name NOT LIKE 'sqlite_%'");
            ResultSet rs = statement.executeQuery();
            int i = 0;
            while (rs.next()) {
                allTables[i] = rs.getString("name");
                i++;
            }
            input = new JComboBox<>(allTables);
            input.setSelectedIndex(0);
            input.setPreferredSize(new Dimension(180, 20));
            headerPanel.add(header);
            headerPanel.add(input);
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        Standard_Panel buttonPanel = new Standard_Panel(new FlowLayout());
        ArrayList<Standard_Button> buttons = new ArrayList<>();
        Standard_Button cancel = new Standard_Button("Cancel");
        cancel.addActionListener(e -> this.dispose());
        Standard_Button go = new Standard_Button("Go");
        go.addActionListener(e -> {
            this.dispose();
            String selected = (String) input.getSelectedItem();
            LiteSQL.onUpdate("DROP TABLE " + (String) input.getSelectedItem());
            if (selected.equals(Global.selected)){
                Global.selectNextTable();
            }
            owner.dispose();
            new Frame_Dashboard();
        });
        buttons.add(go);
        buttons.add(cancel);

        for (Standard_Button b : buttons) {
            b.setPreferredSize(new Dimension(80, 40));
            buttonPanel.add(b);
        }

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);

        pack();
        setVisible(true);

    }

}
