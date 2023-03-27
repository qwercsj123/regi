package com.csj.regi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csj.regi.dto.DishDto;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author 23200
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2023-03-24 11:27:23
*/
public interface DishService extends IService<Dish> {

    Result<String> saveDish(DishDto dishDto);
    void savaWithFlavor(DishDto dishDto);

    Result<Page> pageDish(int page,int pageSize,String name);

    Result<DishDto> getDish(Long id);

    DishDto getByIdWithFlavor(Long id);//根据id来查询口味的信息

    Result<String> updateDish(DishDto dishDto);

    void updateWithFlavor(DishDto dishDto);

//    void updateStatus(String status, List<Long> list, HttpServletResponse response);
    Result<List<DishDto>> listDish(Dish dish);

    Result<String> deleteIds(Long[] ids);

    Result<String> updateStatus(Long[] ids,int status);
}
