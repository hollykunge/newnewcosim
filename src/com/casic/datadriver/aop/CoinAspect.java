package com.casic.datadriver.aop;


import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.coin.CoinService;
import com.casic.datadriver.service.coin.DdScoreInflowService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Aspect
public class CoinAspect {
    private Log logger = LogFactory.getLog(CoinAspect.class);
    @Resource
    private DdScoreInflowService ddScoreInflowService;
    @Resource
    private CoinService coinService;

    private List<Map<String, String>> flagList = new ArrayList<>();
    private boolean flag = false;

    /**
     * design_1,奖励1积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.todotask(..))")
    public void todotaskAspect() {
    }

    /**
     * design_2,奖励2积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.updatePrivateData(..))")
    public void updatePrivateDataAspect() {
    }

    /**
     * design_3,奖励2积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.uploadPrivateFile(..))")
    public void uploadPrivateFileAspect() {
    }

    /**
     * design_4,奖励10积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.submittask(..))")
    public void submittaskAspect() {
    }

    /**
     * design_5,奖励10积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.project.ProjectController.done(..))")
    public void doneAspect() {
    }

    /**
     * design_6,奖励10积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.project.ProjectController.save(..))")
    public void saveAspect() {
    }

    /**
     * design_7,奖励5积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.project.ProjectController.createtopublish(..))||" +
            "execution(public * com.casic.datadriver.controller.project.ProjectController.onepunchsend(..))")
    public void createtopublishAspect() {
    }

    /**
     * design_8,奖励10积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.canOrderToOrder(..))||" +
            "execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.createToPublish(..))")
    public void orderAndCreateAspect() {
    }

    //TODO controller方法移植到SERVICE
    @AfterReturning(returning = "result", pointcut = "todotaskAspect()")
    public void todotaskReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        ddScoreInflow.setSourceScore(1);
        ddScoreInflow.setSourceDetail("design_1");
        setData(ddScoreInflow);
    }

    @AfterReturning(returning = "result", pointcut = "updatePrivateDataAspect()")
    public void updatePrivateDataReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        ddScoreInflow.setSourceScore(2);
        ddScoreInflow.setSourceDetail("design_2");
        setData(ddScoreInflow);
    }

    @AfterReturning(returning = "result", pointcut = "uploadPrivateFileAspect()")
    public void uploadPrivateFileReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        ddScoreInflow.setSourceScore(2);
        ddScoreInflow.setSourceDetail("design_3");
        setData(ddScoreInflow);
    }

    @AfterReturning(returning = "result", pointcut = "submittaskAspect()")
    public void submittaskReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        ddScoreInflow.setSourceScore(10);
        ddScoreInflow.setSourceDetail("design_4");
        setData(ddScoreInflow);
    }

    @AfterReturning(returning = "result", pointcut = "saveAspect()")
    public void saveReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        ddScoreInflow.setSourceScore(10);
        ddScoreInflow.setSourceDetail("design_6");
        setData(ddScoreInflow);
    }

    @AfterReturning(returning = "result", pointcut = "createtopublishAspect()")
    public void createtopublishReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        Map<String, String> map = new HashMap<>();

        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long taskId = RequestUtil.getLong(request, "taskId");
        String account = ContextUtil.getCurrentUser().getAccount();
        if (flagList.size() == 0) {
            map.put(account, taskId.toString());
            flagList.add(map);
            ddScoreInflow.setSourceScore(10);
            ddScoreInflow.setSourceDetail("design_7");
            setData(ddScoreInflow);
        } else {
            for (Map<String, String> flagMap : flagList) {
                for (Map.Entry<String, String> entry : flagMap.entrySet()) {
                    if (entry.getKey().equals(account) && entry.getValue().equals(taskId.toString())) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                map.put(account, taskId.toString());
                flagList.add(map);
                ddScoreInflow.setSourceScore(10);
                ddScoreInflow.setSourceDetail("design_7");
                setData(ddScoreInflow);
            }
        }

        flag = false;
    }

    @AfterReturning(returning = "result", pointcut = "orderAndCreateAspect()")
    public void orderAndCreateReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        Map<String, String> map = new HashMap<>();

        //当前用户在同一个任务中点击订阅，只有第一次计算积分，多次点击只计算一次
        //TODO: 需用更好的方法
        if (joinPoint.getSignature().getName().equals("canOrderToOrder")) {
            HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
            Long taskId = RequestUtil.getLong(request, "taskId");
            String account = ContextUtil.getCurrentUser().getAccount();
            if (flagList.size() == 0) {
                map.put(account, taskId.toString());
                flagList.add(map);
                ddScoreInflow.setSourceScore(10);
                ddScoreInflow.setSourceDetail("design_8");
                setData(ddScoreInflow);
            } else {
                for (Map<String, String> flagMap : flagList) {
                    for (Map.Entry<String, String> entry : flagMap.entrySet()) {
                        if (entry.getKey().equals(account) && entry.getValue().equals(taskId.toString())) {
                            flag = true;
                        }
                    }
                }
                if (!flag) {
                    map.put(account, taskId.toString());
                    flagList.add(map);
                    ddScoreInflow.setSourceScore(10);
                    ddScoreInflow.setSourceDetail("design_8");
                    setData(ddScoreInflow);
                }
            }
        } else {

            ddScoreInflow.setSourceScore(10);
            ddScoreInflow.setSourceDetail("design_8");
            setData(ddScoreInflow);
        }
        flag = false;
    }

    @AfterReturning(returning = "result", pointcut = "doneAspect()")
    public void doneReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        DdScoreInflow ddScoreInflow = new DdScoreInflow();
        ddScoreInflow.setSourceScore(10);
        ddScoreInflow.setSourceDetail("design_5");
        setData(ddScoreInflow);
    }

    public void setData(DdScoreInflow ddScoreInflow) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updTime = simpleDateFormat.format(new Date());
        ddScoreInflow.setSourceType("quanju");
        ddScoreInflow.setUpdTime(updTime);

        //传递身份证号、分数、类型、详情、更新时间
        coinService.addScore(ContextUtil.getCurrentUser().getAccount(),
                String.valueOf(ddScoreInflow.getSourceScore()),
                ddScoreInflow.getSourceType(),
                ddScoreInflow.getSourceDetail(),
                ddScoreInflow.getUpdTime());
    }
}
