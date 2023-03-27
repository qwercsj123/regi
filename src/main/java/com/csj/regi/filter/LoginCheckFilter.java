package com.csj.regi.filter;

import com.alibaba.fastjson.JSON;
import com.csj.regi.common.BaseContext;
import com.csj.regi.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 防止未登录就访问主页面
 * @author:csj
 * @version:1.0
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static  final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;


        //获取本次请求的url
        String requestURI = request.getRequestURI();
        //放行的url路径
        String urls[]=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        //判断本次的请求是否要处理
        boolean check = check(urls, requestURI);

        if(check){
            chain.doFilter(request,response);
            return;
        }
        //判断用户是否登录了(管理端)
        Object employee = request.getSession().getAttribute("employee");
        if(employee!=null){
            //设置存储在当前线程里面的值
            Long empId=(Long) employee;
            BaseContext.setCurrentId(empId);
            chain.doFilter(request,response);
            return;
        }


        //移动端的用户
        Object user = request.getSession().getAttribute("user");
        if(user!=null){
            //设置存储在当前线程里面的值
            Long userId =(Long) user;
            BaseContext.setCurrentId(userId);
            chain.doFilter(request,response);
            return;
        }

        //如果未登录，通过输出流来向前端输出数据
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
    }
    //路径匹配的函数
    public boolean check(String[] urls,String requestUrl){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if(match){
                return true;
            }

        }
        return false;
    }

}
