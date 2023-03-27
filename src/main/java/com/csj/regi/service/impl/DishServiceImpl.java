package com.csj.regi.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Category;
import com.csj.regi.domain.Dish;
import com.csj.regi.domain.DishFlavor;
import com.csj.regi.dto.DishDto;
import com.csj.regi.service.CategoryService;
import com.csj.regi.service.DishFlavorService;
import com.csj.regi.service.DishService;
import com.csj.regi.mapper.DishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 23200
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-03-24 11:27:23
*/
@SuppressWarnings({"all"})
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Override
    public Result<String> saveDish(@RequestBody DishDto dishDto) {
        savaWithFlavor(dishDto);
        return Result.success("保存成功");
    }

    @Transactional
    //新增菜品的同时要保存口味数据
    @Override
    public void savaWithFlavor(DishDto dishDto) {
        this.save(dishDto); //保存基本的菜品数据

        Long dishId = dishDto.getId();//获取菜品的id

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存口味
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public Result<Page> pageDish(int page, int pageSize, String name) {
        Page<Dish> pageInfo=new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage=new Page<>();

        QueryWrapper<Dish> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(name!=null,"name",name);
        queryWrapper.orderByDesc("update_time");

        this.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records=pageInfo.getRecords();
        List<DishDto> list=records.stream().map((item)->{
            DishDto dishDto=new DishDto();

            BeanUtils.copyProperties(item,dishDto);
            Long categoryId=item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());

            return dishDto;
        }).collect(Collectors.toList());


        dishDtoPage.setRecords(list);
        return Result.success(dishDtoPage);
    }

    @Override
    public Result<DishDto> getDish(Long id) {
        DishDto dishDto = getByIdWithFlavor(id);
        return Result.success(dishDto);
    }

    //查询菜品和菜品的口味信息
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品的基本信息
        Dish dish = this.getById(id);

        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        //查询菜品的口味信息
        QueryWrapper<DishFlavor> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("dish_id",dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        return dishDto;
    }

    @Override
    public Result<String> updateDish(DishDto dishDto) {
        updateWithFlavor(dishDto);
        return Result.success("保存成功");
    }

    @Transactional//开启事务
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish的基本数据
        this.updateById(dishDto);

        //清理原先的数据
        QueryWrapper<DishFlavor> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("dish_id",dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //添加数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

//    @Override
//    public Result<List<Dish>> listDish(Dish dish) {
//        QueryWrapper<Dish> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId()!=null,"category_id",dish.getCategoryId());
//        queryWrapper.eq("status",1);//查询状态为1的菜品
//        queryWrapper.orderByAsc("sort").orderByDesc("update_time");
//        List<Dish> list = this.list(queryWrapper);
//        return Result.success(list);
//    }
    @Override
    public Result<List<DishDto>> listDish(Dish dish) {
        QueryWrapper<Dish> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,"category_id",dish.getCategoryId());
        queryWrapper.eq("status",1);//查询状态为1的菜品
        queryWrapper.orderByAsc("sort").orderByDesc("update_time");
        List<Dish> list = this.list(queryWrapper);

        List<DishDto> dishDtoList=list.stream().map((item)->{
            DishDto dishDto=new DishDto();

            BeanUtils.copyProperties(item,dishDto);
            Long categoryId=item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());


            Long id = item.getId();//菜品的id
            LambdaQueryWrapper<DishFlavor> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId,id);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(wrapper);

            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return Result.success(dishDtoList);
    }

    @Override
    public Result<String> deleteIds(Long[] ids) {
        //将数组转为集合
        List<Long> longs = Arrays.asList(ids);
        //批量删除
        this.removeByIds(longs);
        return Result.success("删除成功！");
    }

    @Override
    public Result<String> updateStatus(Long[] ids, int status) {
        //将数组转为集合
        List<Long> list = Arrays.asList(ids);
        //创建更新的条件构造器
        LambdaUpdateWrapper<Dish> queryWrapper = new LambdaUpdateWrapper<>();
        //设置修改状态和条件
        queryWrapper.set(Dish::getStatus,status).in(Dish::getId,list);
        //执行更新操作
        this.update(queryWrapper);
        return Result.success("操作成功！");
    }

}




