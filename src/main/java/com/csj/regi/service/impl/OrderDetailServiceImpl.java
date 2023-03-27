package com.csj.regi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.domain.OrderDetail;
import com.csj.regi.service.OrderDetailService;
import com.csj.regi.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 23200
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2023-03-27 12:52:45
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




