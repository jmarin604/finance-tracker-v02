package com.finance.tracker.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.tracker.domain.Category;
import com.finance.tracker.dto.CategoryDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.repository.CategoryRepository;
import com.finance.tracker.utility.Constants;

import lombok.extern.slf4j.Slf4j;

@Scope("singleton")
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void validateCategory(CategoryDTO category) throws NotFoundException {
        if (category == null) {
            throw new NotFoundException("Category is null");
        }
        if (category.getName() == null || category.getName().isBlank()) {
            throw new NotFoundException("Category name is null or empty");
        }
        if (category.getDescription() == null || category.getDescription().isBlank()) {
            throw new NotFoundException("Category description is null or empty");
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createCategory(CategoryDTO category, String userCreation) throws NotFoundException {
        log.info("Creating category: " + category.getName());
        validateCategory(category);

        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        newCategory.setCreatorUser(userCreation);
        newCategory.setCreationDate(new Date());
        newCategory.setStatus(Constants.ENABLED);

        categoryRepository.save(newCategory);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCategory(CategoryDTO category, String userModification) throws NotFoundException {
        log.info("Updating category: " + category.getName());
        validateCategory(category);
        Category categoryUpdate;

        Optional<Category> optionalCategory = categoryRepository.findById(category.getCatId());
        categoryUpdate = optionalCategory.orElseThrow(() -> new NotFoundException("Category not found"));
       
        categoryUpdate.setName(category.getName());
        categoryUpdate.setDescription(category.getDescription());
        categoryUpdate.setModifierUser(userModification);
        categoryUpdate.setModificationDate(new Date());

        categoryRepository.save(categoryUpdate);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCategory(Integer categoryId, String userDeletion) throws NotFoundException {
        log.info("Deleting category: " + categoryId);
        Category categoryDelete;
        
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        categoryDelete = optionalCategory.orElseThrow(() -> new NotFoundException("Category not found"));
        
        categoryDelete.setStatus(Constants.DISABLED);
        categoryDelete.setModifierUser(userDeletion);
        categoryDelete.setModificationDate(new Date());

        categoryRepository.save(categoryDelete);
    }

}
