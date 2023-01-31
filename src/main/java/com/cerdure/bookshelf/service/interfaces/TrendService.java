package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.domain.book.Trend;

import java.util.List;


public interface TrendService {
    public List<Trend> findTop10();
    public void insert(String searchData);
}
