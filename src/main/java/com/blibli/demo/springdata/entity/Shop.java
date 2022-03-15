package com.blibli.demo.springdata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @Column(name = "shop_id")
    private String id;

    @Column(name = "shop_name")
    private String name;

    @Version
    @Column
    private Long version;
}
