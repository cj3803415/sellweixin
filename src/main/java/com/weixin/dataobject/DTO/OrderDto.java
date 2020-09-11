package com.weixin.dataobject.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weixin.Utils.serialize.Date2LongSerializer;
import com.weixin.dataobject.OrderDetail;
import com.weixin.enums.OrderStatusEnum;
import com.weixin.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    /*订单ID*/

    private String orderId;

    /*买家名字*/
    private String buyerName;

    /*买家手机号*/
    private String buyerPhone;

    /*买家地址*/
    private String buyerAddress;

    /*买家微信Openid*/
    private String buyerOpenid;

    /*订单总金额*/
    private BigDecimal orderAmount;

    /*订单状态，默认为新下单*/
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();

    /*支付状态，默认零为未支付*/
    private Integer payStatus=PayStatusEnum.WAIT.getCode();

    /*创建时间*/
    @JsonSerialize(using=Date2LongSerializer.class)
    private Date createTime;

    /*更新时间*/
    @JsonSerialize(using=Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
