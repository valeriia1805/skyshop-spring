package org.skypro.skyshop.service;

import lombok.RequiredArgsConstructor;
import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final StorageService storageService;

    public Collection<SearchResult> search(String query) {
        return storageService.getAll().stream()
                .filter(x -> x.getSearchTerm().contains(query))
                .map(SearchResult::fromSearchable)
                .toList();
    }
}
