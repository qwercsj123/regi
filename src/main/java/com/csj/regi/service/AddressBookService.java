package com.csj.regi.service;

import com.csj.regi.common.Result;
import com.csj.regi.domain.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* @author 23200
* @description 针对表【address_book(地址管理)】的数据库操作Service
* @createDate 2023-03-24 22:19:09
*/
public interface AddressBookService extends IService<AddressBook> {
    Result<AddressBook> saveAddress(AddressBook addressBook);

    Result<AddressBook> setDefaultAddress(AddressBook addressBook);

    Result getAddress( Long id);

    Result<AddressBook> getDefaultAddress();

    Result<List<AddressBook>> listAddress(AddressBook addressBook);
}
