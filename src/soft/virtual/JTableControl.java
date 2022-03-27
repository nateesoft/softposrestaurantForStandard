
package soft.virtual;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class JTableControl {
    
    public static void alignColumn(JTable tblShowBalance, int col, String align) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        switch (align) {
            case "right":
                rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            case "left":
                rightRenderer.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case "center":
                rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            default:
                break;
        }
        
        tblShowBalance.getColumnModel().getColumn(col).setCellRenderer(rightRenderer);
    }
}
