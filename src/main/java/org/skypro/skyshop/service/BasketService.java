package org.skypro.skyshop.service;

import lombok.RequiredArgsConstructor;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final ProductBasket productBasket;

    private final StorageService storageService;

    public void addProduct(UUID id) {
        if (storageService.getProductById(id).isEmpty()) {
            throw new IllegalArgumentException("Товар с id = %s отсутствует.".formatted(id));
        }
        productBasket.add(id);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> items = productBasket.getProducts().entrySet().stream()
                .map(e -> new BasketItem(storageService.getProductById(e.getKey()).get(), e.getValue()))
                .toList();
        return new UserBasket(items);
    }
}
