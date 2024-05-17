package Product;

import Assets.Asset;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionFromMersenneRandomNumbers;
import net.finmath.montecarlo.assetderivativevaluation.models.BlackScholesModel;
import net.finmath.montecarlo.process.EulerSchemeFromProcessModel;
import net.finmath.time.TimeDiscretizationFromArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Derivative {


    private Asset asset;
    private double volatility;
    private final int workdays;
    private TimeDiscretizationFromArray td;
    private final int NUMOFPATHS = 10000;
    private EulerSchemeFromProcessModel process;
    private BrownianMotion brownianMotion;
    private final List<Double> randomPath;


    public Derivative(Asset asset, int workDays,double volatility) {
        this.asset=asset;
        this.workdays = workDays;
        this.volatility=volatility;
        randomPath = new ArrayList<>();
        generateProcess();
        generatePath();
    }


    private void generateProcess() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(9000) + 1000;
        var model = new BlackScholesModel(asset.getPrice(), asset.getDailyRiskFreeRate(workdays), volatility);
        td = new TimeDiscretizationFromArray(0.0, workdays, 1);
        brownianMotion = new BrownianMotionFromMersenneRandomNumbers(td, 1, NUMOFPATHS, randomNumber);
        process = new EulerSchemeFromProcessModel(model, brownianMotion);
    }

    private void generatePath() {
        for (int i = 0; i <= workdays; i++) {
            double value = 0;
            for (int j = 0; j < NUMOFPATHS; j++)
                value+=process.getProcessValue(i, 0).get(j);
            randomPath.add(value/NUMOFPATHS);
        }
    }

    public EulerSchemeFromProcessModel getProcess() {
        return process;
    }


    public List<Double> getRandomPath() {
        return randomPath;
    }


    // Getters and setters for the private variables

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }



    /*public static void main(String[] args) {
        // Sample data stored in a List<List<Double>>
        List<Double> column1 = new ArrayList<>();
        List<Double> column2 = new ArrayList<>();
        List<String> column3= new ArrayList<>();
        String abc = "abcdefghijklmnopqrst";
        for (int i =0; i<10; ++i){
            column1.add((double)i);
            column2.add((double)i*2);
            column3.add(String.valueOf(abc.charAt(i)));
        }
        for (int i =10; i<10; ++i){
            column1.add((double)i);
            column2.add((double)i*2);
            column3.add(String.valueOf(abc.charAt(i)));
        }
        List<List<Double>> columnok = new ArrayList<>();
        columnok.add(column1);
        columnok.add(column2);

        // Create column names
        String[] columns = {"Column 1", "Column 2"};

        // Create a new JFrame
        JFrame frame = new JFrame("Table Example");

        // Create a new DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(0,0);

        // Populate the table model with data from the List<List<Double>>
        for (String row : column3) {
            model.addColumn(row);
        }
        model.addRow(column1.toArray());
        model.addRow(column2.toArray());


        // Create a new JTable with the table model
        JTable table = new JTable(model);

        // Set some properties of the table (optional)
        table.setPreferredScrollableViewportSize(new Dimension(500,3*10+10));


        // Create a JScrollPane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        frame.add(scrollPane);

        // Set some properties of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/
}
