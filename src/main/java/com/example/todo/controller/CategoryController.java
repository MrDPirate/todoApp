package com.example.todo.controller;

import com.example.todo.model.Category;
import com.example.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    //Create
    @PostMapping("categories")
    public Category craeteCategory(@RequestBody Category category){
        System.out.println("Controller Calling createCategory ==>");
        return categoryService.createCategory(category);
    }
    //Read all

    //Read One

    //Update

    //Delete
}
