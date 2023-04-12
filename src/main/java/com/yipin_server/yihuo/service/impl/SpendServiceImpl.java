package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Spend;
import com.yipin_server.yihuo.entity.WxUser;
import com.yipin_server.yihuo.mapper.AccountMapper;
import com.yipin_server.yihuo.mapper.SpendMapper;
import com.yipin_server.yihuo.service.AccountService;
import com.yipin_server.yihuo.service.SpendService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class SpendServiceImpl extends ServiceImpl<SpendMapper, Spend> implements SpendService {
}
