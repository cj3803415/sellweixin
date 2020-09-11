package com.weixin.Service.impl;

import com.weixin.Exception.SellException;
import com.weixin.Service.BuyerService;
import com.weixin.Service.OrderService;
import com.weixin.dataobject.DTO.OrderDto;
import com.weixin.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Slf4j
public class BuyerServiceImp implements BuyerService {

    @Autowired
    private OrderService orderService;


    public BuyerServiceImp() {
        super();
    }

    @Override
    public OrderDto findOrderOne(String openid, String orderId) {

        return checkOrderOwner(openid, orderId);

    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto=checkOrderOwner(openid, orderId);
        if (orderDto == null) {
            log.error("【取消订单】查不到该订单，orderd={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDto);
    }

    private OrderDto checkOrderOwner(String openid,String orderId){
        OrderDto orderDto=orderService.findOne(orderId);
        if (orderDto==null){
            return null;
        }
        if (!orderDto.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致，openid={},orderDto={}",openid,orderDto);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;

    }

}
