package com.casic.datadriver.model.coin;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dd_gambler")
public class DdGambler {
    @Id
    private Long id;

    @Column(name = "period")
    private Long period;

    @Column(name = "gambler_num")
    private Long gamblerNum;

    @Column(name = "gambler_name")
    private String gamblerName;

    @Column(name = "winner_num")
    private Long winnerNum;

    @Column(name = "winner_name")
    private String winnerName;

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

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Long getGamblerNum() {
        return gamblerNum;
    }

    public void setGamblerNum(Long gamblerNum) {
        this.gamblerNum = gamblerNum;
    }

    public String getGamblerName() {
        return gamblerName;
    }

    public void setGamblerName(String gamblerName) {
        this.gamblerName = gamblerName;
    }

    public Long getWinnerNum() {
        return winnerNum;
    }

    public void setWinnerNum(Long winnerNum) {
        this.winnerNum = winnerNum;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

}