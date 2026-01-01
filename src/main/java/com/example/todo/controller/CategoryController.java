package com.example.todo.controller;

import com.example.todo.model.Category;
import com.example.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    //Create
    @PostMapping()
    public Category craeteCategory(@RequestBody Category category){
        System.out.println("Controller Calling createCategory ==>");
        return categoryService.createCategory(category);
    }
    //Read all

    @GetMapping()
    public List<Category> getCategory(){
        System.out.println("Controller Calling getCategory - all ==>");
        return categoryService.getCategory();
    }

    //Read One
    @GetMapping("{categoryId}")
    public Optional<Category> getCategory(@PathVariable(value = "categoryId") Long id){
        System.out.println("Controller Calling getCategory - one ==>");
        return categoryService.getCategory(id);
    }

    //Update
    @PutMapping("{categoryId}")
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category categoryObject){
        System.out.println("Calling updateCategory ==>");
        return categoryService.updateCategory(categoryId,categoryObject);
    }

    //Delete
    @DeleteMapping(path = "{categoryId}")
    public Optional<Category> deleteCategory(@PathVariable(value = "categoryId") Long id){
        System.out.println("Controller Calling deleteCategory ==>");
        return categoryService.deleteCategory(id);
    }
}
