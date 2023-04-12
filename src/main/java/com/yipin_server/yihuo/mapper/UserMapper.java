package com.yipin_server.yihuo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yipin_server.yihuo.entity.User;
import com.yipin_server.yihuo.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: wangTao
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<WxUser> {
    String login(String code,String encryptedData,String iv);
}
