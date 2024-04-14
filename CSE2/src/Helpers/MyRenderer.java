package Helpers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


class MyRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column, Color[][] textColor) {
        JLabel label = ((JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column));
        label.setForeground(textColor[row][column]);

        return label;
    }

}
