public class Main {

    public static void main(String[] args) {
        Market market = new Market();
        Market exampleMarket = new Market();
        Simulation simulation = new Simulation(market, exampleMarket);
        //simulation.simulate();
        //150 days call options calculated every 10th days several different strike prices
        simulation.printOptionValues(true);

        //150 days put options...
        //simulation.printOptionValues(false);

        //plots of averages, paths, call options...
        //simulation.simulateBlackSholes2();
    }
}
