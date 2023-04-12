package com.yipin_server.yihuo.entity.dto;

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
public class WxUserDTO implements Serializable {

    private Integer id;

    private String nickname;

    private Integer phoneNumber;

    private String openId;

    private String avatarUrl;

    private Integer roleGrade;

    private Date registerTime;

    private String loginIp;

    private String cRole;

    private Integer companyId;

    private String token;
}
