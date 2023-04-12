package com.yipin_server.yihuo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yipin_server.yihuo.entity.Files;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: wangTao
 */
@Mapper
@Repository
public interface FileMapper extends BaseMapper<Files> {

}
