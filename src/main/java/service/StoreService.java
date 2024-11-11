package service;

import static model.Products.OVER_BUY_ERROR;

import camp.nextstep.edu.missionutils.DateTimes;
import constants.Constants;
import factory.OrderFactory;
import factory.ProductFactory;
import factory.PromotionFactory;
import factory.SalePriceFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import model.Gift;
import model.Gifts;
import model.Order;
import model.Orders;
import model.Product;
import model.Products;
import model.Promotion;
import model.Promotions;
import validation.Validation;
import view.InputView;
import view.OutputView;

public class StoreService {

    private Products products;
    private Orders orders;
    private Promotions promotions;
    private Gifts gifts;
    private int salePrice;
    private int notApplyTotalPromotionPrice;

    public Products createProducts() {
        products = new Products();
        ProductFactory.input(products);
        return products;
    }

    public void printProducts() {
        OutputView outputView = new OutputView();
        outputView.printProductions(products);
    }

    public void readOrders() {
        while(true) {
            try {
                inputOrders(new InputView().readItem());
                validateOrderCount();
                break;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    private void validateOrderCount(){
        for(Order order:orders.getOrders()){
            products.isExist(order);
            if(order.getQuantity() > products.getPromotionNormalQuantity(order.getName())){
                throw new IllegalArgumentException(OVER_BUY_ERROR);
            }
        }
    }

    private void inputOrders(String orderString) {
        orders = new Orders();
        OrderFactory.add(orders, orderString);
    }

    public Promotions createPromotions() {
        promotions = new Promotions();
        PromotionFactory.input(promotions);
        return promotions;
    }

    public int buyProducts() {
        int totalPrice = 0;
        notApplyTotalPromotionPrice=0;
        gifts = new Gifts();
        for (Order order : orders.getOrders()) {
            String promotionName = products.getPromotionName(order);
            Optional<Promotion> buyPromotion = promotions.isPromotion(DateTimes.now().toLocalDate(), promotionName);
            totalPrice += getTotalPrice(order, buyPromotion);
        }
        return totalPrice;
    }


    public int membershipSale() {
        salePrice = 0;
        InputView inputView = new InputView();
        String answer;
        answer = getValidatedInput(inputView::checkMembership);
        if (answer.equals(Constants.YES_ANSWER)) {
            salePrice = SalePriceFactory.salePrice(notApplyTotalPromotionPrice);
        }
        return salePrice;
    }

    public void printReceipt() {
        OutputView outputView = new OutputView();
        outputView.printReceipt(orders, products, gifts, salePrice);
    }

    public boolean isBuyMoreProducts(){
        InputView inputView = new InputView();
        String answer;
        answer = getValidatedInput(inputView::buyMoreProducts);
        if(answer.equals(Constants.YES_ANSWER))
            return true;
        return false;
    }
    private int getTotalPrice(Order order, Optional<Promotion> buyPromotion) {
        //10줄 이하 리팩토링
        String orderName = order.getName();
        int orderQuantity = order.getQuantity();
        if (!buyPromotion.isPresent()) {
            notApplyTotalPromotionPrice = products.buy(orderName, orderQuantity, null);
            return notApplyTotalPromotionPrice;
        }
        return promotionPresent(order, buyPromotion);
    }

    private int promotionPresent(Order order, Optional<Promotion> buyPromotion) {
        Promotion promotion = buyPromotion.get();
        int orderQuantity = order.getQuantity();
        boolean possibleMoreFree = products.isPossibleMoreFree(order,promotion);
        if (possibleMoreFree) {
            orderQuantity = orderQuantityDecision(order.getName(), orderQuantity);
            order = updateOrder(order, orderQuantity);
            return process(promotion, order);
        }
        return process(promotion, order);
    }

    private Order updateOrder(Order order, int newQuantity) {
        return orders.update(order, newQuantity);
    }

    private int orderQuantityDecision(String orderName, int orderQuantity) {
        InputView inputView = new InputView();
        String answer;
        answer =getValidatedInput(()->inputView.moreFreeProduct(orderName));
        if (answer.equals(Constants.YES_ANSWER)) {
            orderQuantity++;
        }
        return orderQuantity;
    }

    private int process(Promotion promotion, Order order) {
        int buyCount = promotion.getBuyCount(order.getQuantity());
        int freeCount = promotion.getFreeCount(order.getQuantity());

        Product promotionProduct = infoToProduct(order.getName(), promotion.getName());
        if (products.isEnoughPromotion(promotionProduct,promotion,order)) {
            return enoughPromotion(promotion, order.getName(), freeCount, buyCount);
        }
        return notEnoughPromotion(promotionProduct, promotion,order);
    }

    private int enoughPromotion(Promotion promotion, String orderName,
        int freeCount, int buyCount) {
        products.buy(orderName, freeCount, promotion.getName());
        gifts.add(new Gift(orderName, freeCount));
        return products.buy(orderName, buyCount, promotion.getName());
    }


    private int notEnoughPromotion(Product promotionProduct,Promotion promotion,Order order) {
        InputView inputView = new InputView();
        String orderName = order.getName();
        int orderQuantity = order.getQuantity();

        int notApplyPromotionCount = products.getNotApplyPromotionCount(promotionProduct,promotion,orderQuantity);

        String answer;
        answer = getValidatedInput(() -> inputView.purchaseAtPrice(orderName, notApplyPromotionCount));

        int buyCount = promotion.getBuyCount(orderQuantity-notApplyPromotionCount);
        int freeCount = promotion.getFreeCount(orderQuantity-notApplyPromotionCount);
        int totalPrice = 0;
        int notApplyPromotionPrice = 0;
        if (answer.equals(Constants.YES_ANSWER)) {
            //수정 필요
            products.buy(orderName, freeCount, promotion.getName());
            gifts.add(new Gift(orderName, freeCount));
            totalPrice += products.buy(orderName, buyCount, promotion.getName());
            notApplyPromotionPrice += products.buy(orderName,
                notApplyPromotionCount - products.getQuantity(promotionProduct), null);
            notApplyPromotionPrice += products.buy(orderName, products.getQuantity(promotionProduct),
                promotion.getName());
            totalPrice += notApplyPromotionPrice;
            notApplyTotalPromotionPrice += notApplyPromotionPrice;
            return totalPrice;
        }
        //추가구매 하지 않는 경우
        buyCount = promotion.getBuyCount(orderQuantity-notApplyPromotionCount);
        freeCount = promotion.getFreeCount(orderQuantity-notApplyPromotionCount);
        products.buy(orderName, freeCount, promotion.getName());
        gifts.add(new Gift(orderName, freeCount));
        order = updateOrder(order, orderQuantity-notApplyPromotionCount);
        return products.buy(orderName, buyCount, promotion.getName());

    }


    private String getValidatedInput(Supplier<String>inputMethod){
        String answer;
        while(true){
            try {
                answer = inputMethod.get();
                validate(answer);
                return answer;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void validate(String answer) {
        Validation.blank(answer);
        Validation.answerPattern(answer);
    }

    private Product infoToProduct(String orderName, String promotionName) {
        List<Product> productList = products.getProductions();
        Product returnProduct = null;
        for (Product product : productList) {
            if (product.isSameName(orderName) && Objects.equals(product.getPromotion(), promotionName)) {
                returnProduct = product;
                break;
            }
        }
        return returnProduct;
    }
}
