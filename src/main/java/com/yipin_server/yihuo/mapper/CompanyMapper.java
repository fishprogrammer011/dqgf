package com.yipin_server.yihuo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yipin_server.yihuo.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: wangTao
 */
@Mapper
@Repository
public interface CompanyMapper extends BaseMapper<Company> {
}
