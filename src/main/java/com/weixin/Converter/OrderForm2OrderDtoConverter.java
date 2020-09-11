package com.weixin.Converter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weixin.Exception.SellException;
import com.weixin.dataobject.DTO.OrderDto;
import com.weixin.dataobject.OrderDetail;
import com.weixin.enums.ResultEnum;
import com.weixin.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDtoConverter {

    public  static OrderDto convert(OrderForm orderForm){
        Gson gson=new Gson();


        OrderDto orderDto=new OrderDto();

        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {

            orderDetailList=gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
            }.getType());

        } catch(Exception e){
            log.error("【对象转换】错误，string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);

        }
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;

    }
}
