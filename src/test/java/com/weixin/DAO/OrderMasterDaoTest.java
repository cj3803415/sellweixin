package com.weixin.DAO;

import com.weixin.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    private  final String OPENID="3803415";

    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("小群");
        orderMaster.setBuyerPhone("13767156755");
        orderMaster.setBuyerAddress("江城苑东区");
        orderMaster.setBuyerOpenid("3803415");
        orderMaster.setOrderAmount(new BigDecimal(2.5));

        OrderMaster result=orderMasterDao.save(orderMaster);

        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid(){
        PageRequest request=new PageRequest(0,1);

        Page<OrderMaster> page= orderMasterDao.findByBuyerOpenid(OPENID,request);


        System.out.println(page.getTotalElements());

    }
}