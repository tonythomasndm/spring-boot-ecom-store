package com.tonythomasndm.store.repositories;

import com.tonythomasndm.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //@Query("select p from Product p where p.category.id = :categoryId")
    @EntityGraph(attributePaths = "category")
    List<Product> findAllByCategoryId(Byte categoryId);

    @Override
    @EntityGraph(attributePaths = "category")
    List<Product> findAll();
}

// two ways to provide data in request 1. query param - filter, sort, paginate
// anotehr way using request headers (just liek key value pairs)
// ur browser will probably sne some additional headers to backend - often used for metadta authentciation, cahing
// by default we prefix them with x-