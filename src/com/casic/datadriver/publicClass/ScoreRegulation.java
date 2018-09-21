package com.casic.datadriver.publicClass;

import com.casic.cloud.model.tool.ToolType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: hollykunge
 * @Description: 积分规则
 * @Date: 创建于 2018/9/21
 * @Modified:
 */
public class ScoreRegulation {

    public static final int SCORE_LIMIT = 10;

    private static final String QUAN_JU = "quanju";
    private static final String FENG_XIAN = "fengxian";
    private static final String QIU_SHI = "qiushi";
    private static final String CHUANG_XIN = "chuangxin";

    public boolean isToday(String time){
//        String time = "2017-09-27 11:20:45";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        LocalDateTime localTime = LocalDateTime.parse(time, dtf);
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        //如果小于今天的开始日期
        if (localTime.isBefore(startTime)) {
            System.out.println("时间是过去");
        }
        //如果大于今天的开始日期，小于今天的结束日期
        if (localTime.isAfter(startTime) && localTime.isBefore(endTime)) {
            System.out.println("时间是今天");
            return true;
        }
        //如果大于今天的结束日期
        if (localTime.isAfter(endTime)) {
            System.out.println("时间是未来");
        }
        return false;
    }

    /**
     *
     * @param scoreInflow 单次赚取积分数
     * @param todayScore 今天已经得到的积分数
     * @return
     */
    public static boolean isOverFlow(Integer scoreInflow, Integer todayScore){
        if (scoreInflow + todayScore>SCORE_LIMIT){
            return true;
        }
        return false;
    }
}
