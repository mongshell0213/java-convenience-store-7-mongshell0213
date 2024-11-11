package policy;

import static constants.Constants.SALE_PERCENT_PRICE;

public class SalePercentPolicy implements SalePolicy{
    @Override
    public int salePrice(final int payPrice) {
        return (int)(payPrice * SALE_PERCENT_PRICE);
    }
}
