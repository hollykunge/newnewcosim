package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import java.util.Date;

/**
 * @author workhub
 */

public class AddScoreModel extends BaseModel {

    /**
     * 身份证号
     */
    private String account;

    /**
     * 积分数目
     */
    private String sourceScore;

    /**
     * 积分种类
     */
    private String sourceType;

    /**
     * 积分明细
     */
    private String sourceDetail;

    /**
     * 时间戳
     */
    private Date updTime;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSourceScore() {
        return sourceScore;
    }

    public void setSourceScore(String sourceScore) {
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

    @Override
    public String toString() {
        return "AddScoreModel {" +
                "account='" + account + '\'' +
                ", sourceScore=" + sourceScore +
                ", sourceType='" + sourceType + '\'' +
                ", sourceDetail='" + sourceDetail + '\'' +
                ", updTime='" + updTime +
                '}';
    }
}
