package com.blibli.demo.springdata.repository.impl;

import com.blibli.demo.springdata.entity.Product;
import com.blibli.demo.springdata.model.FilterProductRequest;
import com.blibli.demo.springdata.repository.ProductCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<Product> findByFilter(FilterProductRequest request) {
        String contentQueryString = "SELECT p from Product p JOIN Shop s on s.id = p.shopId";
        String countQueryString = "SELECT COUNT(1) FROM Product p JOIN Shop s on s.id = p.shopId";

        List<String> criteriaList = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();

        if(StringUtils.hasText(request.getProductName())){
            criteriaList.add("LOWER(p,name) LIKE :productName");
            parameters.put("productName", String.format("%%%s%%", request.getProductName().toLowerCase()));
        }

        if(StringUtils.hasText(request.getShopIdOrName())){
            criteriaList.add("LOWER(s.id) LIKE :shopId OR LOWER(s.name) LIKE :shopName");
        }

        if(!criteriaList.isEmpty()){
            String whereClause = "WHERE " + String.join("AND ", criteriaList);
            contentQueryString += whereClause;
            countQueryString += whereClause;
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        TypedQuery<Product> contentQuery = entityManager.createQuery(contentQueryString, Product.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countQueryString, Long.class);

        parameters.forEach((key, value) -> {
            contentQuery.setParameter(key, value);
            countQuery.setParameter(key, value);
        });

        long total = countQuery.getSingleResult();
        List<Product> products = contentQuery.getResultList();
        return new PageImpl<>(products, pageable, total);
    }
}
