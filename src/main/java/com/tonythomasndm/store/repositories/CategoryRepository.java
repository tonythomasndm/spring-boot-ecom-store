package com.tonythomasndm.store.repositories;

import com.tonythomasndm.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}