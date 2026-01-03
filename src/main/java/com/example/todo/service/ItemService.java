package com.example.todo.service;

import com.example.todo.exception.InformationNotFoundException;
import com.example.todo.exception.InformationNotMatchException;
import com.example.todo.model.Category;
import com.example.todo.model.Item;
import com.example.todo.repository.CategoryRepository;
import com.example.todo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //Create
    public Item createItem(Long categoryId, Item itemObject) {
        System.out.println("Service Calling createItem ==>");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new InformationNotFoundException("Category with id " + categoryId + " not found"));

        itemObject.setCategory(category);
        return itemRepository.save(itemObject);
    }

    //Get All
    public List<Item> getAllItem(Long categoryId) {
        System.out.println("Service Calling getAllItem ==>");
        return itemRepository.findByCategoryId(categoryId);
    }

    //Get one
    public Item getItem(Long categoryId,Long itemId) {
        System.out.println("Service Calling getItem ==>");
        Item item = itemRepository.findByCategoryIdAndId(categoryId,itemId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "item with id " + itemId +" and category id "+categoryId+ " not found"
                        )
                );
            return item;

    }

    //Update
    public Item updateItem(Long categoryId, Long itemId, Item itemObject) {
        System.out.println("Service Calling updateItem ==>");

        Item existingItem = itemRepository.findByCategoryIdAndId(categoryId,itemId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "item with id " + itemId +" and category id "+categoryId+ " not found"
                        )
                );

        if (itemObject.getName() != null) {
            existingItem.setName(itemObject.getName());
        }

        if (itemObject.getDescription() != null) {
            existingItem.setDescription(itemObject.getDescription());
        }

        if (itemObject.getDueDate() != null) {
            existingItem.setDueDate(itemObject.getDueDate());
        }

        return itemRepository.save(existingItem);

    }

    //Delete
    public Item deleteItem(Long categoryId,Long itemId) {

        System.out.println("Service Calling deleteItem ==>");
        Item item = itemRepository.findByCategoryIdAndId(categoryId,itemId)
                .orElseThrow(() ->
                        new InformationNotFoundException(
                                "item with id " + itemId +" and category id "+categoryId+ " not found"
                        )
                );


            itemRepository.deleteById(itemId);
            return item;
    }
}
