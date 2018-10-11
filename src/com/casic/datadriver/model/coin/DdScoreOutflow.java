package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "dd_score_outflow")
public class DdScoreOutflow extends BaseModel {
    @Id
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 积分来源
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 消耗积分
     */
    @Column(name = "expend_score")
    private Integer expendScore;

    /**
     * 消耗原因
     */
    @Column(name = "expend_detail")
    private String expendDetail;

    /**
     * 消耗时间
     */
    @Column(name = "udp_time")
    private String udpTime;

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
     * 获取消耗积分
     *
     * @return expend_score - 消耗积分
     */
    public Integer getExpendScore() {
        return expendScore;
    }

    /**
     * 设置消耗积分
     *
     * @param expendScore 消耗积分
     */
    public void setExpendScore(Integer expendScore) {
        this.expendScore = expendScore;
    }

    /**
     * 获取消耗原因
     *
     * @return expend_detail - 消耗原因
     */
    public String getExpendDetail() {
        return expendDetail;
    }

    /**
     * 设置消耗原因
     *
     * @param expendDetail 消耗原因
     */
    public void setExpendDetail(String expendDetail) {
        this.expendDetail = expendDetail;
    }

    /**
     * 获取消耗时间
     *
     * @return udp_time - 消耗时间
     */
    public String getUdpTime() {
        return udpTime;
    }

    /**
     * 设置消耗时间
     *
     * @param udpTime 消耗时间
     */
    public void setUdpTime(String udpTime) {
        this.udpTime = udpTime;
    }

    @Override
    public String toString() {
        return "DdScoreOutflow{" +
                "id=" + id +
                ", userId=" + userId +
                ", sourceType='" + sourceType + '\'' +
                ", expendScore=" + expendScore +
                ", expendDetail='" + expendDetail + '\'' +
                ", udpTime=" + udpTime +
                '}';
    }
}