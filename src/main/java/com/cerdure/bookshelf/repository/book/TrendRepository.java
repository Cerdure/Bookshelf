package com.cerdure.bookshelf.repository.book;

import com.cerdure.bookshelf.domain.book.Trend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrendRepository extends JpaRepository<Trend, Long> {
    public List<Trend> findTop10ByOrderByCountDesc();
    public Optional<Trend> findBySearchDataIgnoreCase(String searchData);
}
