package com.csj.regi.mapper;

import com.csj.regi.domain.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2023-03-27 12:52:42
* @Entity com.csj.regi.domain.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




