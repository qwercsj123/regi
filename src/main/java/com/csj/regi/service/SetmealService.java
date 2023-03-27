package com.csj.regi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csj.regi.dto.SetmealDto;

import java.util.List;

/**
* @author 23200
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-03-24 11:27:27
*/
public interface SetmealService extends IService<Setmeal> {

    Result<String> saveSetMeal(SetmealDto setmealDto);

    void saveWithDish(SetmealDto setmealDto);//新增套餐同时保存和菜单的相关的东西

    Result<Page> pageSetMeal(int page,int pageSize,String name);//分页展示


   SetmealDto setmealBackData(Long id);//修改套餐的时候回显数据

   void setmealUpdate(SetmealDto setmealDto);//修改套餐操作

   Result<String> updateStatus(Long[] id,int status);//修改套餐的状态

    Result<String> deleteSetMeal(Long[] ids);

    void removeWithDish(List<Long> id);//删除套餐同时也删除和套餐相关的dish

    Result<List<Setmeal>> listSetMeal(Setmeal setmeal);




}
