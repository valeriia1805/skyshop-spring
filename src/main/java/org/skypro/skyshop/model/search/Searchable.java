package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {

    String getSearchTerm();

    String getContentType();

    default String getStringRepresentation(Searchable searchable) {
        return searchable.getSearchTerm() + " - " + searchable.getContentType();
    }

    UUID getId();
}
