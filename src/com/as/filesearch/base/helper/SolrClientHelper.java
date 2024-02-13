package com.as.filesearch.base.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.util.NamedList;

import com.as.filesearch.base.entity.SearchArticleCourtEntity;
import com.as.filesearch.base.entity.SearchArticleDateEntity;
import com.as.filesearch.base.entity.SearchArticleEntity;
import com.as.filesearch.base.entity.SearchArticleTypeEntity;
import com.as.filesearch.base.entity.SearchLableEntity;
import com.as.filesearch.base.entity.SearchTagEntity;

public class SolrClientHelper {

	private static String SOLR_URL = "";

	static {
		ContextHelper ch = new ContextHelper();
//		SOLR_URL = ch.getContextValueByKey("solr_url");
		SOLR_URL = ch.getSolrUrl();
	}

	/**
	 * 
	 * @param articleId
	 * @param keywordsMap
	 * @param tagsMap
	 * @return
	 */
	public boolean updateSolrById(String articleId, Map<String, Object> keywordsMap, Map<String, Object> tagsMap) {
		boolean ret = false;
		try {
			HttpSolrClient client = new HttpSolrServer(SOLR_URL);
			client.setMaxRetries(1); // defaults to 0. > 1 not recommended.
			client.setConnectionTimeout(5000); // 5 seconds to establish TCP
			// create the document
			SolrInputDocument sdoc = new SolrInputDocument();
			sdoc.addField("id", articleId);
//			Map<String, Object> keywordsMap = new HashMap<String, Object>();
//			keywordsMap.put("set", "Cyberpunk");
//			keywordsMap.put("set", "Cyberpunk");
			// null means remove the keywords or tags
			if (keywordsMap == null) {
				keywordsMap = new HashMap<String, Object>();
				keywordsMap.put("set", null);
			}
			sdoc.addField("keywords", keywordsMap);  // add the map as the field value
			if (tagsMap == null) {
				tagsMap = new HashMap<String, Object>();
				tagsMap.put("set", null);
			}
			sdoc.addField("tags", tagsMap);  // add the map as the field value

			client.add( sdoc );  // send it to the solr server

			client.commit();
			client.close();
			ret = true;
		} catch (Exception e) {
		}
		return ret;
	}

	public boolean deleteSolrById(String articleId) {
		boolean ret = false;
		try {
			HttpSolrClient server = new HttpSolrServer(SOLR_URL);
			server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
			server.setConnectionTimeout(5000); // 5 seconds to establish TCP
			server.deleteById(articleId);
			server.commit();
			server.close();
			ret = true;
		} catch (Exception e) {
		}
		return ret;
	}

	public Map<String, Object> querySolrById(String searchKeyWords,
			String articleId) throws SolrServerException, IOException {
		SearchArticleEntity searchArticleEntity = new SearchArticleEntity();
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
		server.setAllowCompression(false);

		// 使用SolrQuery传递参数，SolrQuery的封装性更好
		server.setRequestWriter(new BinaryRequestWriter());
		SolrQuery query = new SolrQuery();

		// ID和关键字搜索,
		// 此处仅需要传入唯一的ID即可查询出来文档，传入关键字，主要是为了文档高亮
		String keyWordsAndIdQueryStr = "id:" + articleId;
		if (searchKeyWords != null && !"".equals(searchKeyWords)) {
			keyWordsAndIdQueryStr += " AND ( contentindex:" + searchKeyWords + " OR title:" + searchKeyWords + ")";
		}

		query.setQuery(keyWordsAndIdQueryStr);
		query.setFields("id,title,contenthtml,caseno,court,judgedate,cause,type,step");
		query.setHighlight(true);
		String[] hlList = { "title", "contentplain" };
		query.setParam("hl.fl", hlList);
		query.addHighlightField("title");
		query.addHighlightField("contenthtml");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		// 所显示的内容的字数
		query.setHighlightFragsize(0);
		query.setHighlightSnippets(1); //
		// 根据标签facet
		query.setFacet(true);			// 设置使用facet
		query.setFacetMinCount(1);		// 设置facet最少的统计数量
		query.setFacetLimit(10);		// facet结果的返回行数
		query.addFacetField("keywords", "tags"); // facet的字段
		query.setFacetSort(FacetParams.FACET_SORT_COUNT);

		QueryResponse response = server.query(query);
		Map<String, Map<String, List<String>>> hl = response.getHighlighting();

		// 搜索得到的结果数
		System.out.println("Find:" + response.getResults().getNumFound());
		// 输出结果
		for (SolrDocument doc : response.getResults()) {

			String docId = String.valueOf(doc.getFieldValue("id"));
			Map<String, List<String>> hlItems = hl.get(docId);
			if (hlItems == null) {
				continue;
			}
			// Add to highlight list ==>
			doc.setField("title", hlItems.get("title"));
			// Add to highlight list <==
			System.out.println("----------title------------");
			
			String title = (doc.getFieldValue("title") != null) ? doc.getFieldValue("title").toString() : "";
			searchArticleEntity.setArticleTitle(title);
			//案号
			searchArticleEntity.setArticleCaseNO(String.valueOf(doc.getFieldValue("caseno") == null ? "" : doc.getFieldValue("caseno")));
			//法庭
			searchArticleEntity.setArticleCourt(String.valueOf(doc.getFieldValue("court") == null ? "" : doc.getFieldValue("court")));
			//审判日期
			searchArticleEntity.setArticleJudgeDate(String.valueOf(doc.getFieldValue("judgedate") == null ? "" : doc.getFieldValue("judgedate")));
			//案由
			searchArticleEntity.setArticleReason(String.valueOf(doc.getFieldValue("cause") == null ? "" : doc.getFieldValue("cause")));
			//类型
			searchArticleEntity.setArticleType(String.valueOf(doc.getFieldValue("type") == null ? "" : doc.getFieldValue("type")));	
			//程序
			searchArticleEntity.setArticleStep(String.valueOf(doc.getFieldValue("step") == null ? "" : doc.getFieldValue("step")));
			//Html内容
			Object contentHtml = doc.getFieldValue("contenthtml");
			searchArticleEntity.setArticleContentHtml(String.valueOf((contentHtml != null && !"null".equals(contentHtml.toString())) ? contentHtml : ""));

			List<String> highlightSnippets = hlItems.get("title");
			if (highlightSnippets != null) {
				int i = 0;
				for (String item : highlightSnippets) {
					System.out.print(++i + ":");
					System.out.println(item);
					// title
					System.out.println("title: " + item);
					searchArticleEntity.setArticleTitle(item);
				}
			}

			System.out.println("----------contenthtml------------");
			highlightSnippets = hlItems.get("contenthtml");
			if (highlightSnippets != null) {
				int i = 0;
				for (String item : highlightSnippets) {
					System.out.print(++i + ":");
					System.out.println(item);
					searchArticleEntity.setArticleContentHtml(item);
				}
			}

		}
		/*
		 * Facet section
		 */
		List<FacetField> facetFields = response.getFacetFields();
		/*
		 * Step 4, Facet statistics
		 */
		List<SearchLableEntity> searchArticleLabelList = new ArrayList<SearchLableEntity>();
		List<SearchTagEntity> searchArticleTagList = new ArrayList<SearchTagEntity>();
        for (FacetField ff : facetFields) {
        	List<Count> counts = ff.getValues();
            switch (ff.getName()) {
            case "keywords":
            	for(Count c : counts) {
            		SearchLableEntity entity = new SearchLableEntity();
            		entity.setLabel(c.getName());
            		entity.setLabelNum(c.getCount());
					searchArticleLabelList.add(entity);
            	}
                break;
            case "tags":
            	for(Count c : counts) {
            		SearchTagEntity entity = new SearchTagEntity();
            		entity.setLabel(c.getName());
            		entity.setLabelNum(c.getCount());
					searchArticleTagList.add(entity);
            	}
                break;
            default:
            	break;
            }
        }

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("entity", searchArticleEntity);
		returnMap.put("articleLabelList", searchArticleLabelList);
		returnMap.put("articleTagList", searchArticleTagList);

		server.close();
		return returnMap;
	}

	public Map<String, Object> querySolrList(int startNumber, int pageSize,Map<String, Object> params) 
			throws SolrServerException,	IOException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<SearchArticleEntity> searchResultList = new ArrayList<SearchArticleEntity>();

		/*
		 * Step1, Prepare the parameters
		 */
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
		server.setAllowCompression(false);

		// 使用SolrQuery传递参数，SolrQuery的封装性更好
		server.setRequestWriter(new BinaryRequestWriter());
		SolrQuery query = new SolrQuery();

		String searchKeyWords = (String) params.get("searchKeyWord");
		String articleSearchTypeParam = (String) params.get("articleType");
		String articleCourtParam = (String) params.get("articleCourt");
		String guideCase = (String) params.get("guideCase");
		String judgeYear = (String) params.get("judgeYear");
		String cause = (String) params.get("cause");
		String judge = (String) params.get("judge");
		String litigant = (String) params.get("litigant");
		String executor = (String) params.get("executor");
		String executed = (String) params.get("executed");
		String thirdpart = (String) params.get("thirdpart");
		String lawyer = (String) params.get("lawyer");
		String lawfirm = (String) params.get("lawfirm");
		String legalPerson = (String) params.get("legalPerson");
		String label = (String) params.get("label");
		String tag = (String) params.get("tag");
		// ID和关键字搜索,
		// 此处仅需要传入唯一的ID即可查询出来文档，传入关键字，主要是为了文档高亮
		String solrQueryString = "";
		boolean lastExist = false; //上一个参数值是否存在
		//
		//关键字搜索
		if (searchKeyWords != null) {
			searchKeyWords = searchKeyWords.trim();
		}
		if(null!=searchKeyWords && !"".equals(searchKeyWords)){
//			searchKeyWords = searchKeyWords.replaceAll("\\s", "+");
//			String tmp = "( contentindex:" + searchKeyWords + " OR title:" + searchKeyWords +") ";
			String temp = "( contentindex:";
			String[] arrKey = searchKeyWords.split("\\s");
			for (int index = 0; index < arrKey.length; index ++) {
				temp += arrKey[index];
				if (index == arrKey.length - 1) {
					break;
				}
				if (arrKey[index].trim().isEmpty()) {
					continue;
				}
				temp += " AND contentindex:";
			}
			temp += ") ";
			if(lastExist){ //若上一个存在，则需要在中间添加一个AND
				solrQueryString = solrQueryString + " AND "+ temp;
			} else{
				solrQueryString = temp;
				lastExist = true;
			}
		}
		//
		//当事人
		if(null!=litigant && !"".equals(litigant)){
			if(lastExist){ //若上一个存在，则需要在中间添加一个AND
				solrQueryString = solrQueryString + " AND "+ "( excuted:\"" + litigant + "\" OR excutor:\"" + litigant + "\" OR thirdpart:\"" + litigant +"\") ";
			} else{
				solrQueryString = "( excuted:\"" + litigant + "\" OR excutor:\"" + litigant + "\" OR thirdpart:\"" + litigant +"\") ";
				lastExist = true;
			}
		}
		//
		//典型案例
		Map<String, Object> map = addQueryString("guidecase", guideCase, solrQueryString, lastExist);
		//
		//法院搜索
		map = addQueryString("court", articleCourtParam, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//类型
		map = addQueryString("type", articleSearchTypeParam, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//审判年份
		map = addQueryString("judgeyear", judgeYear, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//案由
		map = addQueryString("cause", cause, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//法官
		map = addQueryString("chiefjudge", judge, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//原告
		map = addQueryString("excutor", executor, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//被告
		map = addQueryString("excuted", executed, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//第三人
		map = addQueryString("thirdpart", thirdpart, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//律师
		map = addQueryString("lawyer", lawyer, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//律所
		map = addQueryString("lawfirm", lawfirm, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//法定代理人
		map = addQueryString("legalperson", legalPerson, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//分组标签
		map = addQueryString("keywords", label, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));
		//
		//环助码
		map = addQueryString("tags", tag, String.valueOf(map.get("sql")), (Boolean) map.get("flag"));

		solrQueryString = String.valueOf(map.get("sql"));
		lastExist = (Boolean) map.get("flag");

		query.setQuery(solrQueryString);
		query.setFields("id,court,type,title,caseno,judgedate,contentplain");
		query.setStart(startNumber);
		query.setRows(pageSize);

		query.setFacet(true);			// 设置使用facet
		query.setFacetMinCount(1);		// 设置facet最少的统计数量
		query.setFacetLimit(10);		// facet结果的返回行数
		query.addFacetField("court", "type", "judgeyear", "keywords", "tags"); // facet的字段
		query.setFacetSort(FacetParams.FACET_SORT_COUNT);

		query.setHighlight(true);
		String[] hlList = { "title", "contentplain" };
		query.setParam("hl.fl", hlList);
		//query.setParam("hl.requireFieldMatch", true);  // This version does not support, because contentindex and contentplain is separated.
		query.addHighlightField("title");
		query.addHighlightField("contentplain");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		// 所显示的内容的字数
		query.setHighlightFragsize(50);
		query.setHighlightSnippets(2); //

		/*
		 * Step 2, execute the query statement
		 */
		QueryResponse response = server.query(query);
		/*
		 * Step 3, get the executed results
		 */
		long totalSize = response.getResults().getNumFound();
		returnMap.put("totalSize", totalSize);
		/*
		 * Highlight section
		 */
		Map<String, Map<String, List<String>>> hl = response.getHighlighting();
		/*
		 * Facet section
		 */
		List<FacetField> facetFields = response.getFacetFields();

		for (SolrDocument doc : response.getResults()) {
			//
			// Entity for the article needs to be filled in
			SearchArticleEntity searchArticleEntity = new SearchArticleEntity();
			// id
			searchArticleEntity.setArticleId(String.valueOf(doc.getFieldValue("id")));
			// caseno
			searchArticleEntity.setArticleCaseNO(String.valueOf(doc.getFieldValue("caseno") == null ? "" : doc.getFieldValue("caseno")));
			// court
			searchArticleEntity.setArticleCourt(String.valueOf(doc.getFieldValue("court") == null ? "" : doc.getFieldValue("court")));
			// type
			searchArticleEntity.setArticleType(String.valueOf(doc.getFieldValue("type") == null ? "" : doc.getFieldValue("type")));
			// title
			String title = (doc.getFieldValue("title") != null) ? doc.getFieldValue("title").toString() : "";
			searchArticleEntity.setArticleTitle(title);
			// judgedate
			searchArticleEntity.setArticleJudgeDate(String.valueOf(doc.getFieldValue("judgedate") == null ? "" : doc.getFieldValue("judgedate")));
			// contentplain
			Object contentPlain = doc.getFieldValue("contentplain");
			String partContent = (contentPlain != null && !"null".equals(contentPlain)) ? contentPlain.toString().substring(0, (contentPlain.toString().length() > 100 ? 100 : contentPlain.toString().length())) : "";
			searchArticleEntity.setArticleContent(partContent);

			String docId = String.valueOf(doc.getFieldValue("id"));
			Map<String, List<String>> hlItems = hl.get(docId);
			if (hlItems == null) {
				continue;
			}
			// Add to highlight list ==>
			doc.setField("title", hlItems.get("title"));
			// Add to highlight list <==

			List<String> highlightSnippets = hlItems.get("title");
			if (highlightSnippets != null) {
				int i = 0;
				int hlSnippetsCount = highlightSnippets.size();
				String titleHighContent = "";
				String splitLetterString = "......";
				for (String item : highlightSnippets) {
					i++;
					// 最后一项，不需要加分隔符
					if (i == hlSnippetsCount) {
						titleHighContent = titleHighContent + item;
					} else {
						titleHighContent = titleHighContent + item + splitLetterString;
					}

				}
				// 当累计的内容不为一次，且长度大于分隔符的长度，则去除最后的分隔符
				if (i > 0 && titleHighContent.length() > searchArticleEntity.getArticleTitle().length()) {
					searchArticleEntity.setArticleTitle(titleHighContent);
				}
			}

			highlightSnippets = hlItems.get("contentplain");
			if (highlightSnippets != null) {
				int i = 0;
				int hlSnippetsCount = highlightSnippets.size();
				String titleHighContent = "";
				String splitLetterString = "......";
				for (String item : highlightSnippets) {
					i++;
					// 最后一项，不需要加分隔符
					if (i == hlSnippetsCount) {
						titleHighContent = titleHighContent + item;
					} else {
						titleHighContent = titleHighContent + item
								+ splitLetterString;
					}
				}
				// 当累计的内容不为一次，且长度大于分隔符的长度，则去除最后的分隔符
				if (i > 0) {
					searchArticleEntity.setArticleContent(titleHighContent);
				}
			}
	        // Add to list
			searchResultList.add(searchArticleEntity);
		}
		/*
		 * Step 4, Facet statistics
		 */
		List<SearchArticleCourtEntity> searchArticleCourtList = new ArrayList<SearchArticleCourtEntity>();
		List<SearchArticleTypeEntity> searchArticleTypeList = new ArrayList<SearchArticleTypeEntity>();
		List<SearchArticleDateEntity> searchArticleDateList = new ArrayList<SearchArticleDateEntity>();
		List<SearchLableEntity> searchArticleLabelList = new ArrayList<SearchLableEntity>();
		List<SearchTagEntity> searchArticleTagList = new ArrayList<SearchTagEntity>();
        for (FacetField ff : facetFields) {
        	List<Count> counts = ff.getValues();
            switch (ff.getName()) {
            case "type":
            	for(Count c : counts) {
					SearchArticleTypeEntity newAritlceTypeItem = new SearchArticleTypeEntity();
					newAritlceTypeItem.setArticleType(c.getName());
					newAritlceTypeItem.setArticleTypeNum(c.getCount());
					searchArticleTypeList.add(newAritlceTypeItem);
            	}
                break;
            case "court":
            	for(Count c : counts) {
					SearchArticleCourtEntity articleCourtTemp = new SearchArticleCourtEntity();
					articleCourtTemp.setCourtName(c.getName());
					articleCourtTemp.setCourtCount(c.getCount());
					searchArticleCourtList.add(articleCourtTemp);
            	}
                break;
            case "judgeyear":
            	for(Count c : counts) {
					SearchArticleDateEntity searchArticleDateTemp = new SearchArticleDateEntity();
					searchArticleDateTemp.setArticleDateStr(c.getName());
					searchArticleDateTemp.setArticleDateCount(c.getCount());
					searchArticleDateList.add(searchArticleDateTemp);
            	}
                break;
            case "keywords":
            	for(Count c : counts) {
            		SearchLableEntity searchArticleDateTemp = new SearchLableEntity();
					searchArticleDateTemp.setLabel(c.getName());
					searchArticleDateTemp.setLabelNum(c.getCount());
					searchArticleLabelList.add(searchArticleDateTemp);
            	}
                break;
            case "tags":
            	for(Count c : counts) {
            		SearchTagEntity entity = new SearchTagEntity();
            		entity.setLabel(c.getName());
            		entity.setLabelNum(c.getCount());
					searchArticleTagList.add(entity);
            	}
                break;
            default:
            	break;
            }
        }
		returnMap.put("articleTypeList", searchArticleTypeList);
		returnMap.put("articleCourtList", searchArticleCourtList);
		returnMap.put("articleDateList", searchArticleDateList);
		returnMap.put("articleLabelList", searchArticleLabelList);
		returnMap.put("articleTagList", searchArticleTagList);
		returnMap.put("dataList", searchResultList);

		server.close();
		return returnMap;
	}

	/**
	 * Add query parameters
	 * @param key
	 * @param value
	 * @param solrQueryString
	 * @param lastExist
	 * @return Map
	 */
	private Map<String, Object> addQueryString(String key, String value, String solrQueryString, boolean lastExist) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null!=value && !"".equals(value)){
			if(lastExist){
				solrQueryString = solrQueryString + " AND " + key + ":\"" + value + "\"";
			}else{
				solrQueryString = " " + key + ":\"" + value + "\"";
				lastExist = true;
			}
		}
		map.put("sql", solrQueryString);
		map.put("flag", lastExist);
		return map;
	}
	/**
	 * Solr Words Changed Handler --
	 *
	 * Dynamically load words into memory in runtime environment.
	 * 
	 * http://localhost:8983/solr/pdfcore/words?action=ADD&words=张三四,李四五
	 * http://localhost:8983/solr/pdfcore/words?action=DEL&words=张三四,李四五
	 * 
	 * @throws IOException
	 *
	 * @since solr 5.1.0
	 */
	public boolean addWords(String action, String keyWords)
			throws SolrServerException, IOException {
		HttpSolrClient server = new HttpSolrServer(SOLR_URL);
		server.setMaxRetries(1); // defaults to 0. > 1 not recommended
		server.setConnectionTimeout(5000); // 5 seconds to establish TCP
		// 正常情况下，以下参数无须设置
		// 使用老版本solrj操作新版本的solr时，因为两个版本的javabin incompatible,所以需要设置Parser
		// server.setParser(new XMLResponseParser());
		server.setSoTimeout(1000); // socket read timeout
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false); // defaults to false
		server.setAllowCompression(false);

		// 使用SolrQuery传递参数，SolrQuery的封装性更好
		server.setRequestWriter(new BinaryRequestWriter());
		SolrQuery query = new SolrQuery();

		/*
		 * http://localhost:8983/solr/pdfcore/words?action=ADD&words=张三四,李四五
		 * http://localhost:8983/solr/pdfcore/words?action=DEL&words=张三四,李四五
		 */
		query.setRequestHandler("/words");
		query.setParam("action", action);
		query.setParam("words", keyWords);

		QueryResponse response = server.query(query);
		NamedList<Object> list = response.getResponse();
		String result = (String) list.get("result");
		// 判断结果
		boolean bRet = false;
		if ("succeeded".equals(result)) {
			bRet = true;
		}

		server.close();
		return bRet;
	}

	public static void main(String[] argv) {
		SolrClientHelper sch = new SolrClientHelper();
		try {
			boolean bret = sch.addWords("ADD", "詹思敏");
			if (bret == false) {
			}
		} catch (SolrServerException sse) {
		} catch (IOException e) {
		}
	}
}
