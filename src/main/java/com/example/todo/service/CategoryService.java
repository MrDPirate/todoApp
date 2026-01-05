package com.example.todo.service;

import com.example.todo.exception.InformationExistException;
import com.example.todo.exception.InformationNotFoundException;
import com.example.todo.model.Category;
import com.example.todo.model.User;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails =
                (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    //Create
    public Category createCategory(Category categoryObject){
        System.out.println("Service Calling createCategory ==>");
        Category category = categoryRepository.findByUserIdAndName(CategoryService.getCurrentLoggedInUser().getId()
                ,categoryObject.getName());

        if (category != null){
            throw new InformationExistException("category with name "+category.getName() + " already exist");
        }
        else {
            categoryObject.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(categoryObject);
        }
    }

    //Read all
    public List<Category> getCategory(){
        System.out.println("Service Calling getCategory ==>");
        return categoryRepository.findByUserId(CategoryService.getCurrentLoggedInUser().getId());
    }

    //Read one
    public Optional<Category> getCategory(Long id){
        System.out.println("Service Calling getCategory ==>");
        Optional<Category> category = categoryRepository.findByIdAndUserId(id,CategoryService.getCurrentLoggedInUser().getId());
        if (category.isPresent()){
            return category;
        }else {
            throw new InformationNotFoundException("Category with id "+id+" not found");
        }
    }

    //Update
    public Category updateCategory(Long categoryId, Category categoryObject) {
        System.out.println("service calling updateCategory ==>");

        Category existingCategory = categoryRepository.findByIdAndUserId(categoryId,CategoryService.getCurrentLoggedInUser().getId())
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "category with id " + categoryId + " not found"
                        )
                );

        //make sure category name is unique
        if (categoryRepository.findByUserIdAndName(CategoryService.getCurrentLoggedInUser().getId(), categoryObject.getName())!=null) {
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
        Optional<Category> category = categoryRepository.findByIdAndUserId(categoryId,CategoryService.getCurrentLoggedInUser().getId());

        if (category.isPresent()){
            categoryRepository.deleteById(categoryId);
            return category;
        }else {
            throw new InformationNotFoundException("Category with id "+categoryId+" not found");
        }
    }
}
