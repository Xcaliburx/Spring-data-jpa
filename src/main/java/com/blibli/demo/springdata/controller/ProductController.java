package com.blibli.demo.springdata.controller;

import com.blibli.demo.springdata.entity.Product;
import com.blibli.demo.springdata.entity.Shop;
import com.blibli.demo.springdata.model.*;
import com.blibli.demo.springdata.service.ProductService;
import com.blibli.demo.springdata.validation.ProductExist;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@Validated
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation("Create new product")
    @PostMapping(path = "/api/product",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ProductResponse> create (@Valid @RequestBody CreateProductRequest request){
        Product product = productService.create(request);
        return Response.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(product))
                .build();
    }

    @ApiOperation("Find a product")
    @GetMapping(path = "/api/product/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ProductResponse> find(@ProductExist @PathVariable String id){
        Product product = productService.find(id);
        return Response.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(product))
                .build();
    }

    @ApiOperation("Find products by filter request")
    @PostMapping(path = "/api/product/filter",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProductResponse>> findByFIlter(@RequestBody FilterProductRequest request){
        Page<Product> productPage = productService.findByFilter(request);
        List<ProductResponse> productResponseList = productPage.map(this::toResponse).getContent();
        return Response.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(productResponseList)
                .pagination(toResponse(productPage))
                .build();
    }

    @ApiOperation("Update product")
    @PutMapping(path = "/api/product/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ProductResponse> update(@ProductExist @PathVariable String id, @Valid @RequestBody UpdateProductRequest request){
        Product product = productService.update(id, request);
        return Response.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(product))
                .build();
    }

    @ApiOperation("Delete a product")
    @DeleteMapping(path = "/api/product/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> delete(@ProductExist @PathVariable String id){
        productService.delete(id);
        return Response.<Boolean>builder()
                .status(HttpStatus.OK.value())
                .data(true)
                .build();
    }

    private Response.Pagination toResponse(Page<?> page){
        return Response.Pagination.builder()
                .page(page.getNumber())
                .size((long) page.getSize())
                .totalItem(page.getTotalElements())
                .build();
    }

    private ProductResponse toResponse(Product product){
        ProductResponse productResponse = ProductResponse.builder()
                .shopResponse(toResponse(product.getShopId()))
                .build();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    private ShopResponse toResponse(Shop shop){
        ShopResponse shopResponse = ShopResponse.builder().build();
        BeanUtils.copyProperties(shop, shopResponse);
        return shopResponse;
    }


}
