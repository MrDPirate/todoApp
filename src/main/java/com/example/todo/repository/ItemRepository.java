package com.example.todo.repository;

import com.example.todo.model.Category;
import com.example.todo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByCategoryId(Long categoryId);
    Optional<Item> findByCategoryIdAndId(Long categoryId, Long ItemId);
    List<Item> findByCategoryIdAndUserId(Long categoryId, Long userId);
    List<Item> findByUserId(Long userId);
    Optional<Item> findByUserIdAndCategoryIdAndId(Long userId, Long categoryId, Long itemId);
}
