package com.sanket.store.repositories;

import com.sanket.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    @EntityGraph(attributePaths="category") //optimizing the hibernate query used by spring data jpa, so that he single query is used to fetch the product
    List<Product> findByCategoryId(Byte categoryId);

    //optimizing the query
    @EntityGraph(attributePaths="category") //works same as JOIN FETCH
    @Query("SELECT p FROM Product p") //jpql query
    List<Product> findAllWithCategory();  // this is a custom query and doesn't follows the conventions of spring Data jpa we have annotate it with @Query




}