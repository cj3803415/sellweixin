package com.weixin.Service;

import com.weixin.dataobject.DTO.OrderDto;

/*
* 买家
* */
public interface BuyerService {

    //查询一个订单
    OrderDto findOrderOne(String openid,String orderId);

    //取消订单
    OrderDto cancelOrder(String openid,String orderId);
}
