package com.yipin_server.yihuo.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

import javax.annotation.security.DenyAll;

/**
 * @Author: wangTao
 */
@Data
@Accessors(chain = true)
public class Spend {

    private Integer id;

    private Integer CompanyId;

    private Decimal spendNum;

    private String spendDate;

    private String spendType;

    private Decimal surplus;
}
