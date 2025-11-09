package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

@Getter
public class Article implements Searchable {

    private String title;
    private String text;
    private final UUID id;

    public Article(String title, String text, UUID id) {
        this.title = title;
        this.text = text;
        this.id = id;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return toString();
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return title + "\n" + text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Article article = (Article) o;
        return title.equals(article.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
