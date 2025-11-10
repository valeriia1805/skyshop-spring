package org.skypro.skyshop.service;

import lombok.RequiredArgsConstructor;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
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
            throw new NoSuchProductException(id);
        }
        productBasket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        List<BasketItem> items = productBasket.getProducts().entrySet().stream()
                .map(e ->
                        new BasketItem(storageService.getProductById(e.getKey())
                                .orElseThrow(() -> new NoSuchProductException(e.getKey())), e.getValue())
                )
                .toList();
        return new UserBasket(items);
    }
}
