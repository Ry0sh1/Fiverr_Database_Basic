package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;
import java.awt.*;

public class Standard_Dialog extends JDialog {

    private final Frame frame;
    public Standard_Dialog(Frame owner) {
        super(owner);
        frame = owner;
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_1);
        setForeground(Global.FOREGROUND);
        setLocationRelativeTo(frame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

}
