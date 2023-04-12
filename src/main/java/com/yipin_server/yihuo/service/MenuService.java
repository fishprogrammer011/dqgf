package com.yipin_server.yihuo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yipin_server.yihuo.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
