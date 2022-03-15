package com.blibli.demo.springdata.repository;

import com.blibli.demo.springdata.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String>, ProductCustomRepository {

    Product findByName(String name);
}
