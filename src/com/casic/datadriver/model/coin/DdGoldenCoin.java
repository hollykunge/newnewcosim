package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import java.util.Date;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 * @Modified:
 */
public class DdGoldenCoin extends BaseModel {
    private Long id;

    private Long userId;
    private Long coinNum;
    private String coinType;
    private String userName;
    private String orgName;
    private Long orgId;
    private java.util.Date updTime;

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

    public Long getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(Long coinNum) {
        this.coinNum = coinNum;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    @Override
    public String toString() {
        return "DdGoldenCoin{" +
                "id=" + id +
                ", userId=" + userId +
                ", coinNum=" + coinNum +
                ", coinType='" + coinType + '\'' +
                ", userName='" + userName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId=" + orgId +
                ", updTime=" + updTime +
                '}';
    }
}
