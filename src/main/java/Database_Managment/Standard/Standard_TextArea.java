package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;

public class Standard_TextArea extends JTextArea {

    public Standard_TextArea(){
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_2);
        setForeground(Global.FOREGROUND);
        setBorder(BorderFactory.createLineBorder(Global.LINES,2));
    }

}
