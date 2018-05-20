package com.shop.test.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * 配置文件测试MQ的使用
 * @author feng
 *
 */
public class TestMQ {

	// 解析配置文件
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMQ.xml");
	
	@Test
	public void testQueueProducer(){
		
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		
		Queue queue = (Queue)context.getBean("queueDestination");
		
		jmsTemplate.send(queue,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("this is a xml queue producer !");
				return textMessage;
			}
		});
		
	}
	
}
