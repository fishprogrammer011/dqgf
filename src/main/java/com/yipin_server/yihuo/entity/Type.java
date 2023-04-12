package com.yipin_server.yihuo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: wangTao
 */
@Data
@TableName("type")
public class Type {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    private Integer level1;

    private Integer level2;

    private Integer level3;
}
