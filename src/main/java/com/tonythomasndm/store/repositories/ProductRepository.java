package com.tonythomasndm.store.repositories;

import com.tonythomasndm.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}