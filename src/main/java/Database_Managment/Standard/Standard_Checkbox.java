package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;

public class Standard_Checkbox extends JCheckBox {

    public Standard_Checkbox() {
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_2);
        setForeground(Global.FOREGROUND);
        setBorder(BorderFactory.createLineBorder(Global.LINES,1));
    }

}
