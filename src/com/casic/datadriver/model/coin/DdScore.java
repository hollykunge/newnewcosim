package com.casic.datadriver.model.coin;

import com.hotent.core.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "dd_score")
public class DdScore extends BaseModel {
    @Id
    private Long id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 积分总量
     */
    @Column(name = "score_total")
    private Long scoreTotal;

    /**
     * 创建时间
     */
    @Column(name = "crt_time")
    private Date crtTime;

    /**
     * 更新时间
     */
    @Column(name = "udp_time")
    private Date udpTime;

    /**
     * 积分类型
     */
    @Column(name = "score_type")
    private String scoreType;

    /**
     * 积分产生动作
     */
    @Column(name = "score_action")
    private String scoreAction;

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
     * 获取积分总量
     *
     * @return score_total - 积分总量
     */
    public Long getScoreTotal() {
        return scoreTotal;
    }

    /**
     * 设置积分总量
     *
     * @param scoreTotal 积分总量
     */
    public void setScoreTotal(Long scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    /**
     * 获取创建时间
     *
     * @return crt_time - 创建时间
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * 设置创建时间
     *
     * @param crtTime 创建时间
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * 获取更新时间
     *
     * @return udp_time - 更新时间
     */
    public Date getUdpTime() {
        return udpTime;
    }

    /**
     * 设置更新时间
     *
     * @param udpTime 更新时间
     */
    public void setUdpTime(Date udpTime) {
        this.udpTime = udpTime;
    }

    /**
     * 获取积分类型
     *
     * @return score_type - 积分类型
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * 设置积分类型
     *
     * @param scoreType 积分类型
     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    /**
     * 获取积分产生动作
     *
     * @return score_action - 积分产生动作
     */
    public String getScoreAction() {
        return scoreAction;
    }

    /**
     * 设置积分产生动作
     *
     * @param scoreAction 积分产生动作
     */
    public void setScoreAction(String scoreAction) {
        this.scoreAction = scoreAction;
    }
}