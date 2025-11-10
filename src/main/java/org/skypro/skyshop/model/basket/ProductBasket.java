package org.skypro.skyshop.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@SessionScope
public class ProductBasket {

    private final Map<UUID, Integer> productMap = new HashMap<>();

    public void addProduct(UUID productId) {
        productMap.compute(productId, (k, v) -> v == null ? 1 : v + 1);
    }

    public Map<UUID, Integer> getProducts() {
        return Collections.unmodifiableMap(productMap);
    }
}
