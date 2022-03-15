package com.blibli.demo.springdata.repository;

import com.blibli.demo.springdata.entity.Product;
import com.blibli.demo.springdata.model.FilterProductRequest;
import org.springframework.data.domain.Page;

public interface ProductCustomRepository {

    Page<Product> findByFilter(FilterProductRequest request);
}
