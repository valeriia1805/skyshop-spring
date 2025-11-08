package org.skypro.skyshop.model.basket;

import lombok.Getter;

import java.util.List;

@Getter
public class UserBasket {

    private final List<BasketItem> items;
    private final int total;

    public UserBasket(List<BasketItem> items) {
        this.items = items;
        total = items.stream()
                .mapToInt(i -> i.getProduct().getPrice() * i.getAmount())
                .sum();
    }
}
