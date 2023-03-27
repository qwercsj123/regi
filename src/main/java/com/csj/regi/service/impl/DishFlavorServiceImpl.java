package com.csj.regi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.domain.DishFlavor;
import com.csj.regi.service.DishFlavorService;
import com.csj.regi.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 23200
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-03-24 13:05:02
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




