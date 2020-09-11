package com.weixin.dataobject;
/**
 * 类目
 */

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    /*类目的ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /*类目的名字*/
    private String categoryName;

    /*类目的编号*/
    private Integer categoryTaye;

    public ProductCategory(){

    }




}
