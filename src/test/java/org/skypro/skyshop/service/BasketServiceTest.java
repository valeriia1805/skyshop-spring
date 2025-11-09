package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private StorageService storageService;

    @Mock
    private ProductBasket productBasket;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProduct_throws_whenProductNotFound() {
        UUID unknownId = UUID.randomUUID();

        when(storageService.getProductById(unknownId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.addProduct(unknownId));

        verify(storageService).getProductById(unknownId);
        verifyNoInteractions(productBasket);
        verifyNoMoreInteractions(storageService);
    }

    @Test
    void addProduct_delegatesToProductBasket_whenFound() {
        var id = UUID.randomUUID();
        var p = mockProduct(id);

        when(storageService.getProductById(id)).thenReturn(Optional.of(p));

        basketService.addProduct(id);

        verify(productBasket).addProduct(id);
        verify(storageService).getProductById(id);
        verifyNoMoreInteractions(productBasket);
        verifyNoMoreInteractions(storageService);
    }

    @Test
    void getUserBasket_returnsEmpty_whenBasketIsEmpty() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        var result = basketService.getUserBasket();

        assertThat(result.getItems()).isEmpty();
        verify(productBasket).getProducts();
        verifyNoInteractions(storageService);
        verifyNoMoreInteractions(productBasket);
    }

    @Test
    void getUserBasket_returnsProducts_whenBasketHasItems() {
        var id1 = UUID.randomUUID();
        var id2 = UUID.randomUUID();
        when(productBasket.getProducts()).thenReturn(Map.of(id1, 1, id2, 1));
        when(storageService.getProductById(id1)).thenReturn(Optional.of(mockProduct(id1)));
        when(storageService.getProductById(id2)).thenReturn(Optional.of(mockProduct(id2)));
        var result = basketService.getUserBasket();
        assertThat(result.getItems()).hasSize(2);
        verify(productBasket).getProducts();
        verifyNoMoreInteractions(storageService);
        verifyNoMoreInteractions(productBasket);
    }

    @Test
    void addProduct_allowsDuplicates_ifBasketSupportsIt() {
        var id = UUID.randomUUID();
        var p = mockProduct(id);
        when(storageService.getProductById(id)).thenReturn(Optional.of(p));

        basketService.addProduct(id);
        basketService.addProduct(id);

        verify(productBasket, times(2)).addProduct(p.getId());
        verify(storageService, times(2)).getProductById(id);
        verifyNoMoreInteractions(productBasket);
        verifyNoMoreInteractions(storageService);
    }

    private static Product mockProduct(UUID id) {
        Product p = mock(Product.class, withSettings().lenient());
        try {
            when(p.getId()).thenReturn(id);
            when(p.getName()).thenReturn("product-" + id);
        } catch (Throwable ignored) {
        }
        return p;
    }
}
