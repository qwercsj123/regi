package com.csj.regi.mapper;

import com.csj.regi.domain.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-03-23 13:44:27
* @Entity com.csj.regi.domain.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




