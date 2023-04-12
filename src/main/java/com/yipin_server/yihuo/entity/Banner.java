package com.yipin_server.yihuo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: wangTao
 */
@Data
public class Banner {

    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    private String name;

    private String bRight;

    private String startTime;

    private String endTime;

    private String url;

    private String address;

    private Integer status;

    private String type;

}
