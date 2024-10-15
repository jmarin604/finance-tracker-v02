package com.finance.tracker.service;

import com.finance.tracker.dto.CategoryDTO;
import com.finance.tracker.exception.NotFoundException;

public interface CategoryService {

    public void createCategory(CategoryDTO category, String userCreation) throws NotFoundException;

    public void updateCategory(CategoryDTO category, String userModification) throws NotFoundException;

    public void deleteCategory(Integer categoryId, String userDeletion) throws NotFoundException;

}
