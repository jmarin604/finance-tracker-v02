package com.finance.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.tracker.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
