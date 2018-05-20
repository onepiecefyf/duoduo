package com.shop.test.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastDfsTest {

	@Test
	public void testFastDFS() throws FileNotFoundException, IOException, MyException{
		// 解析配置文件
		ClientGlobal.init("D:/workspace/taotao-workspace/shop-web/src/main/resources/resource/track_client.conf");
		
		// 创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		// 获得TracerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		
		StorageServer storageServer = null;
		
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		
		
		String[] upload_file = storageClient.upload_file("H:/IMG_1152.jpg", "jpg", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
		
		
	}
	
}
