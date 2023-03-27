package com.csj.regi.mapper;

import com.csj.regi.domain.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2023-03-27 12:37:49
* @Entity com.csj.regi.domain.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




