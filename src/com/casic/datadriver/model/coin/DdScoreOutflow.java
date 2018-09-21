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
    private Long uid;

    /**
     * 消耗积分
     */
    @Column(name = "expend_score")
    private Long expendScore;

    /**
     * 消耗原因
     */
    @Column(name = "expend_detail")
    private String expendDetail;

    /**
     * 消耗时间
     */
    @Column(name = "udp_time")
    private Date udpTime;

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
     * @return uid - 用户id
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取消耗积分
     *
     * @return expend_score - 消耗积分
     */
    public Long getExpendScore() {
        return expendScore;
    }

    /**
     * 设置消耗积分
     *
     * @param expendScore 消耗积分
     */
    public void setExpendScore(Long expendScore) {
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
    public Date getUdpTime() {
        return udpTime;
    }

    /**
     * 设置消耗时间
     *
     * @param udpTime 消耗时间
     */
    public void setUdpTime(Date udpTime) {
        this.udpTime = udpTime;
    }
}