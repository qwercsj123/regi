package com.csj.regi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.common.CustomException;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Category;
import com.csj.regi.domain.Setmeal;
import com.csj.regi.domain.SetmealDish;
import com.csj.regi.dto.SetmealDto;
import com.csj.regi.service.CategoryService;
import com.csj.regi.service.SetmealDishService;
import com.csj.regi.service.SetmealService;
import com.csj.regi.mapper.SetmealMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 23200
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2023-03-24 11:27:27
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    private SetmealDishService setmealDishService;

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Override
    public Result<String> saveSetMeal(SetmealDto setmealDto) {
        saveWithDish(setmealDto);
        return Result.success("新增成功");
    }

    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存菜单的基本信息
        this.save(setmealDto);
        //保存和菜单相关的信息
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        dishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId().toString());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(dishes);
    }

    @Override
    public Result<Page> pageSetMeal(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage=new Page<>();


        QueryWrapper<Setmeal> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(name!=null,"name",name);
        queryWrapper.orderByDesc("update_time");
        this.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> collect = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            //根据分类的id来查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);

            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(collect);
        return Result.success(dtoPage);
    }

    @Override
    public SetmealDto setmealBackData(Long id) {
        SetmealDto setmealDto = new SetmealDto();

        //根据ID查询setmeal
        Setmeal setmeal = this.getById(id);

        //对象拷贝，将查询出来的setmeal给dto赋值
        BeanUtils.copyProperties(setmeal,setmealDto);

        //根据setmealId查询出setmeal_dish表中的所有对应数据
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        //将查询出来的套餐菜品对应关系列表数据添加入dto中
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }

    @Override
    public void setmealUpdate(SetmealDto setmealDto) {
        //向setmeal更新数据
        this.updateById(setmealDto);
        //向setmeal_dish表更新数据
        List<SetmealDish> list = setmealDto.getSetmealDishes();

        //因为套餐菜品关系表中没有套餐的ID，需要手动设置
        Long id = setmealDto.getId();
        list.stream().map((item)->{
            item.setSetmealId(id.toString());
            return item;
        }).collect(Collectors.toList());

        //更新setmeal_dish'表策略：先删除原来内容，后添加新内容即可
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDishService.remove(queryWrapper);
        //添加
        setmealDishService.saveBatch(list);
    }

    @Override
    public Result<String> updateStatus(Long[] id, int status) {
        List<Long> idsList = Arrays.asList(id);
        //创建更新条件构造器
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        //设置更新条件
        updateWrapper.set(Setmeal::getStatus, status).in(Setmeal::getId, idsList);//无论该商品是否在售卖或者是停售状态都设置值
                                                                                    //不用进行相应的判断
        //执行更新动作
        this.update(updateWrapper);
        return Result.success("操作成功!");
    }

    @Override
    public Result<String> deleteSetMeal(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        removeWithDish(list);
        return Result.success("删除成功");
    }

    @Transactional
    @Override
    public void removeWithDish(List<Long> id) {
        LambdaUpdateWrapper<Setmeal> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(Setmeal::getId,id);
        lambdaUpdateWrapper.eq(Setmeal::getStatus,1);
        long count = this.count(lambdaUpdateWrapper);
        if(count>0){
            throw new CustomException("该套餐正在售卖中,不能删除");
        }
        this.removeByIds(id);
        LambdaQueryWrapper<SetmealDish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId,id);
        setmealDishService.remove(queryWrapper);
    }

    @Override
    public Result<List<Setmeal>> listSetMeal(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = this.list(queryWrapper);
        return Result.success(list);
    }
}




