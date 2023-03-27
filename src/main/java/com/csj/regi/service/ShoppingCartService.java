package com.csj.regi.service;

import com.csj.regi.common.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csj.regi.domain.ShoppingCart;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* @author 23200
* @description 针对表【shopping_cart(购物车)】的数据库操作Service
* @createDate 2023-03-24 22:56:34
*/
public interface ShoppingCartService extends IService<ShoppingCart> {

    //添加进购物车的功能
    Result<ShoppingCart> addToShoppingCart(ShoppingCart shoppingCart);

    //展示购物车的内容
    Result<List<ShoppingCart>>  listCart();

    //清空购物车
    Result<String> cleanCart();


    Result<ShoppingCart> sub(ShoppingCart shoppingCart);
}
