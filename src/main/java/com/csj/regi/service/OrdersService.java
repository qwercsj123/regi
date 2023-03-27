package com.csj.regi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 23200
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2023-03-27 12:52:42
*/
public interface OrdersService extends IService<Orders> {

    //订单的提交功能
    Result<String> submitOrders(Orders orders);

    Result<Page> pagePhone(int page, int pageSize);
}
