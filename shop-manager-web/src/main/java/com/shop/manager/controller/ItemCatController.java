package com.shop.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shop.common.pojo.EasyUITreeNode;
import com.shop.manager.utils.FastDFSUtils;
import com.shop.manager.utils.JsonUtils;
import com.shop.service.ItemCatService;

/**
 * @Description 商品类目的Controller
 * @author feng
 *
 */

@Controller
public class ItemCatController {

	@Value("${IMAG_SERVER_URL}")
	private String IMAG_SERVER_URL; 
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 
	 * @Description  获得商品分类
	 * @param parentId
	 * @return
	 * @Author feng
	 * @Date 2017年11月16日 下午7:24:50
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(
			@RequestParam(value="id",defaultValue="0")long parentId){
		
		List<EasyUITreeNode> easyUITreeNodes = itemCatService.getItemCatList(parentId);
		
		return easyUITreeNodes;
	}
	
	/**
	 * 
	 * @Description  上传图片到FastDFS
	 * @param uploadFile
	 * @return
	 * @throws Exception 
	 * @Author feng
	 * @Date 2017年11月16日 下午7:28:40
	 */
	@RequestMapping(value = "/pic/upload" , produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) throws Exception{
		
		Map<String, Object> result = new HashMap<>();
		try {
			// 获得文件的后缀名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.indexOf(".") + 1);
		
			// 创建一个FastDFS的客户端
			FastDFSUtils fastDFSUtils = new FastDFSUtils("classpath:resource/track_client.conf");
			// 拼接完整的图片url
			String path = fastDFSUtils.uploadFile(uploadFile.getBytes(), extName);
			String url = IMAG_SERVER_URL + path;
			
			// 返回
			result.put("error", 0);
			result.put("url", url);
			// 将map装换为json
			String json = JsonUtils.objectToJson(result);
			return json;
			
		} catch (Exception e) {
			result.put("error", 1);
			result.put("message", "图片上传失败！！！");
			String json = JsonUtils.objectToJson(result);
			return json;
		}
	}
	
}
