package com.yipin_server.yihuo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wangTao
 */
@Data
@TableName("act_type")
public class TypeActivity implements Serializable {

    private Integer typeId;

    private Integer activityId;
}
