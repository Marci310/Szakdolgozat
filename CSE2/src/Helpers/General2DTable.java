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


    public General2DTable(String tableName, String subName, int xSize, int ySize) {
        frame = new JFrame(tableName);
        this.subName = subName;
        columnSize = xSize;
        rowSize = ySize;
        columns = new String[columnSize];
        datas = new Object[rowSize][columnSize];
        textColor = new Color[rowSize][columnSize];
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < columnSize; col++) {
                textColor[row][col] = Color.BLACK;
            }
        }
    }

    public void addHeader(String name, int index) {
        columns[index] = name;
    }

    public void addData(Object data, int column, int row) {
        datas[row][column] = data;
    }

    public void setColor(Color color, int column, int row) {
        textColor[row][column] = color;
    }

    public void show() {
        JTable table = new JTable(datas, columns);
        table.setDefaultRenderer(Object.class, new MyRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JLabel lblHeading = new JLabel(subName);
        lblHeading.setFont(new Font("Arial", Font.PLAIN, 24));

        frame.getContentPane().setLayout(new BorderLayout());

        frame.getContentPane().add(lblHeading, BorderLayout.PAGE_START);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(columnSize * 100, rowSize * 20 + 70);
        frame.setVisible(true);
    }

}
