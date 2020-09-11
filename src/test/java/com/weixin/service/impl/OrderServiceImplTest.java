package com.weixin.service.impl;

import com.weixin.Service.impl.OrderServiceImpl;
import com.weixin.dataobject.DTO.OrderDto;
import com.weixin.dataobject.OrderDetail;
import com.weixin.enums.OrderStatusEnum;
import com.weixin.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String buyerOpenid="111110";

    private final String orderid="1536316126704702605";

    @Test
    public void create() {
        OrderDto orderDto=new OrderDto();
        orderDto.setBuyerAddress("神魔网咖");
        orderDto.setBuyerName("蔡先生");
        orderDto.setBuyerPhone("13767156755");
        orderDto.setBuyerOpenid(buyerOpenid);

        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(3);
        orderDetailList.add(o1);

        OrderDetail o2=new OrderDetail();
        o2.setProductId("12345678");
        o2.setProductQuantity(3);
        orderDetailList.add(o2);

        orderDto.setOrderDetailList(orderDetailList);
        OrderDto result=orderService.create(orderDto);
        log.info("创建订单 result={}",result);
        Assert.assertNotNull(result);


    }

    @Test
    public void findOne() {
        OrderDto result=orderService.findOne(orderid);
        log.info("查询单个订单 result={}",result);
        Assert.assertEquals(orderid,result.getOrderId());

    }

    @Test
    public void findList() {
        PageRequest request=new PageRequest(1,2);
        Page<OrderDto> orderDtoPage=orderService.findList(buyerOpenid,request);
        Assert.assertNotEquals(0,orderDtoPage.getTotalElements());


    }

    @Test
    public void cancel() {
        OrderDto result=orderService.findOne(orderid);
        OrderDto orderDto=orderService.cancel(result);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),orderDto.getOrderStatus());


    }

    @Test
    public void finish() {
        OrderDto result=orderService.findOne(orderid);
        OrderDto orderDto=orderService.finish(result);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),orderDto.getOrderStatus());


    }


    @Test
    public void paid() {
        OrderDto result=orderService.findOne(orderid);
        OrderDto orderDto=orderService.paid(result);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),orderDto.getPayStatus());

    }

    @Test
    public void List(){
        PageRequest request=new PageRequest(0,2);
        Page<OrderDto>orderDtoPage=orderService.findList(request);
        Assert.assertNotEquals(0,orderDtoPage.getTotalElements());

    }
}