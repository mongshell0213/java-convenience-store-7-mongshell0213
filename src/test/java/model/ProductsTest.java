package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import constants.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class ProductsTest {

    @Test
    void 상품추가확인() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();

        products.add(product, 10);

        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    void 상품_존재_테스트() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();
        products.add(product, 10);

        Order order = new Order("콜라", 5);

        assertThat(products.isExist(order)).isTrue();
    }

    @Test
    void 상품_미존재_테스트() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();
        products.add(product, 10);

        Order order = new Order("사이다", 5);

        assertThatThrownBy(() -> products.isExist(order)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션_이름_가져오기_테스트() {
        Products products = new Products();
        Product promotionProduct = new Product("콜라", 1000, "MD추천상품");
        Product normalProduct = new Product("콜라", 1000, null);
        products.add(promotionProduct, 10);
        products.add(normalProduct, 10);

        Order order = new Order("콜라", 5);

        assertThat(products.getPromotionName(order)).isEqualTo("MD추천상품");
    }

    @Test
    void 프로모션_이름_가져오기2_테스트() {
        Products products = new Products();
        Product normalProduct = new Product("콜라", 1000, null);
        products.add(normalProduct, 10);

        Order order = new Order("콜라", 5);

        assertThat(products.getPromotionName(order)).isNull();
    }

    @Test
    void 상품_과잉구매_테스트() {
        Product product = new Product("콜라", 1000, "MD추천상품");
        Products products = new Products();
        products.add(product, 10);

        String orderName = "콜라";
        int quantity = 15;

        assertThatThrownBy(() -> products.buy(orderName,quantity, "MD추천상품")).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 상품_구매_테스트() {
        Product product = new Product("콜라", 1000, null);
        Products products = new Products();
        products.add(product, 10);

        String orderName = "콜라";
        int quantity = 10;
        products.buy(orderName,quantity, null);

        assertThat(products.getQuantity(product)).isEqualTo(0);
    }

    @Test
    void 프로모션_충분_테스트(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 10);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        Order order = new Order("콜라",5);

        assertThat(products.isEnoughPromotion(product,promotion,order)).isTrue();
    }

    @Test
    void 프로모션_불충분_테스트(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 4);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        Order order = new Order("콜라",5);

        assertThat(products.isEnoughPromotion(product,promotion,order)).isFalse();
    }
    @Test
    void 프로모션_불충분_테스트2(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 7);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        Order order = new Order("콜라",7);

        assertThat(products.isEnoughPromotion(product,promotion,order)).isFalse();
    }

    @Test
    void 프로모션_미적용_갯수_테스트1(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 10);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        Order order = new Order("콜라",5);

        assertThat(products.getNotApplyPromotionCount(product,promotion,order.getQuantity())).isEqualTo(2);
    }

    @Test
    void 프로모션_미적용_갯수_테스트2(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 6);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        Order order = new Order("콜라",6);

        int count = products.getNotApplyPromotionCount(product,promotion,order.getQuantity());
        assertThat(count).isEqualTo(0);
    }

    @Test
    void 프로모션_미적용_갯수_테스트3(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 7);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        Order order = new Order("콜라",7);

        assertThat(products.getNotApplyPromotionCount(product,promotion,order.getQuantity())).isEqualTo(1);
    }

    @Test
    void 프로모션_미적용_갯수_테스트4(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 5);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",2,1,start,end);
        Order order = new Order("콜라",7);

        assertThat(products.getNotApplyPromotionCount(product,promotion,order.getQuantity())).isEqualTo(4);
    }

    @Test
    void 프로모션_미적용_갯수_테스트5(){
        Product product = new Product("콜라", 1000, "탄산2+1");
        Products products = new Products();
        products.add(product, 2);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        String startString = "2024-01-01";
        String endString = "2024-12-31";
        LocalDate start = LocalDate.parse(startString,dateTimeFormatter);
        LocalDate end = LocalDate.parse(endString,dateTimeFormatter);
        Promotion promotion = new Promotion("탄산2+1",1,1,start,end);
        Order order = new Order("콜라",1);

        assertThat(products.getNotApplyPromotionCount(product,promotion,order.getQuantity())).isEqualTo(1);
    }
}
