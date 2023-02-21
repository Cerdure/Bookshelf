package com.cerdure.bookshelf.service.classes.book.interfaces;

import com.cerdure.bookshelf.domain.book.Trend;

import java.util.List;

public interface TrendService {
    List<Trend> findTop10();
    void insert(String searchData);
}
