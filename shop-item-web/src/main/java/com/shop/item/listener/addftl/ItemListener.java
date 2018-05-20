package com.shop.item.listener.addftl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.shop.pojo.Item;
import com.shop.pojo.TbItem;
import com.shop.pojo.TbItemDesc;
import com.shop.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 添加商品
 * @author feng
 *
 */

public class ItemListener implements MessageListener {

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfig freemarkerConfig;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message) {
		// 获得商品的id
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)message;
			try {
				String text = textMessage.getText();
				if (StringUtils.isNotBlank(text)) {
					long itemId = Long.valueOf(text);
					// 查询商品的信息
					TbItem tbItem = itemService.getItemById(itemId);
					// 封装商品的信息
					Item item = new Item(tbItem);
					// 查询商品的描述信息
					TbItemDesc itemDesc = itemService.geTbItemDesc(itemId);
					
					// 创建数据集，封装填充页面的数据
					@SuppressWarnings("rawtypes")
					Map data = new HashMap<>();
					data.put("item", item);
					data.put("itenDesc", itemDesc);
					
					// 获得创建模板容器
					Configuration configuration = freemarkerConfig.getConfiguration();
					// 获得模板
					Template template = configuration.getTemplate("item.htm");
					FileWriter writer = new FileWriter(new File(HTML_OUT_PATH + itemId + ".html"));
					
					// 执行数据输出模板装换
					template.process(data, writer);
					// 关闭流
					writer.close();
				}
				
			} catch (JMSException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			
		}
	}

}
