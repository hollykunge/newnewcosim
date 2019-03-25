package com.casic.datadriver.aop;


import com.casic.datadriver.model.coin.AddScoreModel;
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


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Aspect
public class CoinAspect {
    private Log logger = LogFactory.getLog(CoinAspect.class);
    @Resource
    private DdScoreInflowService ddScoreInflowService;
    @Resource
    private CoinService coinService;

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
//    @Pointcut("execution(public * com.casic.datadriver.controller.project.ProjectController.save(..))")
//    public void saveAspect() {
//    }

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
        Long taskId = RequestUtil.getLong(request, "id");
        Long resourceId = taskId;
        sourceScore = 2;
        sourceDetail = "design_1";
        setData(sourceScore,sourceDetail,resourceId);
    }

    @AfterReturning(returning = "result", pointcut = "updatePrivateDataAspect()")
    public void updatePrivateDataReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long dataId = RequestUtil.getLong(request, "dataId");
        Long resourceId = dataId;
        sourceScore = 10;
        sourceDetail = "design_2";
        setData(sourceScore,sourceDetail,resourceId);
    }

    @AfterReturning(returning = "result", pointcut = "uploadPrivateFileAspect()")
    public void uploadPrivateFileReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long taskId = RequestUtil.getLong(request, "taskId");
        Long resourceId = taskId;
        sourceScore = 5;
        sourceDetail = "design_3";
        setData(sourceScore,sourceDetail,resourceId);
    }

    @AfterReturning(returning = "result", pointcut = "movetaskAspect()")
    public void movetaskReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        String parent = RequestUtil.getString(request, "parent");
            if(parent.equals("completepanel")){
                Long taskId = RequestUtil.getLong(request, "id");
                Long resourceId = Long.parseLong(taskId.toString());
                sourceScore = 30;
                sourceDetail = "design_4";
                setData(sourceScore,sourceDetail,resourceId);
            }
    }

//    @AfterReturning(returning = "result", pointcut = "saveAspect()")
//    public void saveReturning(JoinPoint joinPoint, Object result) throws Throwable {
//        logger.info(joinPoint.getSignature().getName());
//        sourceScore = 30;
//        sourceDetail = "design_6";
//        setData(sourceScore,sourceDetail,null);
//    }

    @AfterReturning(returning = "result", pointcut = "orderAndCreateAspect()")
    public void orderAndCreateReturning(JoinPoint joinPoint, Object result) throws Throwable {
        logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long taskId = RequestUtil.getLong(request, "taskId");
        Long resourceId = Long.parseLong(taskId.toString());
        sourceScore = 30;
        sourceDetail = "design_8";
        setData(sourceScore,sourceDetail,resourceId);
    }

    @AfterReturning(returning = "result", pointcut = "doneAspect()")
    public void doneReturning(JoinPoint joinPoint, Object result) throws Throwable {
         logger.info(joinPoint.getSignature().getName());
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Long projectId = RequestUtil.getLong(request, "id");
        Long resourceId = projectId;
            logger.info(joinPoint.getSignature().getName());
            sourceScore = 50;
            sourceDetail = "design_5";
            setData(sourceScore,sourceDetail,resourceId);
    }

    public void setData(Integer sourceScore,String sourceDetail,Long resourceId) {

        String sourceType = "quanju";
        Date updTime = new Date();

        //传递身份证号、分数、类型、详情、更新时间
        AddScoreModel addScoreModel = new AddScoreModel();
        addScoreModel.setAccount(ContextUtil.getCurrentUser().getAccount());
        addScoreModel.setSourceScore(String.valueOf(sourceScore));
        addScoreModel.setSourceType(sourceType);
        addScoreModel.setSourceDetail(sourceDetail);
        addScoreModel.setUpdTime(updTime);
        addScoreModel.setResourceId(resourceId);

        coinService.addScore(addScoreModel);
    }
}
