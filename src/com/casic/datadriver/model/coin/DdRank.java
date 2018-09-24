package com.casic.datadriver.model.coin;

public class DdRank {
    /**
     * 用户名称
     */
    private String userName;

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
}
