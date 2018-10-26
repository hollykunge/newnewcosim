package com.hotent.core.aop;

import java.lang.reflect.Method;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysAudit;
import com.hotent.platform.service.system.SysAuditService;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 
 * 使用AOP拦截Controller的方式记录日志。<br>
 * 如果控制器方法需要被拦截，请在方法之前添加注解  {@link Action Action},这样才能记录日志。
 * 
 * @author csx
 * 
 */
 @Aspect
public class LogAspect
{
	private Log logger = LogFactory.getLog(LogAspect.class);
	@Resource
	private SysAuditService sysAuditService;

	@Around("execution(* com.hotent.platform.controller..*.*(..))||execution(* com.casic.datadriver.controller..*.*(..))")
	public Object doAudit(ProceedingJoinPoint point) throws Throwable
	{
		logger.debug("enetr log aspect doAudit method============================");
		String methodName = point.getSignature().getName();
		// 目标方法不为空
		/*if (StringUtils.isEmpty(methodName)
				|| (methodName.startsWith("set") || methodName
						.startsWith("get")))
		{ // set与get方法除外
			return point.proceed();
		}*/
		Class targetClass = point.getTarget().getClass();
		Method[] methods = targetClass.getMethods();
		Method method = null;
		for (int i = 0; i < methods.length; i++)
		{
			if (methods[i].getName() == methodName)
			{
				method = methods[i];
				break;
			}
		}
		if (method == null)
			return point.proceed();

		boolean hasAnnotation = method.isAnnotationPresent(Action.class);
		if (hasAnnotation)
		{
			Action annotation = method.getAnnotation(Action.class);

			String methodDescp = annotation.description();
			logger.debug("Action method:" + method.toString() + " Description:" + methodDescp);
			// 取到当前的操作用户
			ISysUser curUser = ContextUtil.getCurrentUser();
			try
			{
				SysAudit sysAudit = new SysAudit();
				sysAudit.setAuditId(UniqueIdUtil.genId());
				sysAudit.setOpName(methodDescp);
				if (curUser != null)
				{
					sysAudit.setExecutorId(curUser.getUserId());
					sysAudit.setExecutor(curUser.getFullname());
				}
				sysAudit.setExeTime(new Date());
				sysAudit.setExeMethod(targetClass.getName() + "." + method.getName());
				HttpServletRequest request = RequestUtil.getHttpServletRequest();
				if (request != null)
				{
					sysAudit.setFromIp(request.getRemoteAddr());
					sysAudit.setRequestURI(request.getRequestURI());
					sysAudit.setReqParams(RequestUtil.getRequestInfo(request).toString());
				}
				sysAuditService.add(sysAudit);
			} catch (Exception ex)
			{
				logger.error(ex.getMessage());
			}
		}

		return point.proceed();
	}
}
