package com.yipin_server.yihuo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: wangTao
 */
@Data
@TableName("apply")
@Accessors(chain = true)
public class Apply {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //微信小程序用户open_id
    private String openId;

    private String nickname;

    private String avatarUrl;

    @TableField("wx_user_phone")
    private String wxUserPhone;

    //企业id
    private Integer cid;

    private String crole;

    //企业图标
    private String icon;

    //企业背景图
    private String bgicon;

    private String name;

    private String type;

    private String address;

    private String detailAddress;

    private String contactPerson;

    private String phone;

    private String legalPerson;

    @TableField("legal_person_ID1")
    private String legalPersonID1;

    @TableField("legal_person_ID2")
    private String legalPersonID2;

    private String license;

    private Integer status;

    private String applyTime;
}
