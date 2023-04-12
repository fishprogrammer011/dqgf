package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Type;
import com.yipin_server.yihuo.mapper.TypeMapper;
import com.yipin_server.yihuo.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
}
