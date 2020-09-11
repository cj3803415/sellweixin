package com.weixin.Service;

import com.weixin.dataobject.ProductCategory;

import java.util.List;

/*
  类目表服务层

 */
public interface CategoryService {


    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTayeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
