package com.productmanager.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProductManager {

    private Product product;
    private Review[] reviews = new Review[5];
    private Locale locale;
    private ResourceBundle resources;
    private DateTimeFormatter dateFormatter;
    private NumberFormat currencyFormatter;


    public ProductManager(Locale locale) {
        this.locale = locale;
        resources = ResourceBundle.getBundle("reviews_en_GB");
        dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        product = new Food(id, name, price, rating, bestBefore);
        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        product = new Drink(id, name, price, rating);
        return product;
    }

    public Product reviewProduct(Product newProduct, Rating rating, String comments) {
        if (reviews[reviews.length - 1] != null) {
            reviews = Arrays.copyOf(reviews, reviews.length + 5);
        }
        int sum = 0, index = 0;
        boolean reviewed = false;

        while (index < reviews.length && !reviewed) {
            if (reviews[index] == null) {
                reviews[index] = new Review(rating, comments);
                reviewed =true;
            }
            sum += reviews[index].getRating().ordinal();
            index++;
        }
        this.product = newProduct.applyNewRating(Rateable.convert(Math.round((float) sum / index)));

        System.out.println(product.getRating().ordinal());
        return this.product;
    }

    public void printProductReport() {
        StringBuilder txt = new StringBuilder();
        txt.append(MessageFormat.format(resources.getString("product"),
                product.getName(),
                currencyFormatter.format(product.getPrice()),
                product.getRating().getStars(),
                dateFormatter.format(product.getBestBefore())));
        txt.append("\n");
        for (Review review : reviews) {
            if (review == null) {
                break;
            }
            txt.append(MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments()));
            txt.append("\n");
        }
        if (reviews[0] == null){
            txt.append(resources.getString("no.review"));
            txt.append("\n");
        }
        System.out.println(txt);
    }
}
