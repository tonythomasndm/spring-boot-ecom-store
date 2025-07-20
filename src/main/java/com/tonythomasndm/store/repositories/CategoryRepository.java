package com.tonythomasndm.store.repositories;

import com.tonythomasndm.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}