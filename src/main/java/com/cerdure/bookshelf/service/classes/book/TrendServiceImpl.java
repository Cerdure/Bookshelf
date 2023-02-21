package com.cerdure.bookshelf.service.classes.book;

import com.cerdure.bookshelf.domain.book.Trend;
import com.cerdure.bookshelf.repository.book.TrendRepository;
import com.cerdure.bookshelf.service.classes.book.interfaces.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendServiceImpl implements TrendService {

    private final TrendRepository trendRepository;

    @Override
    public List<Trend> findTop10() {
        List<Trend> trends = trendRepository.findTop10ByOrderByCountDesc();
        while (trends.size() < 10) {
            trends.add(Trend.builder().searchData("-").build());
        }
        return trends;
    }

    @Override
    public void insert(String searchData) {
        if (searchData.length() > 1) {
            Trend trend = trendRepository.findBySearchDataIgnoreCase(searchData)
                    .orElseGet(() -> Trend.builder().searchData(searchData).count(0).build());
            trend.countPlus();
            trendRepository.save(trend);
        }
    }
}
