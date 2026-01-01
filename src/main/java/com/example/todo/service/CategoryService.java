package com.example.todo.service;

import com.example.todo.exception.InformationExistException;
import com.example.todo.exception.InformationNotFoundException;
import com.example.todo.model.Category;
import com.example.todo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    //Create
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

    //Read all
    public List<Category> getCategory(){
        System.out.println("Service Calling getCategory ==>");
        return categoryRepository.findAll();
    }

    //Read one
    public Optional<Category> getCategory(Long id){
        System.out.println("Service Calling getCategory ==>");
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()){
            return category;
        }else {
            throw new InformationNotFoundException("Category with id "+id+" not found");
        }
    }

    //Update

    //Delete
}
