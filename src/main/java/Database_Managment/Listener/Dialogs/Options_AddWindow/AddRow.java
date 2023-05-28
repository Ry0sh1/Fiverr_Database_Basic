package Database_Managment.Listener.Dialogs.Options_AddWindow;

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

public class AddRow extends Standard_Dialog implements ActionListener {

    private String[] columnNames;
    private String[] columnType;
    private String[] isNotNull;
    private String[] isPrimaryKey;
    private int columnCount;
    private final ArrayList<Standard_TextField> textFields = new ArrayList<>();

    public AddRow(Frame owner) {
        super(owner);
        setTitle("Add a row");

        try {
            PreparedStatement st = LiteSQL.prepareStatement("SELECT * FROM pragma_table_info('" + Global.selected + "') WHERE pk <> 1 order by cid");
            ResultSet rs = st.executeQuery();
            columnCount = LiteSQL.onQuery("SELECT COUNT(*) FROM pragma_table_info('" + Global.selected + "') WHERE pk <> 1").getInt(1);
            columnNames = new String[columnCount];
            columnType = new String[columnCount];
            isNotNull = new String[columnCount];
            isPrimaryKey = new String[columnCount];
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
                textFields.add(textField);
                panel.add(label);
                panel.add(textField);
                center.add(panel, gbc);
                gbc.gridy++;
            }

            Standard_Panel south = new Standard_Panel(new FlowLayout());
            Standard_Button cancel = new Standard_Button("Cancel");
            cancel.setPreferredSize(new Dimension(80,40));
            cancel.addActionListener(e -> this.dispose());
            Standard_Button create = new Standard_Button("Create");
            create.addActionListener(this);
            create.setPreferredSize(new Dimension(80,40));
            south.add(cancel);
            south.add(create);

            add(center, BorderLayout.CENTER);
            add(south, BorderLayout.SOUTH);
            pack();
            setVisible(true);

        } catch (SQLException sqlException) {
            System.out.println("Line 28 AddRow.java");
            sqlException.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (checkValidation()){

            StringBuilder stmt = new StringBuilder("INSERT INTO " + Global.selected + "(");

            for (int i = 0; i<columnCount;i++){
                stmt.append(columnNames[i]).append(",");
            }

            stmt = new StringBuilder(stmt.substring(0, stmt.length()-1));
            stmt.append(") VALUES (");

            for (int i = 0;i<columnCount;i++){
                switch (columnType[i]){
                    case "INTEGER", "LONG" -> stmt.append(textFields.get(i).getText());
                    case "TEXT" -> stmt.append("'").append(textFields.get(i).getText()).append("'");
                }
                stmt.append(",");
            }

            stmt = new StringBuilder(stmt.substring(0, stmt.length()-1));
            stmt.append(")");

            LiteSQL.onUpdate(stmt.toString());

        }else {
            JOptionPane.showMessageDialog(getOwner(),"Invalid Input", "Error 204", JOptionPane.ERROR_MESSAGE);
        }

    }

    private Boolean checkValidation(){

        for (int i = 0;i<columnCount;i++){

            if (isNotNull[i].equals(", not Null") && (textFields.get(i).getText().equals("") || textFields.get(i).getText()==null)){
                return false;
            }
            if (isPrimaryKey[i].equals(", primary key") && (textFields.get(i).getText().equals("") || textFields.get(i).getText()==null)){
                return false;
            }
            if (columnType[i].equals("INTEGER") && !textFields.get(i).getText().matches("^[0-9]+$")){
                return false;
            }

        }

        return true;
    }

}
