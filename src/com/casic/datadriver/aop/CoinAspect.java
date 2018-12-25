package com.casic.datadriver.aop;


import com.casic.datadriver.model.coin.AddScoreModel;
import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.service.coin.CoinService;
import com.casic.datadriver.service.score.DdScoreInflowService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.casic.datadriver.service.project.ProjectService.ACCOUNTMGB;

@Aspect
public class CoinAspect {
    private Log logger = LogFactory.getLog(CoinAspect.class);
    @Resource
    private DdScoreInflowService ddScoreInflowService;
    @Resource
    private CoinService coinService;

    private List<Map<String, String>> flagList = new ArrayList<>();
    private boolean flag = false;
    private List<Map<String, String>> flagListTo = new ArrayList<>();
    private boolean flagTo = false;

    private Integer sourceScore;
    private String sourceDetail;

    /**
     * design_1,奖励2积分,10封顶
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.todotask(..))")
    public void todotaskAspect() {
    }

    /**
     * design_2,奖励10积分，30封顶
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.updatePrivateData(..))")
    public void updatePrivateDataAspect() {
    }

    /**
     * design_3,奖励5积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.uploadPrivateFile(..))")
    public void uploadPrivateFileAspect() {
    }

    /**
     * design_4,奖励30积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.project.ProjectController.createtopublish(..))")
    public void movetaskAspect() {
    }

    /**
     * design_5,奖励30积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.project.ProjectController.done(..))")
    public void doneAspect() {
    }

    /**
     * design_6,奖励30积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.project.ProjectController.save(..))")
    public void saveAspect() {
    }

    /**
     * design_8,奖励30积分
     */
    @Pointcut("execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.canOrderToOrder(..))||" +
            "execution(public * com.casic.datadriver.controller.datacenter.PersonalTaskController.createToPublish(..))")
    public void orderAndCreateAspect() {
    }

    /**
     * TODO controller方法移植到SERVICE
     */
    @AfterReturning(returning = "result", pointcut = "todotaskAspect()")
    public void todotaskReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long projectId = RequestUtil.getLong(request, "projectId");
        Long taskId = RequestUtil.getLong(request, "id");
        Long resourceId = Long.parseLong(projectId.toString() + taskId.toString());
        sourceScore = 2;
        sourceDetail = "design_1";
        setData(sourceScore,sourceDetail,null);
    }

    @AfterReturning(returning = "result", pointcut = "updatePrivateDataAspect()")
    public void updatePrivateDataReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long projectId = RequestUtil.getLong(request, "projectId");
        Long taskId = RequestUtil.getLong(request, "taskId");
        Long dataId = RequestUtil.getLong(request, "dataId");
        Long resourceId = Long.parseLong(projectId.toString() + taskId.toString() + dataId.toString());
        sourceScore = 10;
        sourceDetail = "design_2";
        setData(sourceScore,sourceDetail,resourceId);
    }

    @AfterReturning(returning = "result", pointcut = "uploadPrivateFileAspect()")
    public void uploadPrivateFileReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long projectId = RequestUtil.getLong(request, "projectId");
        Long taskId = RequestUtil.getLong(request, "taskId");
        Long dataId = RequestUtil.getLong(request, "id");
        Long resourceId = Long.parseLong(projectId.toString() + taskId.toString() + dataId.toString());
        sourceScore = 5;
        sourceDetail = "design_3";
        setData(sourceScore,sourceDetail,null);
    }

    @AfterReturning(returning = "result", pointcut = "movetaskAspect()")
    public void movetaskReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        String parent = RequestUtil.getString(request, "parent");
            if(parent.equals("completepanel")){
                Long projectId = RequestUtil.getLong(request, "projectId");
                Long taskId = RequestUtil.getLong(request, "id");
                Long resourceId = Long.parseLong(taskId.toString());
                sourceScore = 30;
                sourceDetail = "design_4";
                setData(sourceScore,sourceDetail,resourceId);
            }
    }

    @AfterReturning(returning = "result", pointcut = "saveAspect()")
    public void saveReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        sourceScore = 30;
        sourceDetail = "design_6";
        setData(sourceScore,sourceDetail,null);
    }

    @AfterReturning(returning = "result", pointcut = "orderAndCreateAspect()")
    public void orderAndCreateReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];

        Long taskId = RequestUtil.getLong(request, "taskId");
        Long resourceId = Long.parseLong(taskId.toString());
        sourceScore = 30;
        sourceDetail = "design_8";
        setData(sourceScore,sourceDetail,resourceId);

        //当前用户在同一个任务中点击订阅，只有第一次计算积分，多次点击只计算一次
        //TODO: 需用更好的方法
//        if (joinPoint.getSignature().getName().equals("canOrderToOrder")) {
//            orderAndCreateDesign(request,map,ddScoreInflow,flagList,flag);
//        } else if (joinPoint.getSignature().getName().equals("createToPublish")) {
//            orderAndCreateDesign(request,map,ddScoreInflow,flagListTo,flagTo);
//        }
//        flag = false;
    }

    @AfterReturning(returning = "result", pointcut = "doneAspect()")
    public void doneReturning(JoinPoint joinPoint, Object result) throws Throwable {
         logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long projectId = RequestUtil.getLong(request, "taskId");
        Long resourceId = Long.parseLong(projectId.toString());
            logger.info(joinPoint.getSignature().getName());
            sourceScore = 50;
            sourceDetail = "design_5";
            setData(sourceScore,sourceDetail,resourceId);
    }

    public void orderAndCreateDesign(HttpServletRequest request,Map<String,String> map,DdScoreInflow ddScoreInflow,List<Map<String,String>> list,boolean temp){
        Long taskId = RequestUtil.getLong(request, "taskId");
        String account = ContextUtil.getCurrentUser().getAccount();
        if (list.size() == 0 ) {
            map.put(account, taskId.toString());
            list.add(map);
            ddScoreInflow.setSourceScore(30);
            ddScoreInflow.setSourceDetail("design_8");
            //setData(ddScoreInflow);
        } else {
            for (Map<String, String> flagMap : list) {
                for (Map.Entry<String, String> entry : flagMap.entrySet()) {
                    if (entry.getKey().equals(account) && entry.getValue().equals(taskId.toString())) {
                        temp = true;
                    }
                }
            }
            if (!temp) {
                map.put(account, taskId.toString());
                list.add(map);
                ddScoreInflow.setSourceScore(30);
                ddScoreInflow.setSourceDetail("design_8");
               // setData(ddScoreInflow);
            }
        }

    }

    public void setData(Integer sourceScore,String sourceDetail,Long resourceId) {

        String sourceType = "quanju";
        Date updTime = new Date();

        //传递身份证号、分数、类型、详情、更新时间
        AddScoreModel addScoreModel = new AddScoreModel();
//        if (ddScoreInflow.getUserId()==null){
//            addScoreModel.setAccount(ContextUtil.getCurrentUser().getAccount());
//        }else {
//            addScoreModel.setAccount(ACCOUNTMGB);
//        }
        addScoreModel.setAccount(ContextUtil.getCurrentUser().getAccount());
        addScoreModel.setSourceScore(String.valueOf(sourceScore));
        addScoreModel.setSourceType(sourceType);
        addScoreModel.setSourceDetail(sourceDetail);
        addScoreModel.setUpdTime(updTime);
        addScoreModel.setResourceId(resourceId);

        coinService.addScore(addScoreModel);
    }
}
