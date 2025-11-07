package org.skypro.skyshop.model.search;

import lombok.Data;

import java.util.UUID;

@Data
public class SearchResult {

    private final String id;
    private final String name;
    private final String contentType;

    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(UUID.randomUUID().toString(), searchable.getSearchTerm(), searchable.getContentType());
    }
}
