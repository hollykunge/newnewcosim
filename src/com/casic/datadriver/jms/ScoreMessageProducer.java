package com.casic.datadriver.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import javax.jms.Queue;

/**
 * 输入消息队列
 * @author workhub
 */

public class ScoreMessageProducer {

    private static final Log logger = LogFactory.getLog(ScoreMessageProducer.class);

    @Resource(name="scoreMessageQueue")
    private Queue scoreMessageQueue;

    @Resource
    private JmsTemplate jmsTemplate;

    public void send(Object model) {
        logger.debug("produce the score message");
        jmsTemplate.convertAndSend(scoreMessageQueue, model);
    }
}