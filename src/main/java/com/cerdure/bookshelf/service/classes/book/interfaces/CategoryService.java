package com.cerdure.bookshelf.service.classes.book.interfaces;

import com.cerdure.bookshelf.domain.book.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Integer id);
    List<Category> findById(List<Integer> ids);
    List<Category> findByName(String name);
}
