package policy;

public class Sale8000Policy implements SalePolicy{
    private static final int MAX_SALE_PRICE = 8000;
    @Override
    public int salePrice(int payPrice) {
        return MAX_SALE_PRICE;
    }
}
