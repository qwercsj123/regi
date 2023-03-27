package com.csj.regi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Dish;
import com.csj.regi.dto.DishDto;
import com.csj.regi.service.DishFlavorService;
import com.csj.regi.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author:csj
 * @version:1.0
 */
@SuppressWarnings({"all"})
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto){
        return dishService.saveDish(dishDto);
    }
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize,String name){
        return dishService.pageDish(page,pageSize,name);
    }

    @GetMapping("/{id}")
    public Result<DishDto> getMessage(@PathVariable Long id){
        return dishService.getDish(id);
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto){
        return dishService.updateDish(dishDto);
    }

    @GetMapping("/list")
    public Result<List<DishDto>> list(Dish dish){
        return dishService.listDish(dish);
    }
    @DeleteMapping
    public Result<String> deleteAll(Long[] ids){
        return dishService.deleteIds(ids);
    }

    @PostMapping("/status/{status}")
    public Result<String> updateStatus(Long[] ids,@PathVariable int status){
        return dishService.updateStatus(ids,status);
    }

}
