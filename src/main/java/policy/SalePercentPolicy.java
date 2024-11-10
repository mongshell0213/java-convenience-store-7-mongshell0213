package policy;

public class SalePercentPolicy implements SalePolicy{
    private static final double SALE_PERCENT_PRICE = 0.3;
    @Override
    public int salePrice(int payPrice) {
        return (int)(payPrice * SALE_PERCENT_PRICE);
    }
}
