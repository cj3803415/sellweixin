package com.weixin.Service.impl;

import com.weixin.DAO.ProductInfoDao;
import com.weixin.Exception.SellException;
import com.weixin.Service.ProductService;
import com.weixin.dataobject.DTO.CartDto;
import com.weixin.dataobject.ProductInfo;
import com.weixin.enums.ProductStatusEnum;
import com.weixin.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceimp implements ProductService {

    @Autowired
    private ProductInfoDao dao;

    @Override
    public ProductInfo findOne(String productId) {
        return  dao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return dao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }


    @Override
    public Page<ProductInfo> findAll(org.springframework.data.domain.Pageable pageable){
        return  dao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {


        return dao.save(productInfo);
    }

    public ProductServiceimp() {
        super();
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for(CartDto cartDto: cartDtoList){
            ProductInfo productInfo=dao.findOne(cartDto.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result=productInfo.getProductStock()+cartDto.getProductQuantity();
           productInfo.setProductStock(result);
           dao.save(productInfo);

        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for(CartDto cartDto: cartDtoList){
            ProductInfo productInfo=dao.findOne(cartDto.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result=productInfo.getProductStock()-cartDto.getProductQuantity();
            if(result<0){
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);

            }
            productInfo.setProductStock(result);
            dao.save(productInfo);
        }

    }
}
