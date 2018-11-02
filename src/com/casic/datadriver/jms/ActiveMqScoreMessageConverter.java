package com.casic.datadriver.jms;

import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;

/**
 * @author workhub
 */

public class ActiveMqScoreMessageConverter implements MessageConverter {

    @Override
    public Message toMessage(Object object, Session session)
            throws JMSException {
        if (object != null && object.getClass() != null) {
            ObjectMessage objMsg = session.createObjectMessage();
            objMsg.setObject((Serializable) object);
            return objMsg;
        } else {
            throw new JMSException("Object:[" + object + "] is not legal message");
        }
    }

    @Override
    public Object fromMessage(Message msg) throws JMSException {
        if (msg instanceof ObjectMessage) {
            ObjectMessage objMsg = (ObjectMessage) msg;
            Object object = objMsg.getObject();
            if (object != null && object.getClass() != null) {
                return object;
            } else {
                throw new JMSException("Object:[" + msg + "] is not legal message");
            }
        } else {
            throw new JMSException("Msg:[" + msg + "] is not ObjectMessage");
        }
    }
}