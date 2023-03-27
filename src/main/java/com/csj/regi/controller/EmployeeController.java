package com.csj.regi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Employee;
import com.csj.regi.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:csj
 * @version:1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工的登录
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        String username = employee.getUsername();
        String password = employee.getPassword();
        if(StringUtils.isAnyEmpty(username,password)){
            return Result.error("账号和密码不能为空");
        }
        return employeeService.login(request,username,password);
    }

    @PostMapping("/logout")
    public Result<String> loginOut(HttpServletRequest request){
         return  employeeService.loginOut(request);
    }

    //添加员工的方法
    @PostMapping
    public Result<String> save(HttpServletRequest request,@RequestBody Employee employee){
        if(employee==null){
            return Result.error("参数不能为空");
        }
       return employeeService.savaEmployee(request,employee);
    }

    //员工信息分页展示
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize,String name){
        return employeeService.page(page,pageSize,name);
    }

    @PutMapping
    public Result<String> update(HttpServletRequest request,@RequestBody Employee employee){
        return employeeService.updateMessage(request,employee);
    }

    @GetMapping("/{id}")
    public Result<Employee> getSingleEmployee(@PathVariable("id") Long id){
        return employeeService.getEmployeeById(id);
    }

}
