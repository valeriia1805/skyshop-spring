package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storage;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_returnsEmpty_whenStorageIsEmpty() {
        when(storage.getAll()).thenReturn(List.of());

        var result = searchService.search("iphone");

        assertThat(result).isEmpty();
        verify(storage).getAll();
        verifyNoMoreInteractions(storage);
    }

    @Test
    void search_returnsEmpty_whenNoItemsMatch() {
        var p1 = mockProduct("Samsung TV");
        var p2 = mockProduct("Sony PlayStation");
        var a1 = mockArticle("Guide: choose TV");
        var a2 = mockArticle("How to calibrate your TV");

        when(storage.getAll()).thenCallRealMethod();
        when(storage.getProducts()).thenReturn(List.of(p1, p2));
        when(storage.getArticles()).thenReturn(List.of(a1, a2));

        var result = searchService.search("iphone");

        assertThat(result).isEmpty();
        verify(storage).getProducts();
        verify(storage).getArticles();
    }

    @Test
    void search_returnsMergedResults_whenProductsAndArticlesMatch() {
        var p1 = mockProduct("Apple iPhone 15");
        var p2 = mockProduct("Samsung TV");
        var a1 = mockArticle("How to choose an iPhone");
        var a2 = mockArticle("Best TVs 2025");

        when(storage.getAll()).thenCallRealMethod();
        when(storage.getProducts()).thenReturn(List.of(p1, p2));
        when(storage.getArticles()).thenReturn(List.of(a1, a2));

        var result = searchService.search("iPhone");

        assertThat(result).hasSize(2);
        verify(storage).getProducts();
        verify(storage).getArticles();
    }

    @Test
    void search_isCaseInsensitive() {
        var p1 = mockProduct("Apple iPhone 15 Pro");
        var a1 = mockArticle("iPhone camera tips");

        when(storage.getAll()).thenCallRealMethod();
        when(storage.getProducts()).thenReturn(List.of(p1));
        when(storage.getArticles()).thenReturn(List.of(a1));

        var result = searchService.search("IPHONE");

        assertThat(result).hasSize(2);
    }

    private static Product mockProduct(String term) {
        var p = mock(Product.class, withSettings());
        when(p.getSearchTerm()).thenReturn(term);
        return p;
    }

    private static Article mockArticle(String term) {
        var a = mock(Article.class, withSettings());
        when(a.getSearchTerm()).thenReturn(term);
        return a;
    }
}
