package com.weixin.DAO;

import com.weixin.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryTayeIn(List<Integer> categoryTypeList);
}
