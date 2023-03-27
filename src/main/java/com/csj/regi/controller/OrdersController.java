package com.csj.regi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Orders;
import com.csj.regi.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:csj
 * @version:1.0
 */
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){
        return ordersService.submitOrders(orders);
    }

    @GetMapping("/userPage")
    public Result<Page> pagePhone(int page, int pageSize){
        return ordersService.pagePhone(page,pageSize);
    }
}
