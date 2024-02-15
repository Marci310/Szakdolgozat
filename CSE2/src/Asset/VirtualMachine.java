package Asset;
import Product.Product;
import Resources.Resources;

import java.util.Date;

public class VirtualMachine extends Asset {
    public VirtualMachine(double price, Resources resources, String location, double riskFreeRate) {
        super(price, resources, location, riskFreeRate);
    }
}
