package com.csj.regi.mapper;

import com.csj.regi.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2023-03-23 22:50:07
* @Entity com.csj.regi.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




