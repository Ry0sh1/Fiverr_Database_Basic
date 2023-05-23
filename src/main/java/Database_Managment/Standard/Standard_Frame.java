package Database_Managment.Standard;

import Database_Managment.Global;
import javax.swing.*;
import java.awt.*;

public class Standard_Frame extends JFrame {

    public Standard_Frame() throws HeadlessException {
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
