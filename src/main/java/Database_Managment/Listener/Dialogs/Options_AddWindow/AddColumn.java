package Database_Managment.Listener.Dialogs.Options_AddWindow;

import Database_Managment.Column;
import Database_Managment.Global;
import Database_Managment.SQL.LiteSQL;
import Database_Managment.Standard.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddColumn extends Standard_Dialog implements ActionListener {

    private Standard_TextField textField_columnName;
    private JComboBox<String> comboBox_dataType;
    private Standard_Checkbox checkBox_primaryKey;
    private Standard_Checkbox checkBox_foreignKey;
    private Standard_TextField textField_foreignKey_tableName;
    private Standard_TextField textField_foreignKey_ColumnName;
    private Standard_Checkbox checkBox_notNull;
    private Standard_Checkbox checkBox_autoincrement;
    private int purpose;
    public AddColumn(Frame owner, int purpose) {
        super(owner);
        setTitle("Add Column");
        this.purpose = purpose;

        Standard_Panel center1 =  new Standard_Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        ArrayList<Standard_Panel> panels = new ArrayList<>();
        panels.add(makeColumnNamePanel());
        panels.add(makeDatatypePanel());
        panels.add(makePrimaryKeyPanel());
        panels.add(makeNotNullPanel());
        panels.add(makeAutoIncrementPanel());

        for (Standard_Panel s : panels) {
            center1.add(s, gbc);
            gbc.gridy++; // Move to the next row
        }

        Standard_Panel center = new Standard_Panel(new BorderLayout());
        center.add(center1, BorderLayout.NORTH);
        center.add(makeForeignKeyPanel(), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
        add(makeButtonPanel(this), BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private Standard_Panel makeColumnNamePanel(){
        Standard_Panel panel = new Standard_Panel(new FlowLayout());
        Standard_Label label_ColumnName = new Standard_Label("Column Name:");
        textField_columnName = new Standard_TextField();
        label_ColumnName.setPreferredSize(new Dimension(100,20));
        textField_columnName.setPreferredSize(new Dimension(80,20));
        panel.add(label_ColumnName);
        panel.add(textField_columnName);
        return panel;
    }

    private Standard_Panel makeDatatypePanel(){
        Standard_Panel panel = new Standard_Panel(new FlowLayout());
        Standard_Label label_dataType = new Standard_Label("Datatype:");
        String[] dataTypes = {"INT","TEXT","REAL","BLOB"};
        comboBox_dataType = new JComboBox<>(dataTypes);
        comboBox_dataType.setSelectedIndex(0);
        label_dataType.setPreferredSize(new Dimension(80,20));
        comboBox_dataType.setPreferredSize(new Dimension(80,20));
        panel.add(label_dataType);
        panel.add(comboBox_dataType);
        return panel;
    }

    private Standard_Panel makePrimaryKeyPanel(){
        Standard_Panel panel = new Standard_Panel(new FlowLayout());
        Standard_Label label_primaryKey = new Standard_Label("Primary Key");
        checkBox_primaryKey = new Standard_Checkbox();
        label_primaryKey.setPreferredSize(new Dimension(80,20));
        panel.add(label_primaryKey);
        panel.add(checkBox_primaryKey);
        return panel;
    }

    private Standard_Panel makeForeignKeyPanel(){
        Standard_Panel panel = new Standard_Panel(new FlowLayout());
        Standard_Label label_foreignKey = new Standard_Label("Foreign Key");
        checkBox_foreignKey = new Standard_Checkbox();
        checkBox_foreignKey.addChangeListener(e->{
            if (checkBox_foreignKey.isSelected()){
                textField_foreignKey_ColumnName.setEnabled(true);
                textField_foreignKey_tableName.setEnabled(true);
            }else {
                textField_foreignKey_ColumnName.setEnabled(false);
                textField_foreignKey_tableName.setEnabled(false);
            }
        });
        label_foreignKey.setPreferredSize(new Dimension(80,20));
        textField_foreignKey_tableName = new Standard_TextField("(TableName)");
        textField_foreignKey_tableName.setPreferredSize(new Dimension(80,20));
        textField_foreignKey_tableName.setEnabled(false);
        textField_foreignKey_ColumnName = new Standard_TextField("(ColumnName)");
        textField_foreignKey_ColumnName.setPreferredSize(new Dimension(80,20));
        textField_foreignKey_ColumnName.setEnabled(false);
        panel.add(label_foreignKey);
        panel.add(checkBox_foreignKey);
        panel.add(textField_foreignKey_tableName);
        panel.add(textField_foreignKey_ColumnName);
        return panel;
    }

    private Standard_Panel makeNotNullPanel(){
        Standard_Panel panel = new Standard_Panel(new FlowLayout());
        Standard_Label label_notNull = new Standard_Label("Not Null");
        checkBox_notNull = new Standard_Checkbox();
        label_notNull.setPreferredSize(new Dimension(80,20));
        panel.add(label_notNull);
        panel.add(checkBox_notNull);
        return panel;
    }

    private Standard_Panel makeAutoIncrementPanel(){
        Standard_Panel panel = new Standard_Panel(new FlowLayout());
        Standard_Label label_autoincrement = new Standard_Label("Autoincrement");
        checkBox_autoincrement = new Standard_Checkbox();
        label_autoincrement.setPreferredSize(new Dimension(100,20));
        panel.add(label_autoincrement);
        panel.add(checkBox_autoincrement);
        return panel;
    }

    public Standard_Panel makeButtonPanel(Standard_Dialog actualFrame){
        Standard_Panel panel = new Standard_Panel(new FlowLayout());
        Standard_Button cancel = new Standard_Button("Cancel");
        cancel.setPreferredSize(new Dimension(80,40));
        cancel.addActionListener(e -> actualFrame.dispose());
        Standard_Button create = new Standard_Button("Create");
        create.addActionListener(this);
        create.setPreferredSize(new Dimension(80,40));
        panel.add(cancel);
        panel.add(create);
        return panel;
    }

    private boolean validInput(){

        String[] noNoWords ={"alter","not","null","primary","key","foreign","table","create","select",
                "where","and","desc","asc","insert","into","add","column","join","left","right","inner"};
        String pattern = "^[a-zA-Z0-9]+$";
        if (textField_columnName.getText()==null || textField_columnName.getText().equals("") || !textField_columnName.getText().matches(pattern)){
            return false;
        }
        for (int i = 0;i < noNoWords.length;i++){
            if (textField_columnName.getText().equalsIgnoreCase(noNoWords[0])){
                return false;
            }
        }
        if (checkBox_foreignKey.isSelected()) {
            if (textField_foreignKey_tableName.getText()==null || textField_foreignKey_tableName.getText().equals("") || textField_foreignKey_ColumnName.getText()==null || textField_foreignKey_ColumnName.getText().equals("")) {
                return false;
            }
        }

        if (purpose==0){
            try {
                int columnCount = LiteSQL.onQuery("SELECT COUNT(*) FROM pragma_table_info('" + Global.selected + "')").getInt(1);
                String[] alreadyExistingColumns = new String[columnCount];
                PreparedStatement stmt = LiteSQL.prepareStatement("SELECT * FROM pragma_table_info('" + Global.selected + "')");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    if (textField_columnName.getText().equals(rs.getString("name"))){
                        return false;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (checkBox_primaryKey.isSelected() || checkBox_autoincrement.isSelected()){
                return false;
            }
        }

        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (validInput()) {
            Column newColumn = new Column(
                    textField_columnName.getText(),
                    comboBox_dataType.getSelectedIndex(),
                    checkBox_primaryKey.isSelected(),
                    checkBox_foreignKey.isSelected(),
                    textField_foreignKey_tableName.getText(),
                    textField_foreignKey_ColumnName.getText(),
                    checkBox_notNull.isSelected(),
                    checkBox_autoincrement.isSelected()
            );

            if (purpose==0){
                StringBuilder arg = new StringBuilder("ALTER TABLE " + Global.selected + " ADD COLUMN " + textField_columnName.getText() + " " + comboBox_dataType.getSelectedItem());
                if (checkBox_notNull.isSelected()){
                    arg.append(" NOT NULL");
                }
                System.out.println(arg);
                LiteSQL.onUpdate(arg.toString());
                this.dispose();
                new AddTable((Frame) this.getOwner());
            }else if (purpose==1){
                AddWindow.COLUMNS.add(newColumn);
                this.dispose();
                new AddTable((Frame) this.getOwner());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Input", "Error 201", JOptionPane.ERROR_MESSAGE);
        }

    }

}
