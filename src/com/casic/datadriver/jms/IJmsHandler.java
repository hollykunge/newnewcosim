package com.casic.datadriver.jms;

/**
 * 需要实现这个接口并在app-jms.xml 配置处理消息的model及实现的类
 * @author workhub
 */
public interface IJmsHandler {
    public void handMessage(Object model);
}
