package com.weixin.service.impl;

import com.weixin.Service.impl.ProductServiceimp;
import com.weixin.dataobject.ProductInfo;
import com.weixin.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceimpTest {

    @Autowired
    private ProductServiceimp productServiceimp;

    @Test
    public void findOne() {
        ProductInfo productInfo=productServiceimp.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfos=productServiceimp.findUpAll();
        Assert.assertNotNull(0);
    }

    @Test
    public void findAll() {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> productInfoPage=productServiceimp.findAll(request);
        System.out.println(productInfoPage.getTotalElements());

    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("超级美味的虾");
        productInfo.setProductIcon("http/:xxxx.com");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result=productServiceimp.save(productInfo);
        Assert.assertNotNull(result);
    }
}