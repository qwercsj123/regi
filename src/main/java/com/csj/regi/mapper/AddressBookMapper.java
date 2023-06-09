package com.csj.regi.mapper;

import com.csj.regi.domain.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 23200
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2023-03-24 22:19:09
* @Entity com.csj.regi.domain.AddressBook
*/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




