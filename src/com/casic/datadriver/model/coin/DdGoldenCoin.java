package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 * @Modified:
 */
public class DdGoldenCoin extends BaseModel {
    private Long id;

    private Long userId;
    private Long total;
    private String coinType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }
}
