package com.csj.regi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.common.BaseContext;
import com.csj.regi.common.Result;
import com.csj.regi.domain.ShoppingCart;
import com.csj.regi.mapper.ShoppingCartMapper;
import com.csj.regi.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 23200
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2023-03-24 22:56:34
*/
@SuppressWarnings({"all"})
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

    @Override
    public Result<ShoppingCart> addToShoppingCart(ShoppingCart shoppingCart) {
        //设置用户的id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);//确定是哪个用户的购物车
        //查询当前的菜品或者是套餐是否存在
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        if(dishId != null){
            //是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            //是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        ShoppingCart cart = this.getOne(queryWrapper); //从数据库中查出来的
        if(cart != null){
            //如果已经存在，那么在以往的数量上面加1
            Integer number = cart.getNumber();
            cart.setNumber(number+1);
        }else {//注意这里的操作对象是shoppingcart
            shoppingCart.setNumber(1);
            this.save(shoppingCart);
            cart=shoppingCart;
        }
        return Result.success(cart);
    }

    @Override
    public Result<List<ShoppingCart>> listCart() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = this.list(queryWrapper);
        return Result.success(list);
    }

    @Override
    public Result<String> cleanCart() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        this.remove(queryWrapper);
        return Result.success("清空购物车成功");
    }

    @Override
    public Result<ShoppingCart> sub(ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // 基本查找条件
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        Long dishId = shoppingCart.getDishId();

        // 判断菜品或者套餐

        if(dishId != null){
            // 购物车为菜品
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else {
            // 购物车为套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        // 判断个数，如果为正数，减一；如果为0，抛出异常
        ShoppingCart shoppingCartServiceOne = this.getOne(queryWrapper);
        Integer number = shoppingCartServiceOne.getNumber();

        if (number != 0){
            shoppingCartServiceOne.setNumber(number - 1);
            this.updateById(shoppingCartServiceOne);
        }else {
            this.removeById(shoppingCartServiceOne);
        }

        return Result.success(shoppingCartServiceOne);
    }
}






