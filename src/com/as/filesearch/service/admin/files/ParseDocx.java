package com.as.filesearch.service.admin.files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.as.filesearch.base.entity.Constant;
import com.as.filesearch.base.helper.ContextHelper;
import com.as.filesearch.entity.SysFileMeta;

public class ParseDocx {

	public static String SOLR_URL = "http://localhost:8983/solr/pdfcore";

	static {
		ContextHelper ch = new ContextHelper();
//		SOLR_URL = ch.getContextValueByKey("solr_url");
		SOLR_URL = ch.getSolrUrl();
	}
	/*
	 * Convert comma seperated string to arraylist
	 */
	private ArrayList<String> convert2Collection(String str) {
		if (str != null && !"".equals(str)) {
			String[] valuesArray = str.split(",");
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			return values;
		}
		return null;
	}

	public boolean IndexDocs(List<SysFileMeta> items) throws SolrServerException, IOException {
		delDocs();
		// String words = "I'm gonna like solr.";
		long start = System.currentTimeMillis();
		int count = items.size();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (int i = 0; i < count; i++) {
			SolrInputDocument doc1 = new SolrInputDocument();
			SysFileMeta item = items.get(i);
			doc1.addField("id", item.getId());
			doc1.addField("caseno", item.getCaseNo());
			doc1.addField("cause", item.getCause());
			doc1.addField("chiefjudge", convert2Collection(item.getChiefJudge()));
			doc1.addField("clerk", convert2Collection(item.getClerk()));
			doc1.addField("contentplain", item.getContentPlain());
			doc1.addField("contenthtml", item.getContentHtml());
			doc1.addField("court", item.getCourt());
			doc1.addField("createdate", item.getCreateDate());
			doc1.addField("excutor", convert2Collection(item.getExcutor()));
			doc1.addField("excuted", convert2Collection(item.getExcuted()));
			doc1.addField("guidecase", item.isGuideCase());
			doc1.addField("judge", convert2Collection(item.getJudge()));
			doc1.addField("judgedate", item.getJudgeDate());
			doc1.addField("judgeyear", item.getJudgeYear());
			doc1.addField("modifydate", item.getModifyDate());
			doc1.addField("step", item.getStep());
			doc1.addField("title", item.getTitle());
			doc1.addField("type", item.getType());
			doc1.addField("authorizedagent", convert2Collection(item.getAuthorizedAgent()));
			doc1.addField("legalperson", convert2Collection(item.getLegalPerson()));
			doc1.addField("lawyer", convert2Collection(item.getLawyers()));
			doc1.addField("lawfirm", item.getLawFirm());
			doc1.addField("thirdpart", convert2Collection(item.getThirdPart()));

			doc1.addField("keynumber", item.getKeyNumber());
			doc1.addField("vicetitle", item.getViceTitle());
			doc1.addField("keywords", convert2Collection(item.getLables()));
			doc1.addField("tags", convert2Collection(item.getHzNumbers()));
//			doc1.addField("keyword1", item.getKeyword1());
//			doc1.addField("keyword2", item.getKeyword2());
//			doc1.addField("keyword3", item.getKeyword3());
//			doc1.addField("keyword4", item.getKeyword4());
//			doc1.addField("keyword5", item.getKeyword5());
			doc1.addField("briefintroducton", item.getBriefIntroduction());
			doc1.addField("judgeresult", item.getJudgeResult());
			doc1.addField("rules", item.getRules());
			doc1.addField("indexes", item.getIndexes());
			doc1.addField("rsvsmart", convert2Collection(item.getRsvsmart()));
			doc1.addField("rsvnosmart", convert2Collection(item.getRsvnosmart()));
			doc1.addField("rsv0", convert2Collection(item.getRsv0()));
			doc1.addField("rsv1", convert2Collection(item.getRsv1()));
			doc1.addField("rsv2", convert2Collection(item.getRsv2()));
			doc1.addField("rsv3", convert2Collection(item.getRsv3()));
			doc1.addField("rsv4", convert2Collection(item.getRsv4()));
			doc1.addField("rsv5", convert2Collection(item.getRsv5()));
			doc1.addField("rsv6", convert2Collection(item.getRsv6()));
			doc1.addField("rsv7", convert2Collection(item.getRsv7()));
			doc1.addField("rsv8", convert2Collection(item.getRsv8()));
			doc1.addField("rsv9", convert2Collection(item.getRsv9()));

//			doc1.addField("contentxml", item);
//			doc1.addField("contentjason", item);
			docs.add(doc1);
		}

		SolrClient server = new HttpSolrClient(SOLR_URL);
		// 可以通过三种方式增加docs,其中server.add(docs.iterator())效率最高
		// 增加后通过执行commit函数commit (936ms)
		// server.add(docs);
		// server.commit();

		// 增加doc后立即commit (946ms)
		// UpdateRequest req = new UpdateRequest();
		// req.setAction(ACTION.COMMIT, false, false);
		// req.add(docs);
		// UpdateResponse rsp = req.process(server);

		// the most optimal way of updating all your docs
		// in one http request(432ms)
		server.add(docs.iterator());
		server.commit();

		System.out.println("time elapsed(ms):"
				+ (System.currentTimeMillis() - start));
		return true;
	}

	public static void delDocs() {
		long start = System.currentTimeMillis();
		try {
			SolrClient server = new HttpSolrClient(SOLR_URL);
			List<String> ids = new ArrayList<String>();
			for (int i = 1; i < 302; i++) {
				ids.add("id" + i);
			}
			ids.add("myuuid");
			server.deleteById(ids);
			server.commit();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("time elapsed(ms):"
				+ (System.currentTimeMillis() - start));
	}

	public static void querySolr() throws SolrServerException, IOException {
		HttpSolrClient server = new HttpSolrServer(SOLR_URL);
		server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
		server.setConnectionTimeout(5000); // 5 seconds to establish TCP
		// 正常情况下，以下参数无须设置
		// 使用老版本solrj操作新版本的solr时，因为两个版本的javabin incompatible,所以需要设置Parser
		server.setParser(new XMLResponseParser());
		server.setSoTimeout(1000); // socket read timeout
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false); // defaults to false
		// allowCompression defaults to false.
		// Server side must support gzip or deflate for this to have any effect.
		server.setAllowCompression(false);

		// 使用ModifiableSolrParams传递参数
		// ModifiableSolrParams params = new ModifiableSolrParams();
		// //
		// 192.168.230.128:8983/solr/select?q=video&fl=id,name,price&sort=price
		// asc&start=0&rows=2&wt=json
		// // 设置参数，实现上面URL中的参数配置
		// // 查询关键词
		// params.set("q", "video");
		// // 返回信息
		// params.set("fl", "id,name,price,score");
		// // 排序
		// params.set("sort", "price asc");
		// // 分页,start=0就是从0开始,rows=5当前返回5条记录,第二页就是变化start这个值为5就可以了
		// params.set("start", 2);
		// params.set("rows", 2);
		// // 返回格式
		// params.set("wt", "javabin");
		// QueryResponse response = server.query(params);

		// 使用SolrQuery传递参数，SolrQuery的封装性更好
		server.setRequestWriter(new BinaryRequestWriter());
		SolrQuery query = new SolrQuery();
		query.setQuery("id:myuuid2 AND contentindex:苏国华");
		
		query.setFields("id,title,contentplain,contenthtml");
//		query.setSort("price", ORDER.asc);
		query.setStart(0);
		query.setRows(5);

		query.setHighlight(true);
		query.setParam("hl.fl", "title");
		query.addHighlightField("title");
		query.addHighlightField("contentplain");
		query.addHighlightField("contenthtml");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		query.setHighlightFragsize(0);
		query.setHighlightSnippets(10);
//		query.setHighlightRequireFieldMatch(true);

		QueryResponse response = server.query(query);

		Map<String, Map<String, List<String>>> hl = response.getHighlighting();

		// 搜索得到的结果数
		System.out.println("Find:" + response.getResults().getNumFound());
		// 输出结果
		int iRow = 1;
		SolrDocumentList list = new SolrDocumentList();
		for (SolrDocument doc : response.getResults()) {
			System.out.println("----------" + iRow + "------------");
			String docId = String.valueOf(doc.getFieldValue("id"));
			System.out.println("id: " + doc.getFieldValue("id").toString());
			System.out.println("title: " + doc.getFieldValue("title").toString());
			System.out.println("contentplain: "
					+ doc.getFieldValue("contentplain").toString());
			System.out.println("contenthtml: " + doc.getFieldValue("contenthtml"));
			iRow++;
			
			Map<String, List<String>> hlItems = hl.get(docId);
			if (hlItems == null) {
				continue;
			}
			// Add to highlight list ==>
			doc.setField("title", hlItems.get("title"));
			list.add(doc);
			// Add to highlight list <==
			System.out.println("----------title------------");
			List<String> highlightSnippets = hlItems.get("title");
			if (highlightSnippets != null) {
				int i = 0;
				for (String item : highlightSnippets) {
					System.out.print(++i + ":");
					System.out.println(item);
				}
			}
			System.out.println("----------contentplain------------");
			highlightSnippets = hlItems.get("contentplain");
			if (highlightSnippets != null) {
				int i = 0;
				for (String item : highlightSnippets) {
					System.out.print(++i + ":");
					System.out.println(item);
				}
			}
			System.out.println("----------contenthtml------------");
			highlightSnippets = hlItems.get("contenthtml");
			if (highlightSnippets != null) {
				int i = 0;
				for (String item : highlightSnippets) {
					System.out.print(++i + ":");
					System.out.println(item);
				}
			}
			
		}

	}

	public static void main(String[] args) throws Exception {
//		delDocs();
//		AddDocs();
//		AddOneDoc();
		querySolr();
//		queryHighlight();
//		String str = "\n \n meta:save-date 2012-02-11T15:48:36Z  \n Author anpoon  \n dcterms:created 2012-02-11T15:48:36Z  \n date 2012-02-11T15:48:36Z  \n creator anpoon  \n Creation-Date 2012-02-11T15:48:36Z  \n meta:author anpoon  \n stream_content_type application/pdf  \n created Sat Feb 11 15:48:36 UTC 2012  \n stream_size null  \n dc:format application/pdf; version=1.3  \n xmp:CreatorTool PrimoPDF http://www.primopdf.com/  \n dc:title Microsoft Word - 01_Overview_CHT_Exercise.doc  \n Last-Save-Date 2012-02-11T15:48:36Z  \n meta:creation-date 2012-02-11T15:48:36Z  \n dcterms:modified 2012-02-11T15:48:36Z  \n dc:creator anpoon  \n pdf:PDFVersion 1.3  \n Last-Modified 2012-02-11T15:48:36Z  \n X-Parsed-By org.apache.tika.parser.DefaultParser  \n X-Parsed-By org.apache.tika.parser.pdf.PDFParser  \n modified 2012-02-11T15:48:36Z  \n xmpTPg:NPages 4  \n pdf:encrypted false  \n producer PrimoPDF  \n Content-Type application/pdf  \n Microsoft Word - 01_Overview_CHT_Exercise.doc \n \n  page   \n  Copyright © Oracle, 2012.. Oracle and/or its affiliates. All rights reserved.  \n \n 第 1课练习: Oracle公用事业客户服务与计费的简介  01_OVERVIEW_CHT_EXERCISE.DOC \nmm/dd/yy 02/13/2012 生效 Page 1 of  4 Rev 1 \n \n 第第第第 1课练习课练习课练习课练习: Oracle公用事业客公用事业客公用事业客公用事业客\n户服务与计费的简介户服务与计费的简介户服务与计费的简介户服务与计费的简介 \n概述概述概述概述  \n  \n \n page   \n  Copyright © Oracle, 2012.. Oracle and/or its affiliates. All rights reserved.  \n \n 第 1课练习: Oracle公用事业客户服务与计费的简介  01_OVERVIEW_CHT_EXERCISE.DOC \nmm/dd/yy 02/13/2012 生效 Page 2 of  4 Rev 1 \n \n 第第第第1课课课课练习练习练习练习 \n \n 分派分派分派分派 \n中石油天然气销售系统项目组,  全国昆仑燃气公司的培训代表. \n \n 拥拥拥拥有有有有权权权权 \nThe Job Title [list@YourCompany.com?Subject=第 1课练习: Oracle公用事业客户服务与计费的\n简介: EDAG0001.doc] is responsible for ensuring this document is necessary, reflects actual \npractice, and supports corporate policy.  \n \n 练习练习练习练习概述概述概述概述 \n介绍本章提供一个高层次的概述，并解释 Oracle公用事业客户关怀和计费功能实施者课程的目\n标。它还着眼于系统的主要特点和实施，需要实现应用程序的基本步骤。 \n \n 目的目的目的目的 \n通过本章的结尾，你就能: \n \n • 确定本课程的目标 \n• 确定系统内的功能区 \n• 列出实施该系统所需的步骤 \n• 描述在控制表设置顺序 \n \n 课课课课程目的程目的程目的程目的 \n本课程将引导您通过 Oracle公用事业客户服务与计费以及提供一个概念性的结构，在此基础进一\n步学习，当你回到自己的营商环境的主要特征。作为你的导师会导致通过必要的执行程序，你，\n你会得到大量的手工作的主要功能和系统进程的经验。 \n通过本课程结束时，您将了解如何配置 Oracle公用事业客户服务与计费，支持实施客户服务和计\n费要求。 \n \n 系系系系统统统统范范范范围围围围 \nOracle公用事业客户服务与计费是一个复杂的应收账款系统，支持多样化的服务计费。下图说明\n了在 Oracle公用事业客户服务与计费的主要组成部分。 \n \n \n  \n \n  \n \n  \n \n  \n \n  \n \n  \n \n   \n  \n \n page   \n  Copyright © Oracle, 2012.. Oracle and/or its affiliates. All rights reserved.  \n \n 第 1课练习: Oracle公用事业客户服务与计费的简介  01_OVERVIEW_CHT_EXERCISE.DOC \nmm/dd/yy 02/13/2012 生效 Page 3 of  4 Rev 1 \n \n  \n \n  \n \n  \n \n  \n \n  \n \n  \n \n 你的老师会解释每个细节的上述组件。实施的要求，将取决于你所使用的组件。  \n  \n \n page   \n  Copyright © Oracle, 2012.. Oracle and/or its affiliates. All rights reserved.  \n \n 第 1课练习: Oracle公用事业客户服务与计费的简介  01_OVERVIEW_CHT_EXERCISE.DOC \nmm/dd/yy 02/13/2012 生效 Page 4 of  4 Rev 1 \n \n 实实实实施步施步施步施步骤骤骤骤 \n由于该系统是复杂的和可定制的，也有一些在推出和使用新的系统涉及的步骤。  \n \n  \n \n 決定客户信息 \n系统需求 \n \n \n  \n \n 定制系统以\n配合你的需求 \n \n \n 建立控制表\n \n \n 建立应用软件\n安全性\n \n \n 转換现有 \n系统数据 \n \n \n 使用新系统 \n  \n \n  ";
//		System.out.println(str);
	}
}