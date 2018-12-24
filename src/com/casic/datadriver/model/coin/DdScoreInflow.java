package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import java.util.Date;

/**
 * @author workhub
 */

public class DdScoreInflow extends BaseModel {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 获得积分
     */
    private Integer sourceScore;

    /**
     * 积分来源
     */
    private String sourceType;

    /**
     * 获得详情
     */
    private String sourceDetail;

    /**
     * 获得时间
     */
    private Date updTime;

    /**
     * 总和
     */
    private Long total;

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
     * 资源id
     */
    private Long resourceId;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

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

    public Integer getSourceScore() {
        return sourceScore;
    }

    public void setSourceScore(Integer sourceScore) {
        this.sourceScore = sourceScore;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceDetail() {
        return sourceDetail;
    }

    public void setSourceDetail(String sourceDetail) {
        this.sourceDetail = sourceDetail;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "DdScoreInflow{" +
                "id=" + id +
                ", userId=" + userId +
                ", sourceScore=" + sourceScore +
                ", sourceType='" + sourceType + '\'' +
                ", sourceDetail='" + sourceDetail + '\'' +
                ", updTime='" + updTime + '\'' +
                ", total=" + total +
                ", userName='" + userName + '\'' +
                ", orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}