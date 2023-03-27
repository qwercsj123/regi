package com.csj.regi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Setmeal;
import com.csj.regi.dto.SetmealDto;
import com.csj.regi.service.SetmealDishService;
import com.csj.regi.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:csj
 * @version:1.0
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public Result<String> save(@RequestBody SetmealDto setmealDto){
        return setmealService.saveSetMeal(setmealDto);
    }

    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize,String name){
        return  setmealService.pageSetMeal(page,pageSize,name);
    }

    @GetMapping("/{id}")
    public Result<SetmealDto> getSetmealBackData(@PathVariable Long id){
        //调用自定义查询方法
        SetmealDto setmealDto = setmealService.setmealBackData(id);
        return Result.success(setmealDto);
    }
    @PutMapping
    public Result<String> setmealUpdate(@RequestBody SetmealDto setmealDto){
        setmealService.setmealUpdate(setmealDto);
        return Result.success("修改成功");
    }


    @PostMapping("/status/{status}")
    public Result<String> statusUpdate(@PathVariable int status, Long[] ids) {
        //将数组转换成集合
       return setmealService.updateStatus(ids,status);
    }

    @DeleteMapping
    public Result<String> delete(Long[] ids){
        return setmealService.deleteSetMeal(ids);
    }

    @GetMapping("/list")
    public Result<List<Setmeal>> list(Setmeal setmeal){
        return setmealService.listSetMeal(setmeal);
    }
}
