package factory;

import static constants.Constants.DELIMITER;
import static constants.Constants.NOT_EXIST_VALUE;
import static constants.Constants.NULL_STRING;
import static constants.Constants.PRODUCTS_FILE_PATH;
import static constants.Constants.PRODUCT_NAME_POSITION;
import static constants.Constants.PRODUCT_PRICE_POSITION;
import static constants.Constants.PRODUCT_PROMOTION_POSITION;
import static constants.Constants.PRODUCT_QUANTITY_POSITION;
import static error.ErrorMessage.FILE_NOT_FOUND_ERROR;
import static error.ErrorMessage.IO_EXCEPTION_ERROR;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Product;
import model.Products;
import validation.Validation;

public class ProductFactory {

    public static void input(final Products products) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH));
            process(products, br);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(IO_EXCEPTION_ERROR.getMessage());
        }
    }

    private static void process(final Products products, BufferedReader br) throws IOException {
        String inputLine;
        br.readLine();
        while ((inputLine = br.readLine()) != null) {
            String[] strings = inputLine.split(DELIMITER);
            add(products, stringsToProduct(strings), Integer.parseInt(strings[PRODUCT_QUANTITY_POSITION]));
        }
    }

    private static Product stringsToProduct(final String[] strings) {
        validate(strings);

        String name = strings[PRODUCT_NAME_POSITION];
        int price = Integer.parseInt(strings[PRODUCT_PRICE_POSITION]);
        String promotion = strings[PRODUCT_PROMOTION_POSITION];
        if (promotion.equals(NULL_STRING)) {
            promotion = null;
        }
        return new Product(name, price, promotion);
    }

    private static void add(final Products products, final Product product, final int quantity) {
        products.add(product, quantity);
        if (product.isPromotionProduct()) {
            Product noneExistNormalProduct = product.getNoneExistNormalProduct();
            add(products, noneExistNormalProduct, NOT_EXIST_VALUE);
        }
    }

    private static void validate(final String[] input) {
        for (String string : input) {
            Validation.blank(string);
        }
        Validation.number(input[PRODUCT_PRICE_POSITION]);
        Validation.number(input[PRODUCT_QUANTITY_POSITION]);
    }
}
