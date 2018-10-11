package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DdScoreInflow extends BaseModel {
    private Long id;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
    private String updTime;

    /**
     * 总和
     */
    private Long total;

    /**
     * 用户名
     */
    private String userName;


    /**
     *
     * @return total
     */
    public Long getTotal() {
        return total;
    }



    /**
     *
     * @param total
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return userId - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取获得积分
     *
     * @return source_score - 获得积分
     */
    public Integer getSourceScore() {
        return sourceScore;
    }

    /**
     * 设置获得积分
     *
     * @param sourceScore 获得积分
     */
    public void setSourceScore(Integer sourceScore) {
        this.sourceScore = sourceScore;
    }

    /**
     * 获取积分来源
     *
     * @return source_type - 积分来源
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 设置积分来源
     *
     * @param sourceType 积分来源
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取获得详情
     *
     * @return source_detail - 获得详情
     */
    public String getSourceDetail() {
        return sourceDetail;
    }

    /**
     * 设置获得详情
     *
     * @param sourceDetail 获得详情
     */
    public void setSourceDetail(String sourceDetail) {
        this.sourceDetail = sourceDetail;
    }

    /**
     * 获取获得时间
     *
     * @return upd_time - 获得时间
     */
    public String getUpdTime() {
        return updTime;
    }

    /**
     * 设置获得时间
     *
     * @param updTime 获得时间
     */
    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
                '}';
    }
}