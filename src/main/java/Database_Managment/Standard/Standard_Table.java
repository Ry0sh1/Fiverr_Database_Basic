package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.Vector;

public class Standard_Table extends JTable {

    public Standard_Table() {
    }

    public Standard_Table(TableModel dm) {
        super(dm);
    }

    public Standard_Table(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    public Standard_Table(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    public Standard_Table(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    public Standard_Table(Vector<? extends Vector> rowData, Vector<?> columnNames) {
        super(rowData, columnNames);
    }

    public Standard_Table(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_1);
        setGridColor(Global.BACKGROUND_2);
        setForeground(Global.FOREGROUND);
        getTableHeader().setBackground(Global.BACKGROUND_2);
        getTableHeader().setForeground(Global.FOREGROUND);
        getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        setBorder(BorderFactory.createEmptyBorder());
    }

}
