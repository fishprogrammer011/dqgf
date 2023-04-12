package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Address;
import com.yipin_server.yihuo.entity.Order;
import com.yipin_server.yihuo.mapper.AddressMapper;
import com.yipin_server.yihuo.mapper.OrderMapper;
import com.yipin_server.yihuo.service.AddressService;
import com.yipin_server.yihuo.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
