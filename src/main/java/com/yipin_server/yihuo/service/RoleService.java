package com.yipin_server.yihuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yipin_server.yihuo.entity.Role;
import java.util.List;

public interface RoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
