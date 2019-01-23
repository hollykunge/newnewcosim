package com.casic.datadriver.jms;

import com.hotent.core.util.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费消息队列
 * @author workhub
 */

public class ScoreMessageConsumer {

//    private Map<String, IJmsHandler> handlers = new HashMap<String, IJmsHandler>();
//
//    public void setHandlers(Map<String, IJmsHandler> handlers) {
//        this.handlers = handlers;
//    }
//
//    /**
//     * @param model 模型
//     * @throws Exception e
//     */
//    public void sendMessage(Object model) throws Exception {
//        if(BeanUtils.isNotEmpty(handlers) && BeanUtils.isNotEmpty(model)) {
//            handlers.get(model.getClass().getName()).handMessage(model);
//        } else {
//            throw new Exception("Object:[" + model + "] is not entity object");
//        }
//    }
}