package com.yipin_server.yihuo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yipin_server.yihuo.entity.WxUser;

import java.util.Map;

public interface IUserService extends IService<WxUser> {

    Map<String,Object> login(String code, String encryptedData, String iv) throws Exception;

    String getPhone(String code);
}
