package com.example.todo.controller;

import com.example.todo.model.Category;
import com.example.todo.model.Item;
import com.example.todo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categories/{categoryId}/items")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    //Create
    @PostMapping()
    public Item createItem(@PathVariable(value = "categoryId")Long categoryId, @RequestBody Item itemObject){
        System.out.println("Controller Calling createItem ==>");
        return itemService.createItem(categoryId,itemObject);
    }

    //Read All
    @GetMapping()
    public List<Item> getAllCategoryItems(@PathVariable(value = "categoryId")Long categoryId){
        System.out.println("Controller Calling readAllItems ==>");
        return itemService.getAllCategoryItems(categoryId);
    }

    @GetMapping()
    public List<Item> readAllItems(){
        System.out.println("Controller Calling readAllItems ==>");
        return itemService.getAllItem();
    }


    //Read one
    @GetMapping(path = "/{itemId}")
    public Item readOneItem(@PathVariable(value = "categoryId")Long categoryId,@PathVariable(value = "itemId")Long itemId){
        System.out.println("Controller Calling readOneItems ==>");
        return itemService.getItem(categoryId, itemId);
    }

    //Update
    @PutMapping(path = "/{itemId}")
    public Item updateItem(@PathVariable(value = "categoryId")Long categoryId,
                           @PathVariable(value = "itemId")Long itemId,
                           @RequestBody Item itemObject){
        System.out.println("Controller Calling updateItem ==>");
        return itemService.updateItem(categoryId,itemId,itemObject);
    }

    //Delete
    @DeleteMapping(path = "/{itemId}")
    public Item deleteItem(@PathVariable(value = "categoryId") Long categoryId,
                           @PathVariable(value = "itemId") Long itemId) {
        System.out.println("Controller Calling deleteItem ==>");
        return itemService.deleteItem(categoryId,itemId);
    }

}
