package com.csj.regi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 23200
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2023-03-23 13:44:27
*/
public interface EmployeeService extends IService<Employee> {

 Result<Employee> login(HttpServletRequest request,String userName, String password);

 Result<String> loginOut(HttpServletRequest request);

 Result<String> savaEmployee(HttpServletRequest request,Employee employee);

 Result<Page> page(int page,int pageSize,String name);

 Result<String> updateMessage(HttpServletRequest request,Employee employee);

 Result<Employee> getEmployeeById(Long id);



}
