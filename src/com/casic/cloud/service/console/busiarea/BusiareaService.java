package com.casic.cloud.service.console.busiarea;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import com.casic.cloud.dao.console.busiarea.BusiareaDao;
import com.casic.cloud.model.console.busiarea.Busiarea;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.system.SysOrgInfoDao;
import com.hotent.platform.model.system.Dictionary;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.system.DictionaryService;
import com.hotent.platform.service.system.SysOrgInfoService;

/**
 * <pre>
 * 对象功能:CLOUD_BUSINESS_AREA Service类
 * 开发公司:中国航天科工集团
 * 开发人员:xingchi
 * 创建时间:2013-04-17 21:23:49
 * </pre>
 */
@Service
public class BusiareaService extends BaseService<Busiarea> {
	@Resource
	private BusiareaDao dao;
	@Resource
	private SysOrgInfoDao sysOrgInfoDao;
	@Resource
	private BusiareaService busiareaService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;

	public BusiareaService() {
	}

	@Override
	protected IEntityDao<Busiarea, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 获取当前用户的商友分页列表
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<Busiarea> getAll(QueryFilter queryFilter) {
		return dao.getAll(queryFilter);
	}

	/**
	 * 获取当前用户的未分组商友分页列表
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<Busiarea> getAllNoGroup(QueryFilter queryFilter) {
		return dao.getAllNoGroup(queryFilter);
	}

	/**
	 * 根据企业ID获取企业好友列表
	 * 
	 * @param main_ent
	 * @return
	 */
	public List<Busiarea> getByMainEntId(Long main_ent) {
		return dao.getByMainEntId(main_ent);
	}

	/**
	 * 根据商友分组ID获取企业好友列表
	 * 
	 * @param groupid
	 * @return
	 */
	public List<Busiarea> getByGroupId(QueryFilter queryFilter) {
		return dao.getByGroupId(queryFilter);
	}

	/**
	 * 判断两个企业是否为商友
	 * 
	 * @param mainEnt
	 * @param corpEnt
	 * @return
	 */
	public boolean isFriend(Long mainEnt, Long corpEnt) {
		List<Busiarea> busiareaFriends = new ArrayList<Busiarea>();
		List<Busiarea> isFriends = new ArrayList<Busiarea>();
		busiareaFriends = this.getByMainEntId(mainEnt);
		for (Busiarea busiareaFriend : busiareaFriends) {
			if (busiareaFriend.getCorpEnt().longValue() == corpEnt.longValue()
					&& busiareaFriend.getState() == 1) {
				isFriends.add(busiareaFriend);
			}
		}
		if (isFriends.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断两个企业是否为待商友状态
	 * 
	 * @param mainEnt
	 * @param corpEnt
	 * @return
	 */
	public boolean waitForAccept(Long mainEnt, Long corpEnt) {
		List<Busiarea> busiareaFriends = new ArrayList<Busiarea>();
		List<Busiarea> isFriends = new ArrayList<Busiarea>();
		busiareaFriends = this.getByMainEntId(mainEnt);
		for (Busiarea busiareaFriend : busiareaFriends) {
			if (busiareaFriend.getCorpEnt().longValue() == corpEnt.longValue()
					&& busiareaFriend.getState() == 0) {
				isFriends.add(busiareaFriend);
			}
		}
		if (isFriends.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成商圈动态图的XML文件
	 * 
	 * @param friends
	 * @param path
	 * @param request
	 * @throws IOException
	 */
	public void generateXML(List<Busiarea> friends, String path,
			HttpServletRequest request) throws IOException {
		XMLWriter writer = null; // 声明写XML的对象
		SAXReader reader = new SAXReader();

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8"); // 设置XML文件的编码格式
		String filePath = path + "\\"
				+ ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()
				+ ".xml";
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}

		Document document = DocumentHelper.createDocument();
		document.addComment("An excerpt of an egocentric social network");
		Element graphml = document.addElement("graphml");
		graphml.addAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
		Element graph = graphml.addElement("graph");
		graph.addAttribute("edgedefault", "undirected");

		Element key1 = graph.addElement("key");
		key1.addAttribute("id", "name");
		key1.addAttribute("for", "node");
		key1.addAttribute("attr.name", "name");
		key1.addAttribute("attr.type", "string");
		Element key2 = graph.addElement("key");
		key2.addAttribute("id", "color");
		key2.addAttribute("for", "node");
		key2.addAttribute("attr.name", "color");
		key2.addAttribute("attr.type", "string");

		int i = 1;
		int j = 0;
		boolean nameIsExist1 = false;
		boolean nameIsExist2 = false;
		List<String> allFriendsName = new ArrayList<String>();
		allFriendsName
				.add(ContextUtil.getCurrentOrgInfoFromSession().getName());
		for (i = 1; i <= friends.size(); i++) {
			String corpName = friends.get(i - 1).getCorpEnterprise().getName();
			for (int l = 0; l < allFriendsName.size(); l++) {
				if (corpName.equals(allFriendsName.get(l))) {
					nameIsExist1 = true;
				}
			}
			if (nameIsExist1 != true) {
				allFriendsName.add(corpName);
			}
			nameIsExist1 = false;
			// 根据商友的名字，获得商友的商友
			// 根据corpName获得sysOrgInfo内容，并提起corpEntId
			List<SysOrgInfo> corpInfos = sysOrgInfoDao.getByName(corpName);
			QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
			queryFilter.getFilters().put("mainEnt",
					corpInfos.get(0).getSysOrgInfoId());
			queryFilter.addFilter("state", 1);
			List<Busiarea> corpFriends = busiareaService.getAll(queryFilter);
			for (j = 0; j < corpFriends.size(); j++) {
				for (int k = 0; k < allFriendsName.size(); k++) {
					if (corpFriends.get(j).getCorpEnterprise().getName()
							.equals(allFriendsName.get(k))) {
						nameIsExist2 = true;
					}
				}
				if (nameIsExist2 != true) {
					allFriendsName.add(corpFriends.get(j).getCorpEnterprise()
							.getName());
				}
				nameIsExist2 = false;
			}
		}

		Map params = new HashMap();
		for (int m = 1; m <= allFriendsName.size(); m++) {
			Element node1 = graph.addElement("node");
			node1.addAttribute("id", Integer.toString(m));
			Element data11 = node1.addElement("data");
			data11.addAttribute("key", "name");
			if (allFriendsName.get(m - 1) == null) {
				data11.setText("");
			} else {
				data11.setText(allFriendsName.get(m - 1));
			}
			Element data12 = node1.addElement("data");
			data12.addAttribute("key", "color");
			if (ContextUtil.getCurrentOrgInfoFromSession().getName()
					.equals(allFriendsName.get(m - 1))) {
				data12.setText("01");
			} else {
				data12.setText("05");
			}
			params.put(allFriendsName.get(m - 1), m);
		}
		for (int k = 0; k < friends.size(); k++) {
			Object value = params.get(friends.get(k).getCorpEnterprise()
					.getName());
			Element edge = graph.addElement("edge");
			edge.addAttribute("source", "1");
			edge.addAttribute("target", value.toString());
			edge.setText("");

			String corpName = friends.get(k).getCorpEnterprise().getName();
			// 根据商友的名字，获得商友的商友
			// 根据corpName获得sysOrgInfo内容，并提起corpEntId
			List<SysOrgInfo> corpInfos = sysOrgInfoDao.getByName(corpName);
			QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
			queryFilter.getFilters().put("mainEnt",
					corpInfos.get(0).getSysOrgInfoId());
			queryFilter.addFilter("state", 1);
			List<Busiarea> corpFriends = busiareaService.getAll(queryFilter);
			for (int l = 0; l < corpFriends.size(); l++) {
				Object valueTagget = params.get(corpFriends.get(l)
						.getCorpEnterprise().getName());
				Element edge1 = graph.addElement("edge");
				edge1.addAttribute("source", value.toString());
				edge1.addAttribute("target", valueTagget.toString());
				edge1.setText("");
			}

		}
		writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document);
		writer.close();
	}

	/**
	 * 生成行业动态图
	 * 
	 * @param industryName
	 * @param fileName
	 * @param path
	 * @param request
	 * @throws Exception
	 * @throws IOException
	 */
	public void generateIndustryXML(String industry, int industryId,
			String path, HttpServletRequest request) throws Exception,
			IOException {
		XMLWriter writer = null; // 声明写XML的对象
		SAXReader reader = new SAXReader();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8"); // 设置XML文件的编码格式
		String filePath = path + "\\data\\" + industryId + ".xml";
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}

		Document document = DocumentHelper.createDocument();
		document.addComment("An excerpt of an egocentric social network");
		Element graphml = document.addElement("graphml");
		graphml.addAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
		Element graph = graphml.addElement("graph");
		graph.addAttribute("edgedefault", "undirected");

		Element key1 = graph.addElement("key");
		key1.addAttribute("id", "name");
		key1.addAttribute("for", "node");
		key1.addAttribute("attr.name", "name");
		key1.addAttribute("attr.type", "string");
		Element key2 = graph.addElement("key");
		key2.addAttribute("id", "gender");
		key2.addAttribute("for", "node");
		key2.addAttribute("attr.name", "gender");
		key2.addAttribute("attr.type", "string");

		QueryFilter queryFilter = new QueryFilter(request, "entsList");
		queryFilter.getFilters().put("industry", industry);
		List<SysOrgInfo> ents = sysOrgInfoService.getAll(queryFilter);

		Map params = new HashMap();
		for (int x = 0; x < ents.size(); x++) {
			params.put(ents.get(x).getName(), x + 1);
		}

		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			String entName = String.valueOf(key);
			Element node = graph.addElement("node");
			node.addAttribute("id", String.valueOf(val));
			Element data = node.addElement("data");
			data.addAttribute("key", "name");
			data.setText(entName);

			for (int i = 0; i < params.size(); i++) {
				if (String.valueOf(val) != (String.valueOf(i + 1))) {
					Element edge = graph.addElement("edge");
					edge.addAttribute("source", String.valueOf(val));
					edge.addAttribute("target", String.valueOf(i + 1));
					edge.setText("");
				}
			}
		}

		//
		// for (int i = 0; i < ents.size(); i++) {
		//
		// QueryFilter queryFilter1 = new QueryFilter(request,
		// "industryFriends");
		// queryFilter1.getFilters().put("mainEnt",
		// ents.get(0).getSysOrgInfoId());
		// queryFilter1.addFilter("state", 1);
		// List<Busiarea> corpFriends1 = busiareaService.getAll(queryFilter1);
		//
		// QueryFilter queryFilter2 = new QueryFilter(request,
		// "industryFriends");
		// queryFilter2.getFilters().put("corpEnt",
		// ents.get(0).getSysOrgInfoId());
		// queryFilter2.addFilter("state", 1);
		// List<Busiarea> corpFriends2 = busiareaService.getAll(queryFilter2);
		// if (params.size() > 0) {
		// for (int l = 0; l < corpFriends1.size(); l++) {
		// if (corpFriends1.get(l).getCorpEnterprise().getName()
		// .equals(ents.get(i).getName())) {
		// Object sourceValue = params.get(ents.get(0).getName());
		// Object targetValue = params.get(corpFriends1.get(l)
		// .getCorpEnterprise().getName());
		// if (sourceValue != null && targetValue != null) {
		// Element edge = graph.addElement("edge");
		// edge.addAttribute("source", sourceValue.toString());
		// edge.addAttribute("target", targetValue.toString());
		// edge.setText("");
		// }
		// }
		// }
		//
		// for (int l = 0; l < corpFriends2.size(); l++) {
		// if (corpFriends2.get(l).getCorpEnterprise().getName()
		// .equals(ents.get(i).getName())) {
		// Object sourceValue = params.get(ents.get(0).getName());
		// Object targetValue = params.get(sysOrgInfoService
		// .getById(corpFriends2.get(l).getMainEnt())
		// .getName());
		// if (sourceValue != null && targetValue != null) {
		// Element edge = graph.addElement("edge");
		// edge.addAttribute("source", sourceValue.toString());
		// edge.addAttribute("target", targetValue.toString());
		// edge.setText("");
		// }
		// }
		// }
		// }
		// }

		writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document);
		writer.close();
	}

}
