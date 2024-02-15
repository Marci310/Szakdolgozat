package Asset;
import Product.Product;
import Resources.Resources;

import java.util.Date;

public class KubernetesPod extends Asset {
    public KubernetesPod(double price, Resources resources, String location, double riskFreeRate) {
        super(price, resources, location, riskFreeRate);
    }
}
