package com.yipin_server.yihuo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

/**
 * @Author: wangTao
 */
@Data
@Accessors(chain = true)
@TableName("pro_order")
public class Order {

    @TableId(value = "order_id",type = IdType.AUTO)
    private Integer orderId;

    @TableField("order_total_price")
    private BigDecimal totalPrice;

    private String companyName;

    public String photoS;

    private Integer addressId;

    private String payType;

    private String creatTime;

    private String payTime;

    private Integer status;

    private Integer productId;

    private String remarks;

    private String name;

    private Integer num;

    @TableField("wx_user_id")
    private Integer wxUserId;

    private String content;
}
