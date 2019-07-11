package com.casic.datadriver.manager;

import com.casic.datadriver.publicClass.DataTimeHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
     * 缓存key头
     */
    public static final String CACHE_SCORE_PREFIX = "DdScore_";
    public static final String CACHE_SCOREINFLOW_PREFIX = "DdScoreInflow_";
    public static final String CACHE_SCOREOUTFLOW_PREFIX = "DdScoreOutflow_";

    /**
     * 四种币
     */
    public static final String QUAN_JU = "quanju";
    public static final String FENG_XIAN = "fengxian";
    public static final String QIU_SHI = "qiushi";
    public static final String CHUANG_XIN = "chuangxin";

    /**
     * 三种币联合类型
     */
    public static final String SUM_QFQ = "sum_of_quanju_fengxian_qiushi";

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

    /**
     * 抽奖最低分，抽奖规则待定
     */
    public static final Integer LOTTERY_BASE = 200;

    /**
     * 抽奖最少人数，抽奖规则待定
     */
    public static final Integer LOTTERY_MIN_POOL = 100;

    /**
     * 积分兑币消耗类型，抽奖规则待定
     */
    public static final String MONTH_RANK = "month_rank";
    public static final String MONTH_LOTTERY = "month_lottery";

    /**
     * 创新兑币积分
     */
    public static final Integer CHUANG_XIN_BASE = 100;

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


    //求实12种
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
    /**
     * 工具收到评星
     */
    private static final String TOOL_5 = "tool_5";
    /**
     * 上传收到评论
     */
    private static final String TOOL_6 = "tool_6";

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

    /**
     * 加分项
     */
    private ArrayList<String> sourceDetailList = new ArrayList<String>();
    /**
     * 封顶项
     */
    private Map<String, Integer> overflowMap = new HashMap();

    public ScoreRegulation() {
        //全局
        //sourceDetailList.add(DESIGN_1);
        sourceDetailList.add(DESIGN_2);
        sourceDetailList.add(DESIGN_3);
        //sourceDetailList.add(DESIGN_4);
        sourceDetailList.add(DESIGN_5);
        //sourceDetailList.add(DESIGN_6);
        //sourceDetailList.add(DESIGN_7);
        sourceDetailList.add(DESIGN_8);
        //奉献
        sourceDetailList.add(ISSUE_1);
        //sourceDetailList.add(ISSUE_2);
        sourceDetailList.add(KNOWLEDGE_1);
        sourceDetailList.add(KNOWLEDGE_2);
        sourceDetailList.add(KNOWLEDGE_3);
        sourceDetailList.add(KNOWLEDGE_4);
        sourceDetailList.add(KNOWLEDGE_5);
        sourceDetailList.add(KNOWLEDGE_6);
        sourceDetailList.add(KNOWLEDGE_7);
        sourceDetailList.add(KNOWLEDGE_8);
        //求实
        //sourceDetailList.add(TALK_1);
        //sourceDetailList.add(TALK_2);
        //sourceDetailList.add(TALK_3);
        sourceDetailList.add(SEARCH_1);
        //sourceDetailList.add(SEARCH_2);
        //sourceDetailList.add(SEARCH_3);
        //sourceDetailList.add(TOOL_1);
        sourceDetailList.add(TOOL_2);
        //sourceDetailList.add(TOOL_3);
        sourceDetailList.add(TOOL_4);
        //sourceDetailList.add(TOOL_5);
        //sourceDetailList.add(TOOL_6);
        //创新
        sourceDetailList.add(STORE_1);
        //sourceDetailList.add(STORE_2);
        //sourceDetailList.add(STORE_3);

        //overflowMap.put(DESIGN_1, 10);
        overflowMap.put(DESIGN_2, 30);

        overflowMap.put(KNOWLEDGE_1, 5);
        overflowMap.put(KNOWLEDGE_2, 5);
        overflowMap.put(KNOWLEDGE_3, 5);
        overflowMap.put(KNOWLEDGE_4, 10);
        overflowMap.put(KNOWLEDGE_6, 5);

        overflowMap.put(TALK_2, 9);
        overflowMap.put(TALK_3, 8);

        overflowMap.put(SEARCH_1, 3);
        //overflowMap.put(SEARCH_2, 5);
        //overflowMap.put(SEARCH_3, 5);

        overflowMap.put(TOOL_2, 3);
        //overflowMap.put(TOOL_3, 5);
        overflowMap.put(TOOL_4, 3);
    }

    /**
     * 判断是否今天
     *
     * @param time time
     */
    public boolean isToday(Date time) {
        if (time == null) {
            return true;
        }
        //String time = "1988-12-10 11:20:45";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeDate;
        timeDate = DataTimeHandler.DATE_FORMATTER2.get().format(time);
        LocalDateTime localTime = LocalDateTime.parse(timeDate, dtf);
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        //如果大于今天的开始日期，小于今天的结束日期
        return localTime.isAfter(startTime) && localTime.isBefore(endTime);
    }

    /**
     * @param scoreInflow  单次赚取积分数
     * @param todayScore   今天已经得到的积分数
     * @param sourceDetail 来源动作
     */
    public Boolean isOverFlow(Integer scoreInflow, Integer todayScore, String sourceDetail) {
        // 判断该项积分是否有封顶
        if (!overflowMap.containsKey(sourceDetail)) {
            return false;
        }
        // 判断该项积分是否已达到封顶
        if (scoreInflow + todayScore > overflowMap.get(sourceDetail)) {
            System.out.println("积分已封顶！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 传参有效性检验，目前只检验二级类型是否在加分列表种
     *
     * @param sourceDetail 二级类型，来源动作
     * @return 是否合理
     */
    public Boolean dataVerify(String sourceDetail) {
        return sourceDetailList.contains(sourceDetail);
    }


    /**
     * 通过二级类型获得一级类型
     *
     * @param sourceDetail 二级类型
     * @return 一级类型
     */
    public String getSourceByDetail(String sourceDetail) {
        switch (sourceDetail) {
            case (DESIGN_1):
                return QUAN_JU;
            case (DESIGN_2):
                return QUAN_JU;
            case (DESIGN_3):
                return QUAN_JU;
            case (DESIGN_4):
                return QUAN_JU;
            case (DESIGN_5):
                return QUAN_JU;
            case (DESIGN_6):
                return QUAN_JU;
            case (DESIGN_7):
                return QUAN_JU;
            case (DESIGN_8):
                return QUAN_JU;

            case (ISSUE_1):
                return FENG_XIAN;
            case (ISSUE_2):
                return FENG_XIAN;
            case (KNOWLEDGE_1):
                return FENG_XIAN;
            case (KNOWLEDGE_2):
                return FENG_XIAN;
            case (KNOWLEDGE_3):
                return FENG_XIAN;
            case (KNOWLEDGE_4):
                return FENG_XIAN;
            case (KNOWLEDGE_5):
                return FENG_XIAN;
            case (KNOWLEDGE_6):
                return FENG_XIAN;
            case (KNOWLEDGE_7):
                return FENG_XIAN;
            case (KNOWLEDGE_8):
                return FENG_XIAN;

            case (TALK_1):
                return QIU_SHI;
            case (TALK_2):
                return QIU_SHI;
            case (TALK_3):
                return QIU_SHI;
            case (SEARCH_1):
                return QIU_SHI;
            case (SEARCH_2):
                return QIU_SHI;
            case (SEARCH_3):
                return QIU_SHI;
            case (TOOL_1):
                return QIU_SHI;
            case (TOOL_2):
                return QIU_SHI;
            case (TOOL_3):
                return QIU_SHI;
            case (TOOL_4):
                return QIU_SHI;
            case (TOOL_5):
                return QIU_SHI;
            case (TOOL_6):
                return QIU_SHI;

            case (STORE_1):
                return CHUANG_XIN;
            case (STORE_2):
                return CHUANG_XIN;
            case (STORE_3):
                return CHUANG_XIN;

            default:
                return null;
        }
    }

    public String getFinishProjectDetail() {
        return DESIGN_5;
    }
}

