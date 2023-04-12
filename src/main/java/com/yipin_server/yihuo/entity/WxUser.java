package com.yipin_server.yihuo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "wx_user")
public class WxUser implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String nickname;

    @TableField("phone_number")
    private String phoneNumber;

    private String gender;

    private String openId;

    private String avatarUrl;

    private Integer roleGrade;

    private String registerTime;

    private String loginIp;

    private String cRole;

    private Integer companyId;

    private Integer status;

    private Integer applyId;

}
