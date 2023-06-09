package Database_Managment.Listener.Dialogs.Options_AddWindow;

import Database_Managment.Column;
import Database_Managment.Global;
import Database_Managment.SQL.LiteSQL;
import Database_Managment.Standard.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddTable extends Standard_Dialog {

    private final Standard_TextField textField_TableName;

    public static Standard_Dialog INSTANCE;

    private final Frame owner;

    private ArrayList<Standard_Button> columnButtons;

    public AddTable(Frame owner) {
        super(owner);
        this.owner = owner;
        INSTANCE = this;
        setTitle("Create Table");

        Standard_Label label_TableName = new Standard_Label("Table name:");
        textField_TableName = new Standard_TextField();
        label_TableName.setPreferredSize(new Dimension(80,20));
        textField_TableName.setPreferredSize(new Dimension(80,20));
        setLayout(new BorderLayout());
        Standard_Panel panel_TableName = new Standard_Panel(new FlowLayout());
        panel_TableName.add(label_TableName);
        panel_TableName.add(textField_TableName);
        add(panel_TableName, BorderLayout.NORTH);

        Standard_Panel panel_Center = new Standard_Panel(new BorderLayout());
        Standard_Label label_addColumn = new Standard_Label("Add Column");
        Standard_Button button_addColumn = new Standard_Button("Create a column");
        button_addColumn.addActionListener(e -> {
            this.dispose();
            new AddColumn(owner, 1);
        });
        label_addColumn.setPreferredSize(new Dimension(80,20));
        button_addColumn.setPreferredSize(new Dimension(120,20));
        Standard_Panel panel_AddColumn = new Standard_Panel(new FlowLayout());
        panel_AddColumn.add(label_addColumn);
        panel_AddColumn.add(button_addColumn);
        panel_Center.add(panel_AddColumn, BorderLayout.CENTER);

        columnButtons = new ArrayList<>();
        if (AddWindow.COLUMNS.size() > 0) {
            Standard_Panel all_Columns = new Standard_Panel(new FlowLayout());
            for (Column c : AddWindow.COLUMNS) {
                Standard_Button s = new Standard_Button(c.columnName);
                s.setBorder(BorderFactory.createLineBorder(Global.LINES,2));
                s.setFocusable(false);
                s.setBackground(Global.BACKGROUND_2);
                s.setPreferredSize(new Dimension(80, 20));
                s.addActionListener(e -> {
                    columnButtons.remove(s);
                    AddWindow.COLUMNS.remove(c);
                    all_Columns.remove(s);
                    repaint();
                    revalidate();
                });
                all_Columns.add(s);
                columnButtons.add(s);
            }
            panel_Center.add(all_Columns,BorderLayout.SOUTH);
        }

        add(panel_Center, BorderLayout.CENTER);

        Standard_Button cancel = new Standard_Button("Cancel");
        cancel.setPreferredSize(new Dimension(80,40));
        cancel.addActionListener(e -> {
            this.dispose();
            AddWindow.COLUMNS.clear();
        });
        Standard_Button create = new Standard_Button("Create");
        create.addActionListener(e -> {
            if (checkValidation()){
                Global.selected = textField_TableName.getText();
                this.dispose();
            }
        });
        create.setPreferredSize(new Dimension(80,40));
        Standard_Panel panel_buttons = new Standard_Panel(new FlowLayout());
        panel_buttons.add(cancel);
        panel_buttons.add(create);
        add(panel_buttons, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(240,120));
        pack();
        setVisible(true);

    }
    private boolean checkValidation(){

        ArrayList<String> columnNames = new ArrayList<>();
        if (textField_TableName.getText().equals("") || textField_TableName.getText() == null){
            JOptionPane.showMessageDialog(owner,"Table Name shouldn't be empty!","Error 203",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (AddWindow.COLUMNS.isEmpty()){
            JOptionPane.showMessageDialog(owner,"You need at least one column!","Error 205",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int primaryKeyCounter=0;
        for (Column c:AddWindow.COLUMNS) {
            if (c.autoIncrement) {
                if (getAmountPrimaryKeys() > 1) {
                    JOptionPane.showMessageDialog(owner, "Multiple keys with autoincrement constraints aren't allowed!", "Error 202", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            if (c.primaryKey){
                primaryKeyCounter++;
            }
            columnNames.add(c.columnName);
        }
        if (primaryKeyCounter==0){
            JOptionPane.showMessageDialog(owner,"You need at least one primary key!","Error 206",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Set<String> findingDuplicate = new HashSet<>(columnNames);
        if (findingDuplicate.size()!=columnNames.size()){
            JOptionPane.showMessageDialog(owner,"Column names have to be unique!","Error 207",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        createTable();
        return true;
    }
    private void createTable(){
        StringBuilder arg = new StringBuilder("CREATE TABLE IF NOT EXISTS " + textField_TableName.getText() + "(");

        int counter1 = 0;
        for (Column c:AddWindow.COLUMNS) {
            counter1++;
            arg.append(c.columnName).append(" ").append(c.dataType);

            if (getAmountPrimaryKeys() == 1){
                if (c.primaryKey){
                    arg.append(" PRIMARY KEY");
                }
                if (c.autoIncrement){
                    arg.append(" AUTOINCREMENT");
                }
            }
            if (c.notNull){
                arg.append(" NOT NULL");
            }
            if (counter1 < AddWindow.COLUMNS.size()){
                arg.append(",");
            }
        }

        if (getAmountPrimaryKeys()>1){
            int counter = 0;
            arg.append(", PRIMARY KEY (");
            for (Column c:getPrimaryColumns()) {
                counter++;
                arg.append(c.columnName);
                if (c.autoIncrement){
                    arg.append(" AUTOINCREMENT");
                }
                if (counter < getAmountPrimaryKeys()){
                    arg.append(",");
                }
            }
            arg.append(")");
        }

        if (getAmountForeignKeys() > 0){
            for (Column c:getForeignColumns()) {
                arg.append(", FOREIGN KEY (");
                arg.append(c.columnName).append(") ");
                arg.append("REFERENCES ").append(c.foreignTableName).append("(").append(c.foreignColumnName).append(")");
            }
        }

        arg.append(")");

        System.out.println(arg);
        LiteSQL.onUpdate(arg.toString());

        AddWindow.COLUMNS.clear();

    }

    private int getAmountPrimaryKeys(){
        int primaryKeys = 0;
        for (Column c:AddWindow.COLUMNS) {
            if (c.primaryKey) {
                primaryKeys++;
            }
        }
        return primaryKeys;
    }

    private int getAmountForeignKeys(){
        int foreignKeys = 0;
        for (Column c:AddWindow.COLUMNS) {
            if (c.foreignKey) {
                foreignKeys++;
            }
        }
        return foreignKeys;
    }

    private ArrayList<Column> getPrimaryColumns(){
        ArrayList<Column> columnPrimaryKey = new ArrayList<>();
        for (Column c:AddWindow.COLUMNS) {
            if (c.primaryKey) {
                columnPrimaryKey.add(c);
            }
        }
        return columnPrimaryKey;
    }

    private ArrayList<Column> getForeignColumns(){
        ArrayList<Column> columnForeignKey = new ArrayList<>();
        for (Column c:AddWindow.COLUMNS) {
            if (c.foreignKey) {
                columnForeignKey.add(c);
            }
        }
        return columnForeignKey;
    }

}
