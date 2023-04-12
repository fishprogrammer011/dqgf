package com.yipin_server.yihuo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yipin_server.yihuo.entity.Address;
import com.yipin_server.yihuo.entity.SearchHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: wangTao
 */
@Mapper
@Repository
public interface SearchHistoryMapper extends BaseMapper<SearchHistory> {
}
