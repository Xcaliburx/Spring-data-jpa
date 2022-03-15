package com.blibli.demo.springdata.service;

import com.blibli.demo.springdata.entity.Product;
import com.blibli.demo.springdata.entity.Shop;
import com.blibli.demo.springdata.model.CreateProductRequest;
import com.blibli.demo.springdata.model.CreateShopRequest;
import com.blibli.demo.springdata.model.FilterProductRequest;
import com.blibli.demo.springdata.model.UpdateProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    public Product create(CreateProductRequest request);

    public Product find(String id);

    Page<Product> findByFilter(FilterProductRequest request);

    public Product update(String id, UpdateProductRequest request);

    public void delete(String id);

    public Shop createShop(CreateShopRequest request);
}
