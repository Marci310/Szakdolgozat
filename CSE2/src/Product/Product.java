package Product;

import Asset.Asset;


public class Product {
    private double price;
    private Asset asset;

    public Product(double price, Asset asset) {
        this.price = price;
        this.asset = asset;
    }

    // Getters and setters for the private variables

    public double getPrice() {
        return price;
    }

    public double getAssetPrice() {
        return asset.getPrice();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}

