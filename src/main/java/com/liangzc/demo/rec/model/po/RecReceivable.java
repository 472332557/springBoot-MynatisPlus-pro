package com.liangzc.demo.rec.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 应收明细表
 * </p>
 *
 * @author liangzc
 * @since 2025-07-08
 */
@Getter
@Setter
@TableName("rec_receivable")
public class RecReceivable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 应收明细编号
     */
    private String no;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单行ID
     */
    private Long orderLineId;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 抄表读数ID
     */
    private String meterReadingId;

    /**
     * 收费对象ID
     */
    private Long chargeObjectId;

    /**
     * 关联收费对象类型，引用字典表
     */
    private String chargeObjectType;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 客户身份类型，引用字典表
     */
    private String customerType;

    /**
     * 主业主ID
     */
    private Long masterOwnerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 业主名称
     */
    private String ownerName;

    /**
     * 单价（元）/阶梯单价（price : 单价, start : 起始量）
     */
    private String price;

    /**
     * 计量
     */
    private BigDecimal quantity;

    /**
     * 应收金额（元）
     */
    private BigDecimal amount;

    /**
     * 应收月份
     */
    private Date receivableMonth;

    /**
     * 计费月份
     */
    private Date feeMonth;

    /**
     * 月结月份
     */
    private Date closeMonth;

    /**
     * 计费起始时间
     */
    private Date feeStartDate;

    /**
     * 计费结束时间
     */
    private Date feeEndDate;

    /**
     * 生成方式, 0自动, 1手动
     */
    private Integer generateType;

    /**
     * 账单日期
     */
    private Date billDate;

    /**
     * 缴费日期
     */
    private Date payDate;

    /**
     * 所属周期
     */
    private Date periodDate;

    /**
     * 结算周期类型
     */
    private String periodType;

    /**
     * 是否已添加违约金数据: 0-否, 1-是
     */
    private Integer isPenal;

    /**
     * 应收明细来源类型
     */
    private String sourceType;

    /**
     * 应收明细来源标识
     */
    private String sourceNo;

    /**
     * 说明
     */
    private String description;

    /**
     * 数据版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 删除标记: 0-未删除, 非0-已删除
     */
    private Long deleteFlag;

    private Integer dataSource;


}
