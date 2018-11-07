package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

/**
 * @author workhub
 */

public class DdScore extends BaseModel {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 积分总量
     */
    private Integer scoreTotal;

    /**
     * 创建时间
     */
    private String crtTime;

    /**
     * 更新时间
     */
    private String udpTime;

    /**
     * 积分类型
     */
    private String scoreType;

    /**
     * 积分产生动作
     */
    private String scoreAction;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 组织ID
     */
    private Long orgId;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUdpTime() {
        return udpTime;
    }

    public void setUdpTime(String udpTime) {
        this.udpTime = udpTime;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getScoreAction() {
        return scoreAction;
    }

    public void setScoreAction(String scoreAction) {
        this.scoreAction = scoreAction;
    }

    @Override
    public String toString() {
        return "DdScore {" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", scoreTotal=" + scoreTotal +
                ", crtTime='" + crtTime + '\'' +
                ", udpTime='" + udpTime + '\'' +
                ", scoreType='" + scoreType + '\'' +
                ", scoreAction='" + scoreAction + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId=" + orgId +
                '}';
    }
}