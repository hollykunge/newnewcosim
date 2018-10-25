package com.casic.datadriver.manager;

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
 */

@Component
public class ScoreRegulation {

    public static final int SCORE_LIMIT = 10;

    /**
     * 四种币
     */
    public static final String QUAN_JU = "quanju";
    public static final String FENG_XIAN = "fengxian";
    public static final String QIU_SHI = "qiushi";
    public static final String CHUANG_XIN = "chuangxin";

    /**
     * 获得奖励的人数
     */
    public static final Integer LIMIT_QUAN_JU = 60;
    public static final Integer LIMIT_FENG_XIAN = 20;
    public static final Integer LIMIT_QIU_SHI = 20;
    //public static final Integer LIMIT_CHUANG_XIN = 0;

    /**
     * 每种积分榜单回复人数
     */
    public static final Integer RANK_NUM = 80;

    //全局8种
    /**
     * 查看其他专业发来的任务数据
     */
    private static final String DESIGN_1 = "design_1";
    /**
     * 更新任务数据
     */
    private static final String DESIGN_2 = "design_2";
    /**
     * 本人负责任务里上传模型或报告
     */
    private static final String DESIGN_3 = "design_3";
    /**
     * 负责人确认任务完成
     */
    private static final String DESIGN_4 = "design_4";
    /**
     * 负责人确认项目完成
     */
    private static final String DESIGN_5 = "design_5";
    /**
     * 创建跨研究室项目
     */
    private static final String DESIGN_6 = "design_6";
    /**
     * 发布任务，目前弃用
     */
    private static final String DESIGN_7 = "design_7";
    /**
     * 制定输入输出
     */
    private static final String DESIGN_8 = "design_8";

    //奉献10种
    /**
     * 意见建议被采纳
     */
    private static final String ISSUE_1 = "issue_1";
    /**
     * 目前弃用
     */
    private static final String ISSUE_2 = "issue_2";
    /**
     * 浏览知识
     */
    private static final String KNOWLEDGE_1 = "knowledge_1";
    /**
     * 下载知识
     */
    private static final String KNOWLEDGE_2 = "knowledge_2";
    /**
     * 评星知识
     */
    private static final String KNOWLEDGE_3 = "knowledge_3";
    /**
     * 点评知识
     */
    private static final String KNOWLEDGE_4 = "knowledge_4";
    /**
     * 上传被审批
     */
    private static final String KNOWLEDGE_5 = "knowledge_5";
    /**
     * 上传被阅览
     */
    private static final String KNOWLEDGE_6 = "knowledge_6";
    /**
     * 上传收评星
     */
    private static final String KNOWLEDGE_7 = "knowledge_7";
    /**
     * 上传收评论
     */
    private static final String KNOWLEDGE_8 = "knowledge_8";


    //求实10种
    /**
     * 登陆并在线，目前弃用
     */
    private static final String TALK_1 = "talk_1";
    /**
     * 创建讨论组
     */
    private static final String TALK_2 = "talk_2";
    /**
     * 在讨论组中分享文件
     */
    private static final String TALK_3 = "talk_3";
    /**
     * 搜索文档
     */
    private static final String SEARCH_1 = "search_1";
    /**
     * 点击热点搜索内容
     */
    private static final String SEARCH_2 = "search_2";
    /**
     * 从搜索结果中跳转至PDM、TDM
     */
    private static final String SEARCH_3 = "search_3";
    /**
     * 设置个人工具，目前弃用
     */
    private static final String TOOL_1 = "tool_1";
    /**
     * 使用个人工具
     */
    private static final String TOOL_2 = "tool_2";
    /**
     * 给工具评星
     */
    private static final String TOOL_3 = "tool_3";
    /**
     * 给工具点评
     */
    private static final String TOOL_4 = "tool_4";

    //创新3种
    /**
     * 商城中上传工具并被审批
     */
    private static final String STORE_1 = "store_1";
    /**
     * 工具收到评星
     */
    private static final String STORE_2 = "store_2";
    /**
     * 上传收到评论
     */
    private static final String STORE_3 = "store_3";

    private ArrayList<String> sourceDetailList = new ArrayList<String>();
    private Map<String,Integer> overflowMap =  new HashMap();
    public ScoreRegulation() {
        //全局
        sourceDetailList.add(DESIGN_1);
        sourceDetailList.add(DESIGN_2);
        sourceDetailList.add(DESIGN_3);
        sourceDetailList.add(DESIGN_4);
        sourceDetailList.add(DESIGN_5);
        sourceDetailList.add(DESIGN_6);
        sourceDetailList.add(DESIGN_7);
        sourceDetailList.add(DESIGN_8);
        //奉献
        sourceDetailList.add(ISSUE_1);
        sourceDetailList.add(ISSUE_2);
        sourceDetailList.add(KNOWLEDGE_1);
        sourceDetailList.add(KNOWLEDGE_2);
        sourceDetailList.add(KNOWLEDGE_3);
        sourceDetailList.add(KNOWLEDGE_4);
        sourceDetailList.add(KNOWLEDGE_5);
        sourceDetailList.add(KNOWLEDGE_6);
        sourceDetailList.add(KNOWLEDGE_7);
        sourceDetailList.add(KNOWLEDGE_8);
        //求实
        sourceDetailList.add(TALK_1);
        sourceDetailList.add(TALK_2);
        sourceDetailList.add(TALK_3);
        sourceDetailList.add(SEARCH_1);
        sourceDetailList.add(SEARCH_2);
        sourceDetailList.add(SEARCH_3);
        sourceDetailList.add(TOOL_1);
        sourceDetailList.add(TOOL_2);
        sourceDetailList.add(TOOL_3);
        sourceDetailList.add(TOOL_4);
        //创新
        sourceDetailList.add(STORE_1);
        sourceDetailList.add(STORE_2);
        sourceDetailList.add(STORE_3);

        //设置各分项上限
        overflowMap.put(DESIGN_1, 10);
        overflowMap.put(DESIGN_2, 30);

        overflowMap.put(KNOWLEDGE_1, 5);
        overflowMap.put(KNOWLEDGE_2, 5);
        overflowMap.put(KNOWLEDGE_3, 5);
        overflowMap.put(KNOWLEDGE_4, 10);
        overflowMap.put(KNOWLEDGE_6, 5);

        overflowMap.put(TALK_2, 9);
        overflowMap.put(TALK_3, 8);

        overflowMap.put(SEARCH_1, 6);
        overflowMap.put(SEARCH_2, 5);
        overflowMap.put(SEARCH_3, 5);

        overflowMap.put(TOOL_2, 3);
        overflowMap.put(TOOL_3, 5);
        overflowMap.put(TOOL_4, 10);
    }

    /**
     * 判断是否今天
     * @param time time
     */
    public boolean isToday(String time) {
        //String time = "1988-12-10 11:20:45";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
     */
    public Boolean isOverFlow(Integer scoreInflow, Integer todayScore, String sourceDetail) {
        //判断是否是封顶项
        Boolean isCapping = sourceDetailList.contains(sourceDetail);
        if (isCapping) {
            return scoreInflow + todayScore > overflowMap.get(sourceDetail);
        } else {
            return false;
        }
    }
}