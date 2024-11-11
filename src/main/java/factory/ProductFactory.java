package factory;

import static error.ErrorMessage.FILE_NOT_FOUND_ERROR;
import static error.ErrorMessage.IO_EXCEPTION_ERROR;

import constants.Constants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Product;
import model.Products;
import validation.Validation;

public class ProductFactory {


    public static void input(Products products) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.PRODUCTS_FILE_PATH));
            process(products, br);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(IO_EXCEPTION_ERROR.getMessage());
        }
    }

    private static void process(Products products, BufferedReader br) throws IOException {
        String inputLine;
        br.readLine();
        while ((inputLine = br.readLine()) != null) {
            String[] strings = inputLine.split(Constants.DELIMITER);
            add(products, stringsToProduction(strings), Integer.parseInt(strings[Constants.QUANTITY_POSITION]));
        }
    }

    private static Product stringsToProduction(String[] strings) {
        validate(strings);

        String name = strings[Constants.NAME_POSITION];
        int price = Integer.parseInt(strings[Constants.PRICE_POSITION]);
        String promotion = strings[Constants.PROMOTION_POSITION];
        if (promotion.equals("null")) {
            promotion = null;
        }
        return new Product(name, price, promotion);
    }

    private static void add(Products products, Product product, int quantity) {
        products.add(product, quantity);
        if (product.isPromotionProduct()) {
            Product noneExistNormalProduct = product.getNoneExistNormalProduct();
            add(products, noneExistNormalProduct, 0);
        }
    }

    private static void validate(String[] input) {
        for (String string : input) {
            Validation.blank(string);
        }
        Validation.number(input[Constants.PRICE_POSITION]);
        Validation.number(input[Constants.QUANTITY_POSITION]);
    }
}
