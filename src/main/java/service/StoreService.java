package service;

import static constants.Constants.NOT_EXIST_VALUE;
import static constants.Constants.YES_ANSWER;
import static error.ErrorMessage.OVER_BUY_ERROR;

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
        while (true) {
            try {
                inputOrders(new InputView().readItem());
                validateOrderCount();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateOrderCount() {
        for (Order order : orders.getOrders()) {
            products.isExist(order);
            if (order.getQuantity() > products.getPromotionNormalQuantity(order.getName())) {
                throw new IllegalArgumentException(OVER_BUY_ERROR.getMessage());
            }
        }
    }

    private void inputOrders(final String orderString) {
        orders = new Orders();
        OrderFactory.add(orders, orderString);
    }

    public Promotions createPromotions() {
        promotions = new Promotions();
        PromotionFactory.input(promotions);
        return promotions;
    }

    public int buyProducts() {
        int totalPrice = NOT_EXIST_VALUE;
        notApplyTotalPromotionPrice = NOT_EXIST_VALUE;
        gifts = new Gifts();
        for (Order order : orders.getOrders()) {
            String promotionName = products.getPromotionName(order);
            Optional<Promotion> buyPromotion = promotions.isPromotion(DateTimes.now().toLocalDate(), promotionName);
            totalPrice += getTotalPrice(order, buyPromotion);
        }
        return totalPrice;
    }


    public int membershipSale() {
        salePrice = NOT_EXIST_VALUE;
        InputView inputView = new InputView();
        String answer;
        answer = getValidatedInput(inputView::checkMembership);
        if (answer.equals(YES_ANSWER)) {
            salePrice = SalePriceFactory.salePrice(notApplyTotalPromotionPrice);
        }
        return salePrice;
    }

    public void printReceipt() {
        OutputView outputView = new OutputView();
        outputView.printReceipt(orders, products, gifts, salePrice);
    }

    public boolean isBuyMoreProducts() {
        InputView inputView = new InputView();
        String answer;
        answer = getValidatedInput(inputView::buyMoreProducts);
        if (answer.equals(YES_ANSWER)) {
            return true;
        }
        return false;
    }

    private int getTotalPrice(final Order order, final Optional<Promotion> buyPromotion) {
        String orderName = order.getName();
        int orderQuantity = order.getQuantity();
        if (!buyPromotion.isPresent()) {
            notApplyTotalPromotionPrice = products.buy(orderName, orderQuantity, null);
            return notApplyTotalPromotionPrice;
        }
        return promotionPresent(order, buyPromotion);
    }

    private int promotionPresent(final Order order, final Optional<Promotion> buyPromotion) {
        Promotion promotion = buyPromotion.get();
        int orderQuantity = order.getQuantity();
        boolean possibleMoreFree = products.isPossibleMoreFree(order, promotion);
        if (possibleMoreFree) {
            orderQuantity = orderQuantityDecision(order.getName(), orderQuantity);
            updateOrder(order, orderQuantity);
            return process(promotion, order);
        }
        return process(promotion, order);
    }

    private Order updateOrder(final Order order, final int newQuantity) {
        return orders.update(order, newQuantity);
    }

    private int orderQuantityDecision(final String orderName, final int orderQuantity) {
        InputView inputView = new InputView();
        String answer;
        answer = getValidatedInput(() -> inputView.moreFreeProduct(orderName));
        if (answer.equals(YES_ANSWER)) {
            return orderQuantity + 1;
        }
        return orderQuantity;
    }

    private int process(final Promotion promotion, final Order order) {
        Product promotionProduct = infoToProduct(order.getName(), promotion.getName());
        if (products.isEnoughPromotion(promotionProduct, promotion, order)) {
            return enoughPromotion(promotion, order);
        }
        return notEnoughPromotion(promotionProduct, promotion, order);
    }

    private int enoughPromotion(final Promotion promotion, final Order order) {
        int buyCount = promotion.getBuyCount(order.getQuantity());
        int freeCount = promotion.getFreeCount(order.getQuantity());
        String orderName = order.getName();
        products.buy(orderName, freeCount, promotion.getName());
        gifts.add(new Gift(orderName, freeCount));
        return products.buy(orderName, buyCount, promotion.getName());
    }

    private int notEnoughPromotion(final Product promotionProduct, final Promotion promotion, final Order order) {
        InputView inputView = new InputView();
        int notApplyPromotionCount = products.getNotApplyPromotionCount(promotionProduct, promotion, order.getQuantity());
        int buyCount = promotion.getBuyCount(order.getQuantity() - notApplyPromotionCount);
        int freeCount = promotion.getFreeCount(order.getQuantity() - notApplyPromotionCount);
        String answer = getValidatedInput(() -> inputView.purchaseAtPrice(order.getName(), notApplyPromotionCount));
        if (!answer.equals(YES_ANSWER)) {
            return calcPriceExceptNotApply(promotion, order, freeCount, notApplyPromotionCount, buyCount);
        }
        return calcPriceIncludeNotApply(promotionProduct, promotion, order.getName(), freeCount, buyCount, notApplyPromotionCount);
    }

    private int calcPriceIncludeNotApply(final Product promotionProduct, final Promotion promotion, final String orderName, final int freeCount, final int buyCount, final int notApplyPromotionCount) {
        int totalPrice = NOT_EXIST_VALUE;
        int notApplyPromotionPrice = NOT_EXIST_VALUE;
        products.buy(orderName, freeCount, promotion.getName());
        gifts.add(new Gift(orderName, freeCount));
        totalPrice += products.buy(orderName, buyCount, promotion.getName());
        notApplyPromotionPrice += products.buy(orderName, notApplyPromotionCount - products.getQuantity(promotionProduct), null);
        notApplyPromotionPrice += products.buy(orderName, products.getQuantity(promotionProduct), promotion.getName());
        notApplyTotalPromotionPrice += notApplyPromotionPrice;
        return totalPrice + notApplyPromotionPrice;
    }

    private int calcPriceExceptNotApply(final Promotion promotion, final Order order, int freeCount,
        final int notApplyPromotionCount, final int buyCount) {
        String orderName = order.getName();
        products.buy(orderName, freeCount, promotion.getName());
        gifts.add(new Gift(orderName, freeCount));
        updateOrder(order, order.getQuantity() - notApplyPromotionCount);
        return products.buy(orderName, buyCount, promotion.getName());
    }

    private String getValidatedInput(final Supplier<String> inputMethod) {
        String answer;
        while (true) {
            try {
                answer = inputMethod.get();
                return validate(answer);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String validate(final String answer) {
        Validation.blank(answer);
        Validation.answerPattern(answer);
        return answer;
    }

    private Product infoToProduct(final String orderName, final String promotionName) {
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
