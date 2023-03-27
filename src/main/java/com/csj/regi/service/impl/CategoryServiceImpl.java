package com.csj.regi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.common.CustomException;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Category;
import com.csj.regi.domain.Dish;
import com.csj.regi.domain.Setmeal;
import com.csj.regi.mapper.CategoryMapper;
import com.csj.regi.service.CategoryService;
import com.csj.regi.service.DishService;
import com.csj.regi.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 23200
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-03-23 22:50:07
*/
@SuppressWarnings("all")
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public Result<String> saveFood(Category category) {
        this.save(category);
        return Result.success("新增成功");
    }

    @Override
    public Result<Page> pageCategory(int page, int pageSize) {
        Page<Category> pageInfo=new Page<>(page,pageSize);
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        this.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @Override
    public Result<String> deleteCategory(Long id) {
        remove(id);
        return Result.success("删除成功");
    }

    @Override
    public void remove(Long id) {
        //查询当前的分类是否关联菜品
        QueryWrapper<Dish> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("category_id",id);
        long count = dishService.count(queryWrapper);
        if(count>0){
            //已经关联了菜品
            throw new CustomException("当前分类已经关联了菜品");
        }
        //查询当前的分类是否关联了套餐
        QueryWrapper<Setmeal> wrapper=new QueryWrapper<>();
        wrapper.eq("category_id",id);
        long count1 = setmealService.count(wrapper);
        if(count1>0){
            //说明已经关联了套餐
            throw new CustomException("当前分类已经关联了套餐");
        }
        this.removeById(id);
    }

    @Override
    public Result<String> updateCategory(Category category) {
        this.updateById(category);
        return Result.success("修改成功");
    }

    @Override
    public Result<List<Category>> listCategory(Category category) {
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,"type",category.getType());
        queryWrapper.orderByAsc("sort").orderByDesc("update_time");
        List<Category> list = this.list(queryWrapper);
        return Result.success(list);
    }
}




