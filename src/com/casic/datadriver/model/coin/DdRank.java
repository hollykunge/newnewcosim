package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

public class DdRank extends BaseModel {
    /**
     * 用户排名
     */
    private Integer rank;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 积分总量
     */
    private Integer scoreTotal;
    /**
     * 积分类型
     */
    private String scoreType;

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

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
