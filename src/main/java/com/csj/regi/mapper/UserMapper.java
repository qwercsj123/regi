package com.csj.regi.mapper;

import com.csj.regi.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2023-03-24 21:01:09
* @Entity com.csj.regi.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




