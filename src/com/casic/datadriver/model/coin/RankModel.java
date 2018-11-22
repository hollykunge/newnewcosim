package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import java.util.Objects;

/**
 * @author workhub
 */

public class RankModel extends BaseModel {

    /**
     * 用户排名
     */
    private Integer rank;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户ID
     */
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RankModel {" +
                "rank=" + rank + '\'' +
                ", userName=" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", orgName=" + orgName + '\'' +
                ", scoreTotal='" + scoreTotal + '\'' +
                ", scoreType='" + scoreType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankModel rankModel = (RankModel) o;
        return Objects.equals(rank, rankModel.rank) &&
                Objects.equals(userName, rankModel.userName) &&
                Objects.equals(userId, rankModel.userId) &&
                Objects.equals(orgName, rankModel.orgName) &&
                Objects.equals(scoreTotal, rankModel.scoreTotal) &&
                Objects.equals(scoreType, rankModel.scoreType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, userName, userId, orgName, scoreTotal, scoreType);
    }
}