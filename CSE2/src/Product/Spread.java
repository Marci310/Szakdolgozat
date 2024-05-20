package Product;

public abstract class Spread {

    private Option option1;
    private Option option2;

    public Spread(Option option1, Option option2) {
        this.option1 = option1;
        this.option2 = option2;
    }

    public double calculateProfit(double currentPrice) {
        return option1.calculateProfit(currentPrice) + option2.calculateProfit(currentPrice);
    }

    public Option getOption1() {
        return option1;
    }

    public Option getOption2() {
        return option2;
    }

    public abstract String getTitle();
}
