package com.yipin_server.yihuo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wangTao
 */
@Data
@Accessors(chain = true)
public class Activity implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String addtime;

    private Integer sort;

    private String content;

    private String beginTime;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private String endTime;

    private String username;

    private String photo;

    private String type;

    private String address;

    private Integer del;
}
