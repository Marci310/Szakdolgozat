import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws Exception {
        Market market = new Market(0.4,0.03);
        Simulation simulation = new Simulation(market, new LocalDate[]{LocalDate.now(), LocalDate.now().plusYears(1)});
        simulation.simulate();

    }
}
