package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Company;
import com.yipin_server.yihuo.mapper.CompanyMapper;
import com.yipin_server.yihuo.service.CompanyService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
}
