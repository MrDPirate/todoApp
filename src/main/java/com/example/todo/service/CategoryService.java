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
    public Category updateCategory(Long categoryId, Category categoryObject) {
        System.out.println("service calling updateCategory ==>");

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "category with id " + categoryId + " not found"
                        )
                );

        //make sure category name is unique
        if (categoryRepository.findByName(categoryObject.getName())!=null) {
            throw new InformationExistException(
                    "category " + categoryObject.getName() + " already exists"
            );
        }

        if (categoryObject.getName() != null){
            existingCategory.setName(categoryObject.getName());
        }
        if (categoryObject.getDescription() != null) {
            existingCategory.setDescription(categoryObject.getDescription());
        }
        return categoryRepository.save(existingCategory);
    }

    //Delete
    public Optional<Category> deleteCategory(Long categoryId){

        System.out.println("Service Calling deleteCategory ==>");
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()){
            categoryRepository.deleteById(categoryId);
            return category;
        }else {
            throw new InformationNotFoundException("Category with id "+categoryId+" not found");
        }
    }
}
