package com.csj.regi.dto;

import com.csj.regi.domain.OrderDetail;
import com.csj.regi.domain.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
