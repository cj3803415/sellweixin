package com.weixin.service.impl;

import com.weixin.Service.CategoryService;
import com.weixin.Service.impl.CategoryServiceimpl;
import com.weixin.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceimplTest {

    @Autowired
    private CategoryServiceimpl categoryServiceimpl;

    @Test
    public void findOne() {
        ProductCategory productCategory=categoryServiceimpl.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> list=categoryServiceimpl.findAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findByCategoryTayeIn() {
        List<ProductCategory> list=categoryServiceimpl.findByCategoryTayeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryTaye(10);
        ProductCategory result=categoryServiceimpl.save(productCategory);
        Assert.assertNotNull(result);
    }
}