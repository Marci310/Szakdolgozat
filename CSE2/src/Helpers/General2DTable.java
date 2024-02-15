package Helpers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class General2DTable {
    JFrame frame;
    String subName;
    int columnSize;
    int rowSize;
    String[] columns;
    Object[][] datas;

    Color[][] textColor;
    public General2DTable(String tableName, String subName, int xSize, int ySize){
        frame=new JFrame(tableName);
        this.subName=subName;
        columnSize=xSize;
        rowSize=ySize;
        columns=new String[columnSize];
        datas=new Object[rowSize][columnSize];
        textColor=new Color[rowSize][columnSize];
        for (int row=0;row<rowSize;row++)
            for (int col=0;col<columnSize;col++)
                textColor[row][col]=Color.BLACK;
    }
    public void addHeader(String name, int index)
    {
        if (index<0 || index>=columnSize) return;
        columns[index]=name;
    }
    public void addData(Object data, int column, int row)
    {
        if (column<0 || column>=columnSize) return;
        if (row<0 || row>=rowSize) return;
        datas[row][column]=data;
    }
    public void setColor(Color color, int column, int row)
    {
        if (column<0 || column>=columnSize) return;
        if (row<0 || row>=rowSize) return;
        textColor[row][column]=color;
    }
    public void show(){
        JTable table=new JTable(datas, columns);
        table.setDefaultRenderer(Object.class, new MyRenderer());
        /*
        for (int row=0;row<rowSize;row++)
            for (int col=0;col<columnSize;col++)
            {
                JLabel label = (JLabel) table.getModel().getValueAt(row, col);
                label.setForeground(textColor[row][col]);
            }
        */
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JLabel lblHeading = new JLabel(subName);
        lblHeading.setFont(new Font("Arial",Font.TRUETYPE_FONT,24));

        frame.getContentPane().setLayout(new BorderLayout());

        frame.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(columnSize*100, rowSize*20+70);
        frame.setVisible(true);
    }

    class MyRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            JLabel lbl = ((JLabel)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column));
            /*
            if(((Integer)value).intValue() < 3)lbl.setBackground(Color.YELLOW);
            else lbl.setBackground(Color.WHITE);

             */
            lbl.setForeground(textColor[row][column]);
            return lbl;
        }
    }
}
