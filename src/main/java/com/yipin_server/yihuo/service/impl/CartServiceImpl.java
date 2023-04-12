package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Cart;
import com.yipin_server.yihuo.entity.WxUser;
import com.yipin_server.yihuo.mapper.AccountMapper;
import com.yipin_server.yihuo.mapper.CartMapper;
import com.yipin_server.yihuo.service.AccountService;
import com.yipin_server.yihuo.service.CartService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
}
