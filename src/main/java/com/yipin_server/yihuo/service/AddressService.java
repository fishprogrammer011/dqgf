package com.yipin_server.yihuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Address;
import com.yipin_server.yihuo.entity.WxUser;
import com.yipin_server.yihuo.mapper.AccountMapper;
import com.yipin_server.yihuo.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public interface AddressService extends IService<Address> {

}
