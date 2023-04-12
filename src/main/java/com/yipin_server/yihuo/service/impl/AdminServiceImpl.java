package com.yipin_server.yihuo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yipin_server.yihuo.config.Constants;
import com.yipin_server.yihuo.entity.Menu;
import com.yipin_server.yihuo.entity.User;
import com.yipin_server.yihuo.entity.dto.UserDTO;
import com.yipin_server.yihuo.exception.ServiceException;
import com.yipin_server.yihuo.mapper.AdminMapper;
import com.yipin_server.yihuo.mapper.RoleMapper;
import com.yipin_server.yihuo.mapper.RoleMenuMapper;
import com.yipin_server.yihuo.service.AdminService;
import com.yipin_server.yihuo.service.MenuService;
import com.yipin_server.yihuo.util.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, User> implements AdminService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    MenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        //数据库查询是否存在用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one = getOne(queryWrapper);
        if (one != null && one.getStatus() == 1) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            String role = one.getRoleFlag(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            userDTO.setRole(role);
            return userDTO;
        } else if(one == null){
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }else {
            throw new ServiceException(Constants.CODE_600,"账号已禁用");
        }
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
     @Override
     public List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

}
