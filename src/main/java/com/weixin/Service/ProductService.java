package com.weixin.Service;

import com.weixin.dataobject.DTO.CartDto;
import com.weixin.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    /*查询所有在架商品*/
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存

    void increaseStock(List<CartDto> cartDtoList);




    //减库存
    void decreaseStock(List<CartDto> cartDtoList);
}
