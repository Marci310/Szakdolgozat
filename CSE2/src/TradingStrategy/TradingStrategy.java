package TradingStrategy;

import Product.Product;


public abstract class TradingStrategy {

    private Product[] products;

    public TradingStrategy(Product[] products) {
        this.products = products;
    }

    public abstract void trade();

    // Getters and setters for the private variable

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}