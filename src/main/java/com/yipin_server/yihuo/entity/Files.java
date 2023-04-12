package com.yipin_server.yihuo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: wangTao
 */
@Data
public class Files {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private String type;

    private Long size;

    private String url;

    private String md5;

    @TableField("delete_status")
    private Boolean deleteStatus;
}
