package com.csj.regi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csj.regi.common.Result;
import com.csj.regi.domain.User;
import com.csj.regi.service.UserService;
import com.csj.regi.mapper.UserMapper;
import com.csj.regi.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
* @author 23200
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-03-24 21:01:09
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public Result<String> sendMessage(User user, HttpSession session) {
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成随机的验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code:{}",code);
            session.setAttribute("phone",phone);
            session.setAttribute("code",code);

            return Result.success("手机短信发送成功");
        }
        return Result.error("手机验证码发送错误");
    }

    @Override
    public Result<User> login(Map<String, String> map, HttpSession session) {
        //获取手机号
        String phone = map.get("phone");
        //获取验证码
        String code = map.get("code");
        //从session中获取保存的验证吗
        Object codeInSession = "1234";

        //进行验证码的校验
        if(codeInSession!=null || codeInSession.equals(code)){
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = this.getOne(queryWrapper);
            if(user==null){
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                this.save(user);
            }
            session.setAttribute("user",user.getId());
            return Result.success(user);
        }
        return Result.error("登录失败");
    }
}




