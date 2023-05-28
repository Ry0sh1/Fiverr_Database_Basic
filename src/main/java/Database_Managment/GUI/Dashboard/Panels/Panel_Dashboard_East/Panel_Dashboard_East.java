package Database_Managment.GUI.Dashboard.Panels.Panel_Dashboard_East;

import Database_Managment.Standard.Standard_Button;
import Database_Managment.Standard.Standard_Panel;
import Database_Managment.Listener.Data_ActionListener;

import java.awt.*;
import java.util.ArrayList;

public class Panel_Dashboard_East extends Standard_Panel {

    public static Panel_Dashboard_East PANEL;
    public Panel_Dashboard_East() {
        setLayout(new GridBagLayout());
        PANEL = this;

        ArrayList<Standard_Button> buttons = new ArrayList<>();
        Standard_Button add = new Standard_Button("Add");
        add.setName("add");
        Standard_Button remove = new Standard_Button("Remove");
        remove.setName("remove");
        Standard_Button change = new Standard_Button("Change");
        change.setName("change");
        Standard_Button switchTable = new Standard_Button("Switch Table");
        switchTable.setName("switchTable");
        buttons.add(add);
        //buttons.add(change);
        buttons.add(remove);
        buttons.add(switchTable);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        for (Standard_Button b : buttons) {
            b.addActionListener(new Data_ActionListener(b.getName()));
            b.setPreferredSize(new Dimension(160, 80));
            add(b, gbc);
            gbc.gridy++; // Move to the next row
        }

    }

}
