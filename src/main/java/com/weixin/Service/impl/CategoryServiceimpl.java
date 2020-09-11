package com.weixin.Service.impl;

import com.weixin.DAO.ProductCategoryDao;
import com.weixin.Service.CategoryService;
import com.weixin.dataobject.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    private ProductCategoryDao dao;



    @Override
    public ProductCategory findOne(Integer categoryId) {
        return dao.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return dao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTayeIn(List<Integer> categoryTypeList) {
        return dao.findByCategoryTayeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return dao.save(productCategory);
    }
}
