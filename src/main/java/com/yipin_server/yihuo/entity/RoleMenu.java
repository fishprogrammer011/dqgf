package com.yipin_server.yihuo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleMenu implements Serializable {


    private Integer roleId;

    private Integer menuId;
}
