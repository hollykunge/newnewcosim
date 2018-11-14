package com.casic.datadriver.jms;

/**
 * 需要实现这个接口并在app-jms.xml 配置处理消息的model及实现的类
 * @author workhub
 */
public interface IJmsHandler {
    /**
     * 处理消息
     * @param model 消息类型
     */
    public void handMessage(Object model);
}
