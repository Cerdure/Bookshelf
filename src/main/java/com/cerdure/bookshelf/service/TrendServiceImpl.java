package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.book.Trend;
import com.cerdure.bookshelf.repository.TrendRepository;
import com.cerdure.bookshelf.service.interfaces.TrendService;
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
        while (trends.size()<10) {
            System.out.println("trends.size() = " + trends.size());
            trends.add(Trend.builder().searchData("-").build());
        }
        return trends;
    }

    @Override
    public void insert(String searchData) {
        System.out.println("searchData = " + searchData);
        Trend trend = trendRepository.findBySearchDataIgnoreCase(searchData);
        if(trend == null){
            trendRepository.save(Trend.builder().searchData(searchData).build());
        } else {
            trend.countPlus();
            trendRepository.save(trend);
        }
    }
}
