package policy;

import static constants.Constants.MAX_SALE_PRICE;

public class Sale8000Policy implements SalePolicy{
    @Override
    public int salePrice(final int payPrice) {
        return MAX_SALE_PRICE;
    }
}
