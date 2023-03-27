package com.csj.regi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 23200
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2023-03-23 22:50:07
*/
public interface CategoryService extends IService<Category> {

    Result<String> saveFood(Category category);

    Result<Page> pageCategory(int page,int pageSize);

    Result<String> deleteCategory(Long id);//根据id来删除菜品

    void remove(Long id);//防止该分类和菜品和套餐进行关联，可以通过这个方法进行相应的判断

    Result<String> updateCategory(Category category);

    Result<List<Category>> listCategory(Category category);//根据类型来查询分类数据

}
