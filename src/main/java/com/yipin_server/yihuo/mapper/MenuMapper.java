package com.yipin_server.yihuo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yipin_server.yihuo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

}
