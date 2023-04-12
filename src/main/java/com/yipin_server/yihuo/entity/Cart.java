package com.yipin_server.yihuo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

/**
 * @Author: wangTao
 */
@Data
@Accessors(chain = true)
public class Cart {

    private Integer id;

    private Integer productIds;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private BigDecimal price;

    private String creatTime;
}
