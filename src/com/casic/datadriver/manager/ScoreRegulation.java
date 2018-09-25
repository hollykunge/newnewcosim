package com.casic.datadriver.manager;

import com.casic.cloud.model.tool.ToolType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hollykunge
 * @Description: 积分规则
 * @Date: 创建于 2018/9/21
 * @Modified:
 */
@Component
public class ScoreRegulation {

    public static final int SCORE_LIMIT = 10;

    private static final String QUAN_JU = "quanju";
    private static final String FENG_XIAN = "fengxian";
    private static final String QIU_SHI = "qiushi";
    private static final String CHUANG_XIN = "chuangxin";

    private static final String DESIGN_1 = "design_1";
    private static final String DESIGN_2 = "design_2";
    private static final String DESIGN_3 = "design_3";
    private static final String DESIGN_4 = "design_4";
    private static final String DESIGN_5 = "design_5";
    private static final String DESIGN_6 = "design_6";
    private static final String DESIGN_7 = "design_7";
    private static final String DESIGN_8 = "design_8";

    private static final String ISSUE_1 = "issue_1";
    private static final String ISSUE_2 = "issue_2";

    private static final String TALK_1 = "talk_1";
    private static final String TALK_2 = "talk_2";
    private static final String TALK_3 = "talk_3";

    private static final String SEARCH_1 = "search_1";
    private static final String SEARCH_2 = "search_2";
    private static final String SEARCH_3 = "search_3";

    private static final String TOOL_1 = "tool_1";
    private static final String TOOL_2 = "tool_2";
    private static final String TOOL_3 = "tool_3";
    private static final String TOOL_4 = "tool_4";

    private static final String STORE_1 = "store_1";
    private static final String STORE_2 = "store_2";
    private static final String STORE_3 = "store_3";

    ArrayList<String> sourceDetailList = new ArrayList<String>();
    Map<String,Integer> overflowmap =  new HashMap();
    public ScoreRegulation() {
        //协同设计
        sourceDetailList.add(DESIGN_1);
        sourceDetailList.add(DESIGN_2);
        sourceDetailList.add(DESIGN_3);
        //研讨
        sourceDetailList.add(TALK_1);
        sourceDetailList.add(TALK_3);
        //搜索
        sourceDetailList.add(SEARCH_1);
        sourceDetailList.add(SEARCH_2);
        sourceDetailList.add(SEARCH_3);
        //工具
        sourceDetailList.add(TOOL_1);
        sourceDetailList.add(TOOL_2);
        sourceDetailList.add(TOOL_3);
        sourceDetailList.add(TOOL_4);


        overflowmap.put(DESIGN_1, 5);
        overflowmap.put(DESIGN_2, 8);
        overflowmap.put(DESIGN_3, 8);
        overflowmap.put(TALK_1, 3);
        overflowmap.put(TALK_3, 8);
        overflowmap.put(SEARCH_1, 6);
        overflowmap.put(SEARCH_2, 5);
        overflowmap.put(SEARCH_3, 5);
        overflowmap.put(TOOL_1, 10);
        overflowmap.put(TOOL_2, 3);
        overflowmap.put(TOOL_3, 5);
        overflowmap.put(TOOL_4, 10);
    }

    public boolean isToday(String time) {
//        String time = "1988-12-10 11:20:45";
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
            return true;
        }
        //如果大于今天的结束日期
        if (localTime.isAfter(endTime)) {
            System.out.println("时间是未来");
        }
        return false;
    }

    /**
     * @param scoreInflow  单次赚取积分数
     * @param todayScore   今天已经得到的积分数
     * @param sourceDetail 来源动作
     * @return
     */
    public Boolean isOverFlow(Integer scoreInflow, Integer todayScore, String sourceDetail) {
        //判断是否是封顶项
        Boolean isCapping = sourceDetailList.contains(sourceDetail);
        if (isCapping) {
            if (scoreInflow + todayScore > overflowmap.get(sourceDetail)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
