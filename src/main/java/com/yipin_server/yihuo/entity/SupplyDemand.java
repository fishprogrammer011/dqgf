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
@TableName("supply_demand")
public class SupplyDemand {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String type;

    private String sdType;

    private String content;

    private String addTime;

    private String updateTime;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private String username;

    private String company;

    private String beginTime;

    private String endTime;

    private String address;

    private Integer collectNum;

    private String picture;

    private Integer del;

}
