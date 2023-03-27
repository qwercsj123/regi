package com.csj.regi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.common.Result;
import com.csj.regi.domain.Employee;
import com.csj.regi.service.EmployeeService;
import com.csj.regi.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
* @author 23200
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-03-23 13:44:27
*/
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

    @Override
    public Result<Employee> login(HttpServletRequest request,String userName, String password) {
        String newPassword= DigestUtils.md5DigestAsHex(password.getBytes());

        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userName);

        Employee emp = this.getOne(queryWrapper);

        if(emp==null){
            return Result.error("登录失败");
        }

        if(!emp.getPassword().equals(newPassword)){
            return Result.error("密码错误");
        }

        if(emp.getStatus()==0){
            return Result.error("账号已经被禁用");
        }

        request.getSession().setAttribute("employee",emp.getId());

        return Result.success(emp);
    }

    @Override
    public Result<String> loginOut(HttpServletRequest request) {
        if(request.getSession().getAttribute("employee")==null){
            return Result.error("您还没有登录");
        }
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @Override
    public Result<String> savaEmployee(HttpServletRequest request,Employee employee) {
        //设置员工的初始密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(new Date());//创建时间
        employee.setUpdateTime(new Date());//修改时间

        //获取当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);

        //设置最后更新的人
        employee.setUpdateUser(empId);

        this.save(employee);
        return  Result.success("添加成功");
    }

    /**
     * 员工分页查询
     * @param page  当前页
     * @param pageSize 页面的大小
     * @param name 名字
     * @return
     */
    @Override
    public Result<Page> page(int page, int pageSize, String name) {
        //构建分页构造器
        Page pageInfo=new Page(page,pageSize);
        //添加过滤条件
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),"name",name);
        //添加排序条件
        queryWrapper.orderByDesc("update_time");
        this.page(pageInfo,queryWrapper);

        return Result.success(pageInfo);
    }

    @Override
    public Result<String> updateMessage(HttpServletRequest request,Employee employee) {
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(new Date());
        employee.setUpdateUser(empId);
        this.updateById(employee);
        return Result.success("员工信息修改成功");
    }

    @Override
    public Result<Employee> getEmployeeById(Long id) {
        Employee employee = this.getById(id);
        if(employee==null){
            return Result.error("没有该用户");
        }
        return Result.success(employee);

    }

}




