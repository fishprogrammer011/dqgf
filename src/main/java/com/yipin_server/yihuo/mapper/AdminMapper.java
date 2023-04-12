package com.yipin_server.yihuo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yipin_server.yihuo.entity.Menu;
import com.yipin_server.yihuo.entity.User;
import com.yipin_server.yihuo.entity.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper extends BaseMapper<User> {

    public UserDTO login(UserDTO userDTO);

    List<Menu> getRoleMenus(String roleFlag);
}
