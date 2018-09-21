package com.casic.cloud.controller.console.busiarea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.controller.console.ConsoleController;
import com.casic.cloud.controller.pub.RoleConst;
import com.casic.cloud.model.cloudUseRes.CloudUseRes;
import com.casic.cloud.model.config.info.Info;
import com.casic.cloud.model.console.busiarea.Busiarea;
import com.casic.cloud.model.console.businessAreaGroup.BusinessAreaGroup;
import com.casic.cloud.model.console.cloudMessage.CloudMessage;
import com.casic.cloud.model.system.news.News;
import com.casic.cloud.pub.util.HtmlUtil;
import com.casic.cloud.service.cloudUseRes.CloudUseResService;
import com.casic.cloud.service.config.info.InfoService;
import com.casic.cloud.service.console.busiarea.BusiareaService;
import com.casic.cloud.service.console.businessAreaGroup.BusinessAreaGroupService;
import com.casic.cloud.service.console.cloudMessage.CloudMessageService;
import com.casic.cloud.service.system.news.NewsService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysRole;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysUserService;

/**
 * <pre>
 * 对象功能:CLOUD_BUSINESS_AREA 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:xingchi
 * 创建时间:2013-04-17 21:23:49
 * </pre>
 */
@Controller
@RequestMapping("/cloud/console/busiarea/")
public class BusiareaController extends BaseController {
	@Resource
	private BusiareaService busiareaService;
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	@Resource
	private CloudMessageService cloudMessageService;
	@Resource
	private ConsoleController consoleController;
	@Resource
	private NewsService newsService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private InfoService infoService;
	@Resource
	private CloudUseResService cloudUseResService;
	@Resource
	private BusinessAreaGroupService businessAreaGroupService;
	@Resource
	private SysRoleService sysRoleService;
	
	/**
	 * 添加或更新CLOUD_BUSINESS_AREA。
	 * 
	 * @param request
	 * @param response
	 * @param busiarea
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description = "添加或更新CLOUD_BUSINESS_AREA")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultMsg = null;
		Busiarea busiarea = getFormObject(request);
		busiarea.setGroupid(RequestUtil.getLong(request, "groupid"));
		try {
			if (busiarea.getId() == null || busiarea.getId() == 0) {
				busiarea.setId(UniqueIdUtil.genId());
				busiareaService.add(busiarea);
				resultMsg = getText("record.added", "CLOUD_BUSINESS_AREA");
			} else {
				busiareaService.update(busiarea);
				resultMsg = getText("record.updated", "CLOUD_BUSINESS_AREA");
			}
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.Success);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
		}
	}
	
	
	
	/**
	 * 添加或更新CLOUD_BUSINESS_AREA。
	 * 
	 * @param request
	 * @param response
	 * @param busiarea
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("choseGroup")
	 
	@ResponseBody
	@Action(description = "添加或更新CLOUD_BUSINESS_AREA")
	public  Map<String, String> choseGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		Busiarea busiarea = busiareaService.getById(id);
		Map<String, String> dataMap = new HashMap<String, String>();
		busiarea.setGroupid(RequestUtil.getLong(request, "groupid"));
		
		try {
			busiareaService.update(busiarea);
			dataMap.put("choseGroup", "true");
		} catch (Exception e) {
			dataMap.put("choseGroup", "false");
			e.printStackTrace();
		}
		return dataMap;
	}

	/**
	 * 取得 Busiarea 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Busiarea getFormObject(HttpServletRequest request)
			throws Exception {

		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher((new String[] { "yyyy-MM-dd" })));

		String json = RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);

		Busiarea busiarea = (Busiarea) JSONObject.toBean(obj, Busiarea.class);

		return busiarea;
	}

	/**
	 * 取得CLOUD_BUSINESS_AREA分页列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看CLOUD_BUSINESS_AREA分页列表")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Busiarea> list = busiareaService.getAll(new QueryFilter(request,
				"busiareaItem"));
		ModelAndView mv = this.getAutoView().addObject("busiareaList", list);

		return mv;
	}

	/**
	 * 删除CLOUD_BUSINESS_AREA
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除CLOUD_BUSINESS_AREA")
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			busiareaService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success,
					"删除CLOUD_BUSINESS_AREA成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败"
					+ ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}

	/**
	 * 编辑CLOUD_BUSINESS_AREA
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description = "编辑CLOUD_BUSINESS_AREA")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long id = RequestUtil.getLong(request, "id");
		String returnUrl = RequestUtil.getPrePage(request);
		Busiarea busiarea = busiareaService.getById(id);

		return getAutoView().addObject("busiarea", busiarea).addObject(
				"returnUrl", returnUrl);
	}

	/**
	 * 取得CLOUD_BUSINESS_AREA明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看CLOUD_BUSINESS_AREA明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "id");
		Busiarea busiarea = busiareaService.getById(id);
		return getAutoView().addObject("busiarea", busiarea);
	}

	/**
	 * 商圈管理，能够列出商圈好友，并可以进行删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("busiarea")
	@Action(description = "商友列表")
	public ModelAndView busiarea(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 新闻
		List<News> newsList = newsService.getLastNews();
		// 判断当前用户角色
		Collection<GrantedAuthority> authorities = ContextUtil.getCurrentUser()
				.getAuthorities();
		String type = "";
		if (authorities.contains(new GrantedAuthorityImpl(
				"cloud_ROLE_PUR_MANAGER"))) {
			// 采购主管
			type = "采购商机";
		} else if (authorities.contains(new GrantedAuthorityImpl(
				"cloud_ROLE_SALE_MANAGER"))) {
			// 营销主管
			type = "营销商机";
		} else if (authorities.contains(new GrantedAuthorityImpl(
				"cloud_ROLE_MANUF_MANAGER"))) {
			// 生产主管
			type = "生产商机";
		} else if (authorities.contains(new GrantedAuthorityImpl(
				"cloud_ROLE_AFTERSALE"))) {
			// 服务主管
			type = "服务商机";
		} else if (authorities.contains(new GrantedAuthorityImpl(
				"cloud_ROLE_RESEARCH"))) {
			// 研发主管
			type = "研发商机";
		} else {
			type = "其它";

		}
		 Map<String,Object> m=new HashMap<String,Object>();
		 //商圈分组
		    m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);

		QueryFilter queryFilter = new QueryFilter(request, "busiareaList");
		queryFilter.getFilters().put("mainEnt", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		queryFilter.addFilter("state", 1);
		List<Busiarea> friends = busiareaService.getAll(queryFilter);
		
		//生成好友列表的xml文件，供GraphVis读取
		String path = request.getRealPath("/applet");
		busiareaService.generateXML(friends,path,request);
		
		//获得最近使用资源
		String resName = RequestUtil.getString(request, "name");
		List<CloudUseRes> cloudUseResList = cloudUseResService.getUseRes(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId(),resName);
		//获取我的企业
		SysOrgInfo myCompany = sysOrgInfoService.getById(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		Info info = infoService.getById(myCompany.getSysOrgInfoId()); 
		ModelAndView mav = this.getAutoView().addObject("friends", friends)
				.addObject("myCompany", myCompany)
				.addObject("info", info)
				.addObject("businessAreaGroupList",businessAreaGroupList)
				.addObject("cloudUseResList", cloudUseResList)
				.addObject("newsList", newsList).addObject("type", type);
		
		consoleController.getResources(mav, request);

		return mav;
	}

	@RequestMapping("beFriend")
	@ResponseBody
	public Map<String, String> beFriend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		Busiarea model = new Busiarea();
		Long mainEntId = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		if(mainEntId==null){
			dataMap.put("notLogin", "true");
			return dataMap;
		}
		// 从前台获得对方企业ID
		Long corpEntId = RequestUtil.getLong(request, "corpEntID");
		String roleId = ContextUtil.getCurrentUserId().toString();
		if (mainEntId.longValue() == corpEntId.longValue()) {
			// 不能添加自己为商友 Json输出“不能添加自己为商友”
			dataMap.put("isMyself", "true");
			return dataMap;
		}
		if (busiareaService.isFriend(mainEntId, corpEntId)) {
			// 如果已经提交，并且对方已经是商友，Json输出“对方已是商友，请勿重复添加”
			dataMap.put("isFriend", "true");
			return dataMap;
		} else if (busiareaService.waitForAccept(mainEntId, corpEntId)) {
			// 如果已经提交，但还未被同意为商友，Json输出“已经发出添加商友申请，请勿重复提交添加申请”
			dataMap.put("isWaiting", "true");
			return dataMap;
		} else {
			// 向busiarea数据表中写入数据
			model.setMainEnt(mainEntId);
			model.setCorpEnt(corpEntId);
			model.setRoleId(roleId);
			model.setState(0);
			model.setId(UniqueIdUtil.genId());
			busiareaService.add(model);

			// 发送消息，设置己方也为接收方，则自己也可以看到自己发的消息
			List<Long> sendTos = new ArrayList<Long>();
			sendTos.add(mainEntId);
			sendTos.add(corpEntId);
			// 发消息

			for (Long sendTo : sendTos) {
				CloudMessage cloudMessage = new CloudMessage();

				cloudMessage
						.setContent("<div class=\"content_02\"><font color='#400080'><strong>"
								+ ContextUtil.getCurrentOrgInfoFromSession()
										.getName()
								+ "</strong></font>"
								+ "想加"
								+ "<font color='#400080'><strong>"
								+ sysOrgInfoService.getById(corpEntId)
										.getName()
								+ "</strong></font>"
								+ "为商友</div>"
								+ "<a class=\"link02\" href='javascript:accept(\""
								+ ContextUtil.getCurrentUserId()
								+ "\",\""
								+ model.getId()
								+ "\",\""
								+ ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()
								+ "\")'>同意添加</a>"
								+ "&nbsp;<a class=\"link02\" href='javascript:refuse(\""
								+ ContextUtil.getCurrentUserId()
								+ "\",\""
								+ model.getId()
								+ "\",\""
								+ ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()
								+ "\")'>拒绝添加</a>"
								+ "&nbsp;<a class=\"link02\" href='javascript:acceptAndAdd(\""
								+ ContextUtil.getCurrentUserId()
								+ "\",\""
								+ model.getId()
								+ "\",\""
								+ ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()
								+ "\")'>同意并加为商友</a>");
				cloudMessage.setSendentId(mainEntId);
				cloudMessage.setSenderId(ContextUtil.getCurrentUserId()
						.toString());
				cloudMessage.setReceiveentId(sendTo);
				// cloudMessage.setReceiverId(receiverId);
				cloudMessage.setSendtime(new Date());
				// cloudMessage.setOuttime(outtime);
				cloudMessage.setResult(0);
				// cloudMessage.setLink(link);
				cloudMessage.setType(0);
				cloudMessage.setTitle("添加商友申请");
				cloudMessageService.add(cloudMessage);
			}

			// 添加商友并发送消息，Json输出“添加商友请求已经发送，等待对方审核”
			dataMap.put("waitForAccept", "true");
			return dataMap;
		}
	}

	/**
	 * 接受商友添加好友的请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("accept")
	@ResponseBody
	public Map<String, String> accept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();

		Long busiareaId = RequestUtil.getLong(request, "bid");
		Long recevieEntId = RequestUtil.getLong(request, "sendEntId");
		Busiarea busiarea = busiareaService.getById(busiareaId);
		Long mainEntId = busiarea.getMainEnt();
		Long corpEntId = busiarea.getCorpEnt();
		if (busiarea.getState() == 1) {
			dataMap.put("isFriend", "true");
			return dataMap;
		} else if (recevieEntId.longValue() == ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()
				.longValue()) { // 自己不同通过添加申请
			dataMap.put("myself", "true");
			return dataMap;
		} else if (busiarea.getState() == 2) {
			dataMap.put("alreadyRefuse", "true");
			return dataMap;
		}

		busiarea.setState(1);
		busiareaService.update(busiarea);

		// 发送消息，设置己方也为接收方，则自己也可以看到自己发的消息
		List<Long> sendTos = new ArrayList<Long>();
		sendTos.add(mainEntId);
		sendTos.add(corpEntId);

		for (Long sendTo : sendTos) {
			// 发消息
			CloudMessage cloudMessage = new CloudMessage();

			cloudMessage
					.setContent("<div class='content_02'><font color='#400080'><strong>"
							+ sysOrgInfoService.getById(corpEntId).getName()
							+ "</strong></font>"
							+ "同意添加"
							+ "<font color='#400080'><strong>"
							+ sysOrgInfoService.getById(mainEntId).getName()
							+ "</strong></font>" + "为商友</div>");
			cloudMessage.setReceiveentId(sendTo);
			cloudMessage.setResult(0);
			cloudMessage.setSenderId(ContextUtil.getCurrentUserId().toString());
			// cloudMessage.setReceiverId(receiverId);
			cloudMessage.setSendentId(corpEntId);
			cloudMessage.setSendtime(new Date());
			cloudMessage.setTitle("添加好友成功");
			cloudMessage.setType(0);
			cloudMessageService.add(cloudMessage);
		}
		dataMap.put("acceptOK", "true");
		return dataMap;
	}

	/**
	 * 拒绝好友申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("refuse")
	@ResponseBody
	public Map<String, String> refuse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();

		Long busiareaId = RequestUtil.getLong(request, "bid");
		Long recevieEntId = RequestUtil.getLong(request, "sendEntId");
		Busiarea busiarea = busiareaService.getById(busiareaId);
		Long mainEntId = busiarea.getMainEnt();
		Long corpEntId = busiarea.getCorpEnt();
		if (busiarea.getState() == 1) {
			dataMap.put("isFriend", "true");
			return dataMap;
		} else if (recevieEntId.longValue() == ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()
				.longValue()) { // 自己不同通过添加申请
			dataMap.put("myself", "true");
			return dataMap;
		} else if (busiarea.getState() == 2) {
			dataMap.put("alreadyRefuse", "true");
			return dataMap;
		}

		busiarea.setState(2);
		busiareaService.update(busiarea);

		// 发送消息，设置己方也为接收方，则自己也可以看到自己发的消息
		List<Long> sendTos = new ArrayList<Long>();
		sendTos.add(mainEntId);
		sendTos.add(corpEntId);

		for (Long sendTo : sendTos) {
			// 发消息
			CloudMessage cloudMessage = new CloudMessage();

			cloudMessage
					.setContent("<div class='content_02'><font color='#400080'><strong>"
							+ sysOrgInfoService.getById(corpEntId).getName()
							+ "</strong></font>"
							+ "拒绝添加"
							+ "<font color='#400080'><strong>"
							+ sysOrgInfoService.getById(mainEntId).getName()
							+ "</strong></font>" + "为商友</div>");
			cloudMessage.setReceiveentId(sendTo);
			cloudMessage.setResult(0);
			cloudMessage.setSenderId(ContextUtil.getCurrentUserId().toString());
			// cloudMessage.setReceiverId(receiverId);
			cloudMessage.setSendentId(corpEntId);
			cloudMessage.setSendtime(new Date());
			cloudMessage.setTitle("拒绝添加商友");
			cloudMessage.setType(0);
			cloudMessageService.add(cloudMessage);
		}
		dataMap.put("refuseOK", "true");
		return dataMap;
	}

	/**
	 * 接受添加请求，并加对方为好友
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("acceptAndAdd")
	@ResponseBody
	public Map<String, String> acceptAndAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();

		Long busiareaId = RequestUtil.getLong(request, "bid");
		Long recevieEntId = RequestUtil.getLong(request, "sendEntId");
		Busiarea busiarea = busiareaService.getById(busiareaId);
		Busiarea model = new Busiarea();
		String roleId = ContextUtil.getCurrentUserId().toString();
		Long mainEntId = busiarea.getMainEnt();
		Long corpEntId = busiarea.getCorpEnt();
		if (busiarea.getState() == 1) {
			dataMap.put("isFriend", "true");
			return dataMap;
		} else if (recevieEntId.longValue() == ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId()
				.longValue()) { // 自己不同通过添加申请
			dataMap.put("myself", "true");
			return dataMap;
		} else if (busiarea.getState() == 2) {
			dataMap.put("alreadyRefuse", "true");
			return dataMap;
		}

		busiarea.setState(1);
		busiareaService.update(busiarea);

		if(busiareaService.isFriend(corpEntId, mainEntId)==false){
			model.setMainEnt(corpEntId);
			model.setCorpEnt(mainEntId);
			model.setRoleId(roleId);
			model.setState(1);
			model.setId(UniqueIdUtil.genId());
			busiareaService.add(model);
		}
		// 发送消息，设置己方也为接收方，则自己也可以看到自己发的消息
		List<Long> sendTos = new ArrayList<Long>();
		sendTos.add(mainEntId);
		sendTos.add(corpEntId);

		for (Long sendTo : sendTos) {
			// 发消息
			CloudMessage cloudMessage = new CloudMessage();

			cloudMessage
					.setContent("<div class='content_02'><font color='#400080'><strong>"
							+ sysOrgInfoService.getById(corpEntId).getName()
							+ "</strong></font>"
							+ "同意加"
							+ "<font color='#400080'><strong>"
							+ sysOrgInfoService.getById(mainEntId).getName()
							+ "</strong></font>"
							+ "为商友，并加"
							+ "<font color='#400080'><strong>"
							+ sysOrgInfoService.getById(mainEntId).getName()
							+ "</strong></font>" + "为商友</div>");
			cloudMessage.setReceiveentId(sendTo);
			cloudMessage.setResult(0);
			cloudMessage.setSenderId(ContextUtil.getCurrentUserId().toString());
			// cloudMessage.setReceiverId(receiverId);
			cloudMessage.setSendentId(corpEntId);
			cloudMessage.setSendtime(new Date());
			cloudMessage.setTitle("同意申请并添加对方为好友");
			cloudMessage.setType(0);
			cloudMessageService.add(cloudMessage);
		}
		dataMap.put("acceptAndAddOK", "true");
		return dataMap;
	}

	/**
	 * 获取商友列表
	 * 
	 * @throws Exception
	 */
	@RequestMapping("getMyFriends")
	@ResponseBody
	public Map<String, List<Map>> getMyFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获得商友列表
		Map<String, List<Map>> dataMap = new HashMap<String, List<Map>>();
		List<Busiarea> friends = new ArrayList();
		List<Busiarea> list = busiareaService.getByMainEntId(ContextUtil
				.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		for (Busiarea l : list) {
			if (l.getState() == 1) {
				friends.add(l);
			}
		}
		List<Map> lst = new ArrayList();
		for (Busiarea friend : friends) {
			Map map = new HashMap();
			map.put("user", friend.getCorpEnterprise().getName());
			map.put("name", friend.getCorpEnterprise().getName());
			lst.add(map);
		}
		dataMap.put("friends", lst);
		return dataMap;
	}

	/**
	 * 删除好友
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delFriend")
	@ResponseBody
	public Map<String, String> delFriend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		Long busiareaId = RequestUtil.getLong(request, "busiareaId");
		Busiarea busiarea = busiareaService.getById(busiareaId);

		// 发送消息，设置己方也为接收方，则自己也可以看到自己发的消息
		List<Long> sendTos = new ArrayList<Long>();
		sendTos.add(busiarea.getMainEnt());
		sendTos.add(busiarea.getCorpEnt());
		// 发消息

		for (Long sendTo : sendTos) {
			CloudMessage cloudMessage = new CloudMessage();

			cloudMessage
					.setContent("<div class=\"content_02\"><font color='#400080'><strong>"
							+ ContextUtil.getCurrentOrgInfoFromSession()
									.getName()
							+ "</strong></font>"
							+ "将"
							+ "<font color='#400080'><strong>"
							+ busiarea.getCorpEnterprise().getName()
							+ "</strong></font>" + "从商友圈中删除</div>");
			cloudMessage.setSendentId(busiarea.getMainEnt());
			cloudMessage.setSenderId(ContextUtil.getCurrentUserId().toString());
			cloudMessage.setReceiveentId(sendTo);
			// cloudMessage.setReceiverId(receiverId);
			cloudMessage.setSendtime(new Date());
			// cloudMessage.setOuttime(outtime);
			cloudMessage.setResult(0);
			// cloudMessage.setLink(link);
			cloudMessage.setType(0);
			cloudMessage.setTitle("删除好友");
			cloudMessageService.add(cloudMessage);
		}

		// 删除好友
		busiareaService.delById(busiareaId);
		dataMap.put("delFriend", "true");
		return dataMap;
	}

	/**
	 * 给商友发消息 message.type=1
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendMessage")
	@ResponseBody
	public Map<String, String> sendMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		List<SysOrgInfo> toEnts = new ArrayList<SysOrgInfo>();
		CloudMessage cmessage = new CloudMessage();
		String content = RequestUtil.getSecureString(request, "message");
		Long me = ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		if (content != null) {
			toEnts = cloudMessageService.tos(content, me);
			if (toEnts.size() <= 1) {
				dataMap.put("NoCorpEnt", "true");
				return dataMap;
			} else {
				for (SysOrgInfo toEnt : toEnts) {
					cmessage.setContent(HtmlUtil.encode(content));
					cmessage.setReceiveentId(toEnt.getSysOrgInfoId());
					cmessage.setResult(0);
					cmessage.setSendentId(me);
					cmessage.setSenderId(ContextUtil.getCurrentUserId()
							.toString());
					cmessage.setSendtime(new Date());
					cmessage.setTitle(sysOrgInfoService.getById(me).getName()
							+ "发送了一条消息");
					cmessage.setType(1);
					cloudMessageService.add(cmessage);
				}
			}
		}
		dataMap.put("sendMessageOK", "true");
		return dataMap;
	}

	/**
	 * 多选的企业选择器
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listMyFriends")
	public ModelAndView listMyFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "myFriendsList");
		Long groupid=RequestUtil.getLong(request, "groupid");
		queryFilter.getFilters().put("mainEnt", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		queryFilter.addFilter("state", 1);
		if(RequestUtil.getString(request, "corpEntName")!=null){
			queryFilter.addFilter("corpEntName","%" + RequestUtil.getString(request, "corpEntName") + "%");
		}
		if(RequestUtil.getString(request, "corpEntId")!=null){
			queryFilter.addFilter("corpEntId","%" + RequestUtil.getString(request, "corpEntId") + "%");
		}
		if(groupid!=0){
			queryFilter.addFilter("groupid", groupid);
		}
		
		List<Busiarea> friends = busiareaService.getAll(queryFilter);
		Map<String,Object> m=new HashMap<String,Object>();
		//商圈分组
		m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);
		ModelAndView mav = this.getAutoView().addObject("myFriendsList", friends).addObject("businessAreaGroupList", businessAreaGroupList);
		return mav;
	}
	
	/**
	 * 选择多个企业的角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectRolesByFriends")
	public ModelAndView selectRolesByFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, false);
		Long groupid=RequestUtil.getLong(request, "groupid");
		queryFilter.getFilters().put("mainEnt", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		queryFilter.addFilter("state", 1);
		if(RequestUtil.getString(request, "corpEntName")!=null){
			queryFilter.addFilter("corpEntName","%" + RequestUtil.getString(request, "corpEntName") + "%");
		}
		if(RequestUtil.getString(request, "corpEntId")!=null){
			queryFilter.addFilter("corpEntId","%" + RequestUtil.getString(request, "corpEntId") + "%");
		}
		if(groupid!=0){
			queryFilter.addFilter("groupid", groupid);
		}
		
		List<Busiarea> friends = busiareaService.getAll(queryFilter);
		Map<String,Object> m=new HashMap<String,Object>();
		//商圈分组
		m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);
		
		//列举系统角色
		QueryFilter rolesListFilter = new QueryFilter(request, "rolesList");
		//rolesListFilter.addFilter("", "");
		List<ISysRole> roles = sysRoleService.getAll(rolesListFilter);
		
		ModelAndView mav = this.getAutoView().
					addObject("myFriendsList", friends)
					.addObject("businessAreaGroupList", businessAreaGroupList)
					.addObject("rolesList",roles);
		return mav;
	}
	
	/**
	 * 单选企业选择器
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listMyFriendsRadio")
	public ModelAndView listMyFriendsRadio(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = new QueryFilter(request, "myFriendsList");
		if(RequestUtil.getString(request, "corpEntName")!=null){
			queryFilter.addFilter("corpEntName","%" + RequestUtil.getString(request, "corpEntName") + "%");
		}
		if(RequestUtil.getString(request, "corpEntId")!=null){
			queryFilter.addFilter("corpEntId","%" + RequestUtil.getString(request, "corpEntId") + "%");
		}
		Long groupid=RequestUtil.getLong(request, "groupid");
		if(groupid!=0){
			queryFilter.addFilter("groupid", groupid);
		}
		queryFilter.getFilters().put("mainEnt", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		queryFilter.addFilter("state", 1);
		List<Busiarea> friends = busiareaService.getAll(queryFilter);
		
		Map<String,Object> m=new HashMap<String,Object>();
		//商圈分组
		m.put("entid", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		List<BusinessAreaGroup> businessAreaGroupList=businessAreaGroupService.getAllByEntid(m);
		ModelAndView mav = this.getAutoView().addObject("myFriendsList", friends)
			.addObject("businessAreaGroupList", businessAreaGroupList);;
		return mav;
	}

	

	/**
	 * 根据企业和角色获取人员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listUsersByCompAndRole")
	public ModelAndView listUsersByCompAndRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Long> compIds = new ArrayList<Long>();
		compIds.add(ContextUtil.getCurrentOrg().getSn());
		
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(RoleConst.getRole(RequestUtil.getString(request, "role")));
		
		List<ISysUser> users = sysUserService.getByCompsAndRoles(compIds, roleIds);
		ModelAndView mav = this.getAutoView().addObject("users", users);
		return mav;
	}
	
	@RequestMapping("useRes")
	@Action(description = "添加企业最近使用的资源")
	public void useRes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resName = RequestUtil.getString(request, "name");
		String resLink = RequestUtil.getString(request, "link");
		CloudUseRes cloudUseRes = new CloudUseRes();
		cloudUseRes.setId(UniqueIdUtil.genId());
		cloudUseRes.setEntid(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		cloudUseRes.setResName(resName);
		cloudUseRes.setResLink(resLink);
		cloudUseRes.setResTime(new Date());
		cloudUseResService.add(cloudUseRes);
	}
	
	
}
