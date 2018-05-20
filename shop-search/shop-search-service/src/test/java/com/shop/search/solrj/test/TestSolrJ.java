package com.shop.search.solrj.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class TestSolrJ {

	
	public static void main(String[] args) throws Exception {
		//testAddDocument();
		queryDocument();
	}
	
	
	public static void testAddDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://47.95.1.113:9090/solr");
		
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", 11);
		document.addField("price", 100);
		
		solrServer.add(document);
		
		solrServer.commit();
	}
	
	public static void queryDocument() throws Exception {
		// 第一步：创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://47.95.1.113:9090/solr");
		// 第二步：创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 第三步：向SolrQuery中添加查询条件、过滤条件。。。
		query.setQuery("*:*");
		// 第四步：执行查询。得到一个Response对象。
		QueryResponse response = solrServer.query(query);
		// 第五步：取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
	}

	
}
