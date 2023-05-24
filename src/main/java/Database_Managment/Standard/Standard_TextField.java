package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;

public class Standard_TextField extends JTextField {

    public Standard_TextField() {
        init();
    }

    public Standard_TextField(String text) {
        super(text);
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_2);
        setForeground(Global.FOREGROUND);
        setBorder(BorderFactory.createLineBorder(Global.LINES,2));
    }

}
