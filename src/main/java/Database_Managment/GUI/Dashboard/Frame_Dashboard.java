package Database_Managment.GUI.Dashboard;

import Database_Managment.GUI.Dashboard.Panels.Panel_Dashboard_All;
import Database_Managment.Standard.Standard_Frame;


public class Frame_Dashboard extends Standard_Frame {

    public static Frame_Dashboard FRAME;
    public Frame_Dashboard(){
        FRAME = this;
        add(new Panel_Dashboard_All());
        pack();
        setVisible(true);
    }

}
