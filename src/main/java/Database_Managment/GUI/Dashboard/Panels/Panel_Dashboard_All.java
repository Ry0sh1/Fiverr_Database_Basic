package Database_Managment.GUI.Dashboard.Panels;

import Database_Managment.GUI.Dashboard.Panels.Panel_Dashboard_East.Panel_Dashboard_East;
import Database_Managment.GUI.Dashboard.Panels.Panel_Dashboard_West.Panel_Dashboard_West;
import Database_Managment.Standard.Standard_Panel;

import java.awt.*;

public class Panel_Dashboard_All extends Standard_Panel {

    public Panel_Dashboard_All() {
        setPreferredSize(new Dimension(1200,800));
        add(new Panel_Dashboard_West(), BorderLayout.WEST);
        add(new Panel_Dashboard_East(), BorderLayout.EAST);
    }

}
