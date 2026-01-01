package com.example.todo.service;

import com.example.todo.exception.InformationExistException;
import com.example.todo.model.Category;
import com.example.todo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category categoryObject){
        System.out.println("Service Calling createCategory ==>");
        Category category = categoryRepository.findByName(categoryObject.getName());

        if (category != null){
            throw new InformationExistException("category with name "+category.getName() + " already exist");
        }
        else {
                return categoryRepository.save(categoryObject);
        }
    }
}
