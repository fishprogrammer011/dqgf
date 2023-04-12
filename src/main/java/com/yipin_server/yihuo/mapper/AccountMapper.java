package com.yipin_server.yihuo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yipin_server.yihuo.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * @Author: wangTao
 */
@Mapper
@Repository
public interface AccountMapper extends BaseMapper<WxUser> {

}
