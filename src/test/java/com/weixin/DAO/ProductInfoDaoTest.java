package com.weixin.DAO;

import com.weixin.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao dao;

    @Test
    public void saveTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("超级美味");
        productInfo.setProductIcon("http/:xxxx.com");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo result=dao.save(productInfo);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfos= dao.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfos.size());

    }
}