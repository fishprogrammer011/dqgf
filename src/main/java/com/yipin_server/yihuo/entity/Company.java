package com.yipin_server.yihuo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: wangTao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("company")
@Accessors(chain = true)
public class Company {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    //企业图标
    private String icon;

    //企业背景图
    private String bgicon;

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

    private String regtime;

    private Integer forbidden;

    private BigDecimal assets;

    private Integer collectNum;

}
