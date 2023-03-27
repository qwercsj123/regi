package com.csj.regi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Category;
import com.csj.regi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:csj
 * @version:1.0
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<String> save(@RequestBody Category category){
       return  categoryService.saveFood(category);
    }
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize){
        return categoryService.pageCategory(page,pageSize);
    }

    @DeleteMapping
    public Result<String> delete(Long ids){
        return categoryService.deleteCategory(ids);
    }
    @PutMapping
    public Result<String> update(@RequestBody Category category ){
        return categoryService.updateCategory(category);
    }
    @GetMapping("/list")
    public Result<List<Category>> list(Category category){
        return categoryService.listCategory(category);
    }

}
