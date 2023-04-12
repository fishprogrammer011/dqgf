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
@TableName("address")
public class Address {

    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private String username;

    private String phone;

    private String addressProvince;

    private String addressStreet;

    private Integer isDefault;

    private String createTime;
}
