package com.blibli.demo.springdata.service.impl;

import com.blibli.demo.springdata.entity.Product;
import com.blibli.demo.springdata.entity.Shop;
import com.blibli.demo.springdata.model.CreateProductRequest;
import com.blibli.demo.springdata.model.CreateShopRequest;
import com.blibli.demo.springdata.model.FilterProductRequest;
import com.blibli.demo.springdata.model.UpdateProductRequest;
import com.blibli.demo.springdata.repository.ProductRepository;
import com.blibli.demo.springdata.repository.ShopRepository;
import com.blibli.demo.springdata.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopRepository shopRepository;

    @Override
    public Product create(CreateProductRequest request) {
        Shop shop = shopRepository.findById(request.getShopId()).get();
        Product product = Product.builder()
                .shopId(shop)
                .build();

        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);
    }

    @Override
    public Product find(String id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Page<Product> findByFilter(FilterProductRequest request) {
        return productRepository.findByFilter(request);
    }

    @Override
    public Product update(String id, UpdateProductRequest request) {
        Shop shop = shopRepository.findById(request.getShopId()).get();

        Product product = productRepository.findById(id).get();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setShopId(shop);

        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Shop createShop(CreateShopRequest request) {
        Shop shop = Shop.builder()
                .build();

        BeanUtils.copyProperties(request, shop);

        return shopRepository.save(shop);
    }

}
