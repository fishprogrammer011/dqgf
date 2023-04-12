package com.yipin_server.yihuo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yipin_server.yihuo.entity.Menu;
import com.yipin_server.yihuo.entity.User;
import com.yipin_server.yihuo.entity.dto.UserDTO;

import java.util.List;

public interface AdminService extends IService<User> {

    UserDTO login(UserDTO userDTO) ;

    List<Menu> getRoleMenus(String roleFlag);
}
