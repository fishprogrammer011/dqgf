package com.yipin_server.yihuo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: wangTao
 */
@Data
@Accessors(chain = true)
@TableName("user_collect")
public class UserCollect {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private String type;

    private Integer reflectId;
}
