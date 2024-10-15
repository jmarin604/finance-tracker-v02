package com.finance.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.tracker.dto.CategoryDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.service.CategoryService;
import com.finance.tracker.utility.Utilities;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        categoryService.createCategory(categoryDTO, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        categoryService.updateCategory(categoryDTO, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        categoryService.deleteCategory(categoryId, user);
        return ResponseEntity.ok().build();
    }

}
