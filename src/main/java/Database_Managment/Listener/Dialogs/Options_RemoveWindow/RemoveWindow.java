package Database_Managment.Listener.Dialogs.Options_RemoveWindow;

import Database_Managment.Column;
import Database_Managment.Listener.Dialogs.Options_AddWindow.AddColumn;
import Database_Managment.Listener.Dialogs.Options_AddWindow.AddTable;
import Database_Managment.Standard.Standard_Button;
import Database_Managment.Standard.Standard_Dialog;
import Database_Managment.Standard.Standard_Label;
import Database_Managment.Standard.Standard_Panel;
import Database_Managment.Listener.Data_ActionListener;

import java.awt.*;
import java.util.ArrayList;


public class RemoveWindow extends Standard_Dialog {

    public static ArrayList<Column> COLUMNS = new ArrayList<>();
    public RemoveWindow(Frame owner) {
        super(owner);
        setTitle("Remove");

        Standard_Panel contentPanel = new Standard_Panel(new BorderLayout());

        Standard_Panel headerPanel = new Standard_Panel(new FlowLayout());
        Standard_Label header = new Standard_Label("Select what you want to remove!");
        header.setPreferredSize(new Dimension(320,40));
        headerPanel.add(header);

        Standard_Panel buttonPanel = new Standard_Panel(new FlowLayout());
        ArrayList<Standard_Button> buttons = new ArrayList<>();
        Standard_Button cancel = new Standard_Button("Cancel");
        cancel.addActionListener(e -> this.dispose());
        Standard_Button table = new Standard_Button("Table");
        table.addActionListener(e -> {
            dispose();
            new RemoveTable(owner);
        });
        Standard_Button column = new Standard_Button("Column");
        column.addActionListener(e -> {
            dispose();
            new RemoveColumn(owner);
        });
        Standard_Button row = new Standard_Button("Row");
        row.addActionListener(e -> {
            dispose();
            new RemoveRow(owner);
        });
        cancel.setName("cancel");
        table.setName("table");
        column.setName("column");
        row.setName("row");
        buttons.add(cancel);
        buttons.add(table);
        buttons.add(column);
        buttons.add(row);

        for (Standard_Button b : buttons) {
            b.addActionListener(new Data_ActionListener(b.getName()));
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