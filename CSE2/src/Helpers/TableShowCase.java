package Helpers;

import net.finmath.plots.Named;
import net.finmath.plots.Plot;
import net.finmath.plots.Plot2D;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class TableShowCase {

    public void showCase(List<List<Double>> pathes, String title) throws Exception {
        JFrame frame = new JFrame(title);
        DefaultTableModel model = new DefaultTableModel(0,0);
        for (Double row : pathes.get(0)) {
            model.addColumn(row);
        }
        for (int i =1; i<pathes.size(); ++i){
            model.addRow(pathes.get(i).toArray());
        }
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setPreferredScrollableViewportSize(new Dimension(1800,pathes.size()*50+10));
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}
