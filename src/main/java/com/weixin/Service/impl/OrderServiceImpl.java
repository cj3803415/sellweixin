package com.weixin.Service.impl;

import com.weixin.Converter.OrderMaster2OrderDtoConverter;
import com.weixin.DAO.OrderDetailDao;
import com.weixin.DAO.OrderMasterDao;
import com.weixin.Exception.SellException;
import com.weixin.Service.OrderService;
import com.weixin.Service.ProductService;
import com.weixin.Utils.KeyUtil;
import com.weixin.dataobject.DTO.CartDto;
import com.weixin.dataobject.DTO.OrderDto;
import com.weixin.dataobject.OrderDetail;
import com.weixin.dataobject.OrderMaster;
import com.weixin.dataobject.ProductInfo;
import com.weixin.enums.OrderStatusEnum;
import com.weixin.enums.PayStatusEnum;
import com.weixin.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {

        String orderId=KeyUtil.genUniqueKey();
        BigDecimal orderAmount =new BigDecimal(BigInteger.ZERO);
        //1.查询商品（数量，价格）
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()){
           ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
           if(productInfo==null){
               throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

           }

            //2.计算订单总价
            orderAmount=productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                              .add(orderAmount);
           //订单详情入库\
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());

            //JAVA自带的对象属性拷贝
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDao.save(orderDetail);



        }



        //3.写入订单数据库（orderMaster OrderDetail）
        OrderMaster orderMaster=new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);

        orderMaster.setOrderAmount(orderAmount);

        orderMasterDao.save(orderMaster);

        //4.扣库存
        List<CartDto> cartDtoList=orderDto.getOrderDetailList().stream().map(e ->
                 new CartDto(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        productService.decreaseStock(cartDtoList);

        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster=orderMasterDao.findOne(orderId);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList=orderDetailDao.findByOrderId(orderId);

        if (orderDetailList==null){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);

        }
        OrderDto orderDto=new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterList=orderMasterDao.findByBuyerOpenid( buyerOpenid,  pageable);

        List<OrderDto> orderDtoList=OrderMaster2OrderDtoConverter.convert(orderMasterList.getContent());

        Page<OrderDto> orderDtoPage=new PageImpl<>(orderDtoList,pageable,orderMasterList.getTotalElements());


        return orderDtoPage;
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster=new OrderMaster();

        //判断一下订单的状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单状态不正确], orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }

        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult=orderMasterDao.save(orderMaster);
        if(updateResult == null){
            log.error("[取消订单]更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILE);
        }


        //返还库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDto={}",orderDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);

        }
        List<CartDto> cartDtoList=orderDto.getOrderDetailList().stream()
                     .map(e ->new CartDto(e.getProductId(),e.getProductQuantity())
                     ).collect(Collectors.toList());
        productService.increaseStock(cartDtoList);

        //如果已经支付则还需要退款
        if(orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO

        }

        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }

        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult=orderMasterDao.save(orderMaster);
        if(updateResult == null){
            log.error("[取消订单]更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILE);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }

        //判断支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);

        }



        //修改支付状态状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult=orderMasterDao.save(orderMaster);
        if(updateResult == null){
            log.error("[取消订单]更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILE);
        }



        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterDao.findAll(pageable);

        List<OrderDto> orderDtoList=OrderMaster2OrderDtoConverter.convert(orderMasterPage.getContent());



        return new PageImpl<OrderDto>(orderDtoList,pageable,orderMasterPage.getTotalElements());
    }
}
