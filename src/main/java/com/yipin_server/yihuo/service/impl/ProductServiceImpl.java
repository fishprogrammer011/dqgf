package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Product;
import com.yipin_server.yihuo.mapper.ProductMapper;
import com.yipin_server.yihuo.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
