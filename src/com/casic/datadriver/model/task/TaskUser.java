package com.casic.datadriver.model.task;

import com.casic.datadriver.service.task.TaskInfoService;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: hollykunge
 * @Description: 任务执行人视图类
 * @Date: 创建于 2018/10/31
 * @Modified:
 */
public class TaskUser {

    private Long userId;
    private String username;
    private String orgName;
    private Long orgId;

    public TaskUser(Long userId, String username, String orgName, Long orgId) {
        this.userId = userId;
        this.username = username;
        this.orgName = orgName;
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
