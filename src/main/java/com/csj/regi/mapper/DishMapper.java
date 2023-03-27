package com.csj.regi.mapper;

import com.csj.regi.domain.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2023-03-24 11:27:23
* @Entity com.csj.regi.domain.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




