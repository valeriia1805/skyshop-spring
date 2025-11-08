package org.skypro.skyshop.model.basket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.skypro.skyshop.model.product.Product;

@Getter
@RequiredArgsConstructor
public class BasketItem {

    private final Product product;
    private final int amount;
}
