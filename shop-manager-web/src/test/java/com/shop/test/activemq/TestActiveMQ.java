package com.shop.test.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * 测试ActiveMQ的使用
 * @author feng
 *
 */

public class TestActiveMQ {

	
	
	@Test
	public void testQueueProducer() throws Exception{
		
		// 1 创建一个ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.95.1.113:61616");
		
		// 2 创建一个connection对象
		Connection connection = connectionFactory.createConnection();
		
		// 3 启动连接
		connection.start();
		
		// 4 使用connection创建一个session对象  
		// 两个参数  第一个参数是是否开启事务，如果为true表示开启事务，第二个参数就无用
		// 如果第二个参数为false，表示不开启事务，可以设置开启消息发送的模式，手动模式和自动模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 5 使用session对象创建一个Destination对象（Queue队列，topic订阅）
		Queue queue = session.createQueue("test_queue_01");
		
		// 6 使用session创建一个生产者
		MessageProducer messageProducer = session.createProducer(queue);
		
		// 7 使用session创建需要发送的消息
		TextMessage textMessage = session.createTextMessage("this is my first queue massage!");
		
		// 8 调用生产者发送消息
		messageProducer.send(textMessage);
		
		// 9 关闭资源
		messageProducer.close();
		session.close();
		connection.close();
		
	}
	
	@Test
	public void testQueueConsumer() throws Exception{
		// 1 创建一个ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.142.110:61616");
		
		// 2 创建一个connection对象
		Connection connection = connectionFactory.createConnection();
		
		// 3 启动连接
		connection.start();
		
		// 4 使用connection创建一个session对象  
		// 两个参数  第一个参数是是否开启事务，如果为true表示开启事务，第二个参数就无用
		// 如果第二个参数为false，表示不开启事务，可以设置开启消息发送的模式，手动模式和自动模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Queue queue = session.createQueue("test_queue_01");
		
		MessageConsumer consumer = session.createConsumer(queue);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				String text = null;
				
				TextMessage textMessage = (TextMessage) message;
				
				try {
					text = textMessage.getText();
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
				System.out.println(text);
				
			}
		});
		// 等待键盘的输入
		System.in.read();
		// 9 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	
	
	
	
	
	
	
}
