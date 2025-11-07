package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {

    private int basePrice;
    private int discount;

    public DiscountedProduct(String name, int basePrice, int discount, UUID id) {
        super(name, id);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Невалидное значение для цены продукта.");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Невалидное значение для скидки.");
        }
        this.basePrice = basePrice;
        this.discount = discount;
    }

    @Override
    public int getPrice() {
        return (int) (basePrice * (1 - discount / 100.0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return "%s: %d (%d%%)".formatted(getName(), getPrice(), discount);
    }
}
