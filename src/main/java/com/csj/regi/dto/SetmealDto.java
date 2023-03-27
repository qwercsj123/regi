package com.csj.regi.dto;


import com.csj.regi.domain.Setmeal;
import com.csj.regi.domain.SetmealDish;
import lombok.Data;

import java.util.List;


@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
