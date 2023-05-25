package Database_Managment.Listener.Dialogs.Options_SwitchTable;

import Database_Managment.Column;
import Database_Managment.GUI.Dashboard.Frame_Dashboard;
import Database_Managment.Global;
import Database_Managment.Listener.Data_ActionListener;
import Database_Managment.Listener.Dialogs.Options_AddWindow.AddColumn;
import Database_Managment.Listener.Dialogs.Options_AddWindow.AddTable;
import Database_Managment.SQL.LiteSQL;
import Database_Managment.Standard.*;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SwitchTableWindow extends Standard_Dialog {

    public static ArrayList<Column> COLUMNS = new ArrayList<>();
    Standard_TextField input;
    public SwitchTableWindow(Frame owner) {
        super(owner);
        setTitle("Select");

        Standard_Panel contentPanel = new Standard_Panel(new BorderLayout());

        Standard_Panel headerPanel = new Standard_Panel(new FlowLayout());
        Standard_Label header = new Standard_Label("Enter table name!");
        header.setPreferredSize(new Dimension(160,40));
        input = new Standard_TextField();
        input.setPreferredSize(new Dimension(160,40));
        headerPanel.add(header);
        headerPanel.add(input);

        Standard_Panel buttonPanel = new Standard_Panel(new FlowLayout());
        ArrayList<Standard_Button> buttons = new ArrayList<>();
        Standard_Button cancel = new Standard_Button("Cancel");
        cancel.addActionListener(e -> this.dispose());
        Standard_Button go = new Standard_Button("Go");
        go.addActionListener(e -> {
            try {
                PreparedStatement statement = LiteSQL.prepareStatement("SELECT name FROM sqlite_schema WHERE " +
                        "type ='table' AND " +
                        "name NOT LIKE 'sqlite_%'");
                ResultSet rs = statement.executeQuery();

                while (rs.next()){
                    if (rs.getString("name").equals(input.getText())){
                        this.dispose();
                        Global.selected = input.getText();
                        owner.dispose();
                        new Frame_Dashboard();
                    }
                }

                if (Global.selected.equals(input.getText())){
                    JOptionPane.showMessageDialog(owner, "Succeed");
                }else {
                    JOptionPane.showMessageDialog(owner, "There is no table with this name in this data bank");
                }

            }catch (SQLException sqlException){
                System.out.println("Error Line 61 SwitchTableWindow");
                sqlException.printStackTrace();
            }

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