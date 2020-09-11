package com.weixin.DAO;

import com.weixin.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao dao;

    @Test
    public void findOneTest(){

        ProductCategory productCategory=dao.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryTaye(3);
        dao.save(productCategory);

    }

    @Test
    public void findByCategoryTayeInTest(){
        List<Integer> list=Arrays.asList(2,3,4);


        List<ProductCategory> result=dao.findByCategoryTayeIn(list);
        Assert.assertNotEquals(0,result.size());
    }



}