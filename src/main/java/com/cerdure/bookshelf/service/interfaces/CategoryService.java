package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.domain.book.Category;

import java.util.List;


public interface CategoryService {

    public Category findById(Integer id);
    public List<Category> findById(List<Integer> ids);
    public List<Category> findByName(String name);

}
