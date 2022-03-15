package com.blibli.demo.springdata.validation.validator;

import com.blibli.demo.springdata.repository.ShopRepository;
import com.blibli.demo.springdata.validation.ShopExist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShopExistByIdValidator implements ConstraintValidator<ShopExist, String> {

    @Autowired
    ShopRepository shopRepository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return shopRepository.existsById(id);
    }
}
