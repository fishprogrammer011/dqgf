package com.yipin_server.yihuo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wangTao
 */
@Data
public class Product {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String types;

    private Integer companyId;

    private String companyName;

    private String name;

    private String intro;

    private BigDecimal price;

    private String photoS;

    private String photoM;

    private String content;

    private String addtime;

    private String updatetime;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private Integer num;

    private Integer buynum;

    private String address;

    private String service;

    private Integer isToken;

    private Integer del;

    private String deltime;

    private String keywords;

    private Integer status;

    private Integer collectNum;
}
