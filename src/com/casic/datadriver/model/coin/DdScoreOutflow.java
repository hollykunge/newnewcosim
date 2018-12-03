package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import java.util.Date;

/**
 * @author workhub
 */

public class DdScoreOutflow extends BaseModel {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 积分来源
     */
    private String sourceType;

    /**
     * 消耗积分
     */
    private Integer expendScore;

    /**
     * 消耗原因
     */
    private String expendDetail;

    /**
     * 消耗时间
     */
    private Date udpTime;

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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getExpendScore() {
        return expendScore;
    }

    public void setExpendScore(Integer expendScore) {
        this.expendScore = expendScore;
    }

    public String getExpendDetail() {
        return expendDetail;
    }

    public void setExpendDetail(String expendDetail) {
        this.expendDetail = expendDetail;
    }

    public Date getUdpTime() {
        return udpTime;
    }

    public void setUdpTime(Date udpTime) {
        this.udpTime = udpTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "DdScoreOutflow {" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", expendScore=" + expendScore +
                ", expendDetail='" + expendDetail + '\'' +
                ", udpTime='" + udpTime + '\'' +
                '}';
    }
}