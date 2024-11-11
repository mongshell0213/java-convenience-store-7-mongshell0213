package factory;

import policy.Sale8000Policy;
import policy.SalePercentPolicy;
import policy.SalePolicy;
import validation.Validation;

public class SalePriceFactory {
    public static int salePrice(final int payPrice) {
        SalePolicy salePolicy;
        if (Validation.isMaxSale(payPrice)) {
            salePolicy = new Sale8000Policy();
            return salePolicy.salePrice(payPrice);
        }
        salePolicy = new SalePercentPolicy();
        return salePolicy.salePrice(payPrice);
    }
}
