package com.blibli.demo.springdata.controller;

import com.blibli.demo.springdata.entity.Shop;
import com.blibli.demo.springdata.model.CreateShopRequest;
import com.blibli.demo.springdata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {

    @Autowired
    ProductService productService;

    @PostMapping(path = "/api/shop")
    public Shop createShop(@RequestBody CreateShopRequest request){
        return productService.createShop(request);
    }
}
