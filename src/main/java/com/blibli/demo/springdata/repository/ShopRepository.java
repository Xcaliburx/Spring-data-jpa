package com.blibli.demo.springdata.repository;

import com.blibli.demo.springdata.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, String> {

}
