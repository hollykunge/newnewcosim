package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

public class AddScoreModel extends BaseModel {

    private String account;
    private String sourceScore;
    private String sourceType;
    private String sourceDetail;
    private String updTime;

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

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }
}
