package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.WxUser;
import com.yipin_server.yihuo.mapper.AccountMapper;
import com.yipin_server.yihuo.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, WxUser> implements AccountService {
}
