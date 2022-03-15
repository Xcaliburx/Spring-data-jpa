package com.blibli.demo.springdata.validation.validator;

import com.blibli.demo.springdata.repository.ProductRepository;
import com.blibli.demo.springdata.validation.ProductExist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductExistByIdValidator implements ConstraintValidator<ProductExist, String> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return productRepository.existsById(id);
    }
}
