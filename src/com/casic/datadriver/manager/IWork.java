package com.casic.datadriver.manager;

import com.casic.datadriver.model.project.Project;

import java.io.IOException;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/10/17
 * @Modified:
 */
public interface IWork {
    /**
     * @param receiverId 接收人id
     * @param senderId   发送人id
     * @param msg        消息内容
     * @param type       消息类型
     * @author 忠
     * @date 2018/10/17 14:06
     * @Description 消息发送接口
     */
    void sendMsg(Long senderId, Long receiverId, String msg, Long type) throws IOException;

    /**
     * @param object 项目属性
     * @author 忠
     * @date 2018/10/17 14:13
     * @Description 增加讨论组属性
     */
    String addGroup(Object object);

    /**
     * @param project 项目属性
     * @param userId  人员ID
     * @author 忠
     * @date 2018/10/17 14:13
     * @Description 增加讨论组人员
     */
    String addGroupUser(Project project, String userId);
}
