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
@TableName("search_history")
public class SearchHistory {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private String keywords;
}
