package com.shop.search.listener.searitemListener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.shop.search.service.SearchItemService;

public class SearchItemListener implements MessageListener{

	@Autowired
	private SearchItemService searchItemService;
	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null;
			
			// 如果是文本消息 接受
			if (message instanceof TextMessage) {
				textMessage = (TextMessage)message;
				// 接受消息
				itemId = Long.parseLong(textMessage.getText());
			}
			
			// 调用接口查询商品信息
			searchItemService.addSearItem(itemId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
