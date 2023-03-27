package com.csj.regi.service;

import com.csj.regi.common.Result;
import com.csj.regi.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
* @author 23200
* @description 针对表【user(用户信息)】的数据库操作Service
* @createDate 2023-03-24 21:01:09
*/
public interface UserService extends IService<User> {

    Result<String> sendMessage(User user, HttpSession session);

    Result<User> login( Map<String,String> map,HttpSession session);


}
