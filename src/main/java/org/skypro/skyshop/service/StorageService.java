package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        products = new HashMap<>();
        articles = new HashMap<>();
        populate();
    }

    public Collection<Article> getArticles() {
        return articles.values();
    }

    public Collection<Product> getProducts() {
        return products.values();
    }

    public Collection<Searchable> getAll() {
        Collection<Searchable> collection1 = new ArrayList<>(getArticles().stream().map(x -> (Searchable) x).toList());
        Collection<Searchable> collection2 = getProducts().stream().map(x -> (Searchable) x).toList();
        collection1.addAll(collection2);
        return collection1;
    }

    private void populate() {
        Product orange = new SimpleProduct("orange", 100, UUID.randomUUID());
        Product apple = new SimpleProduct("apple", 80, UUID.randomUUID());
        Product banana = new SimpleProduct("banana", 50, UUID.randomUUID());
        Product pineapple = new DiscountedProduct("pineapple", 150, 20, UUID.randomUUID());
        Product milk = new FixPriceProduct("milk", UUID.randomUUID());
        products.put(orange.getId(), orange);
        products.put(apple.getId(), apple);
        products.put(banana.getId(), banana);
        products.put(pineapple.getId(), pineapple);
        products.put(milk.getId(), milk);
        Article article1 = new Article("About apple", "Always buy apple because it's good.", UUID.randomUUID());
        Article article2 = new Article("English proverb", "One apple a day keeps the doctor away", UUID.randomUUID());
        articles.put(article1.getId(), article1);
        articles.put(article2.getId(), article2);
    }
}
