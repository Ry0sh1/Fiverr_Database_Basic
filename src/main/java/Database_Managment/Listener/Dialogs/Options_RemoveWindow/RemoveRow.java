package Database_Managment.Listener.Dialogs.Options_RemoveWindow;

import Database_Managment.GUI.Dashboard.Frame_Dashboard;
import Database_Managment.Global;
import Database_Managment.SQL.LiteSQL;
import Database_Managment.Standard.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveRow extends Standard_Dialog implements ActionListener {

    private int primaryKeyCount;
    private String[] primaryKeyRows;
    private final ArrayList<Standard_TextField> textFields = new ArrayList<>();
    private String[] dataTypes;
    public RemoveRow(Frame owner) {
        super(owner);
        setTitle("Remove Row");

        try {
            primaryKeyCount = LiteSQL.onQuery("SELECT COUNT(*) FROM pragma_table_info('" + Global.selected + "') WHERE pk>0").getInt(1);
            primaryKeyRows = new String[primaryKeyCount];
            dataTypes = new String[primaryKeyCount];

            PreparedStatement stmt = LiteSQL.prepareStatement("SELECT * FROM pragma_table_info('" + Global.selected + "') WHERE pk>0");
            ResultSet rs = stmt.executeQuery();

            int i = 0;
            while (rs.next()) {
                primaryKeyRows[i] = rs.getString("name");
                dataTypes[i] = rs.getString("type");
            }

            Standard_Panel center = new Standard_Panel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            for (i = 0; i < primaryKeyCount; i++) {
                Standard_Panel panel = new Standard_Panel(new FlowLayout());
                Standard_Label label = new Standard_Label(primaryKeyRows[i] + ": ");
                Standard_TextField textField = new Standard_TextField();
                label.setPreferredSize(new Dimension(260, 40));
                textField.setPreferredSize(new Dimension(100, 40));
                textFields.add(textField);
                panel.add(label);
                panel.add(textField);
                center.add(panel, gbc);
                gbc.gridy++;
            }

            Standard_Panel north = new Standard_Panel();
            Standard_Label head = new Standard_Label("Which row do you want to remove?");

            Standard_Panel south = new Standard_Panel(new FlowLayout());
            Standard_Button cancel = new Standard_Button("Cancel");
            cancel.setPreferredSize(new Dimension(80,40));
            cancel.addActionListener(e -> this.dispose());
            Standard_Button create = new Standard_Button("Remove");
            create.addActionListener(this);
            create.setPreferredSize(new Dimension(80,40));
            south.add(cancel);
            south.add(create);

            north.add(head);
            add(north,BorderLayout.NORTH);
            add(center,BorderLayout.CENTER);
            add(south, BorderLayout.SOUTH);


        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        StringBuilder stmt = new StringBuilder("DELETE FROM " + Global.selected + " WHERE ");

        for (int i = 0;i<primaryKeyCount;i++){
            switch (dataTypes[i]){
                case "INTEGER", "LONG" -> stmt.append(primaryKeyRows[i]).append(" = ").append(textFields.get(i).getText()).append(" AND ");
                case "TEXT" -> stmt.append(primaryKeyRows[i]).append(" = '").append(textFields.get(i).getText()).append("' AND ");
            }
        }

        String update = stmt.substring(0,stmt.length()-4);

        LiteSQL.onUpdate(update);
        this.dispose();
        Frame_Dashboard.FRAME.dispose();
        new Frame_Dashboard();

    }

}
