package com.csj.regi.mapper;

import com.csj.regi.domain.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2023-03-27 12:52:45
* @Entity com.csj.regi.domain.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




