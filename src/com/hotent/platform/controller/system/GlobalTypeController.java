package com.hotent.platform.controller.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.ExceptionUtil;
import com.hotent.core.util.Pingyin;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.model.system.SysTypeKey;
import com.hotent.platform.model.system.SystemConst;
import com.hotent.platform.service.bpm.thread.MessageUtil;
import com.hotent.platform.service.system.GlobalTypeService;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.service.system.SysTypeKeyService;

/**
 * 对象功能:系统分类表 控制器类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2011-11-23 11:07:27
 */
@Controller
@RequestMapping("/platform/system/globalType/")
public class GlobalTypeController extends BaseController
{
	@Resource
	private GlobalTypeService globalTypeService;
	
	@Resource
	private SysTypeKeyService sysTypeKeyService;
	
	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	/**
	 * 获取中文字全部拼音
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getPingyin")
	@ResponseBody
	@Action(description="级联删除分类，即同时删除其所有子分类")
	public String getPingyin (HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		String typeName=RequestUtil.getString(request, "typeName"); 
		String nodeKey=Pingyin.getFirstSpell(typeName);
		return nodeKey;
	}
	

	/**
	 * 取得系统分类表用于显示树层次结构的分类可以分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByParentId")
	@ResponseBody
	public List<GlobalType> getByParentId(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long parentId=RequestUtil.getLong(request, "parentId",-1);
		Long catId=RequestUtil.getLong(request, "catId",0);
		List<GlobalType> list=globalTypeService.getByParentId(parentId==-1?parentId=catId:parentId);
		//如果分类的ID和父类ID一致的情况表明，是根节点
		//为根节点的情况需要添加一个跟节点。
		if(catId.equals(parentId)){
			SysTypeKey sysTypeKey= sysTypeKeyService.getById(catId);
			GlobalType globalType=new GlobalType();
			globalType.setTypeName(sysTypeKey.getTypeName());
			globalType.setCatKey(sysTypeKey.getTypeKey());
			
			globalType.setTypeId(sysTypeKey.getTypeId());
			globalType.setParentId(0L);
			globalType.setType(sysTypeKey.getType());
			if(list.size()==0){
				globalType.setIsParent("false");
			}
			else{
				globalType.setIsParent("true");
				globalType.setOpen("true");
			}
			globalType.setNodePath(sysTypeKey.getTypeId() +".");
			list.add(0,globalType);
		}
		
		return list;
	}
	
	/**
	 * 取得系统分类表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tree")
	//@Action(description="查看系统分类列表")
	public ModelAndView tree(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<SysTypeKey> list=sysTypeKeyService.getAll();
		SysTypeKey sysTypeKey=list.get(0);
		ModelAndView mv=this.getAutoView()
		.addObject("typeList", list)
		.addObject("sysTypeKey", sysTypeKey);
		 
		return mv;
	}

	/**
	 * 删除系统分类。
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除系统分类")
	public void del(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		ResultMessage resultMessage=null;
		try {
			Long typeId =RequestUtil.getLong(request, "typeId");
			globalTypeService.delByTypeId(typeId);
			resultMessage=new ResultMessage(ResultMessage.Success, "删除系统分类成功");
			writeResultMessage(response.getWriter(), resultMessage);
		} catch (Exception e) {
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"删除系统分类失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(e);
			    resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}
	
	/**
	 * 导出系统分类。
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("exportXml")
	@Action(description="导出系统分类")
	public void exportXml(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		long typeId=RequestUtil.getLong(request, "typeId");
		String filename="";
		if(typeId!=0){
			String strXml=globalTypeService.exportXml(typeId);
			GlobalType globalType=globalTypeService.getById(typeId);
			if(globalType!=null){
				filename=globalType.getTypeName();
			}else{
				filename=sysTypeKeyService.getById(typeId).getTypeName();
			}
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" +StringUtil.encodingString(filename, "GBK",
					"ISO-8859-1") + ".xml");
			response.getWriter().write(strXml);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
	
	/**
	 * 导入系统分类。
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping("importXml")
	@Action(description="导入系统分类")
	public void importXml(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException
	{
		long typeId=RequestUtil.getLong(request, "typeId");
		MultipartFile fileLoad = request.getFile("xmlFile");
		ResultMessage resultMessage = null;
		try {
			globalTypeService.importXml(fileLoad.getInputStream(),typeId);
			resultMessage = new ResultMessage(ResultMessage.Success, "导入成功!");
			writeResultMessage(response.getWriter(), resultMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"导入系统分类失败:" + str);
				response.getWriter().print(resultMessage);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
				response.getWriter().print(resultMessage);
			}
		}
	}

	/**
	 * 取得系统分类表明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看系统分类表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"typeId");
		GlobalType po = globalTypeService.getById(id);		
		return getAutoView().addObject("globalType", po);
	}
	
	/**
	 * 排序分类列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("sortList")
	@Action(description="排序分类列表")
	public ModelAndView sortList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long parentId=RequestUtil.getLong(request, "parentId",-1);
		List<GlobalType> list=globalTypeService.getByParentId(parentId);
		return getAutoView().addObject("globalTypeList",list);
	}
	/**
	 * 排序分类
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("sort")
	@Action(description="系统分类排序",operateType="系统操作")
	public void sort(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage resultObj=null;
		PrintWriter out = response.getWriter();
		Long[] lAryId =RequestUtil.getLongAryByStr(request, "typeIds");
		if(BeanUtils.isNotEmpty(lAryId)){
			for(int i=0;i<lAryId.length;i++){
				Long typeId=lAryId[i];
				long sn=i+1;
				globalTypeService.updSn(typeId,sn);
			}
		}
		resultObj=new ResultMessage(ResultMessage.Success,"排序分类完成");
		out.print(resultObj);
	}
	
	/**
	 * 移动分类数据。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("move")
	@Action(description="转移分类")
	public void move(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage resultMessage=null;
		PrintWriter out = response.getWriter();
		long targetId=RequestUtil.getLong(request, "targetId",0);
		long dragId=RequestUtil.getLong(request, "dragId",0);
		String moveType=RequestUtil.getString(request, "moveType");
		try{
			globalTypeService.move(targetId, dragId, moveType);
			resultMessage=new ResultMessage(ResultMessage.Success,"转移分类完成");
		}
		catch(Exception ex){
			String str = MessageUtil.getMessage();
			if (StringUtil.isNotEmpty(str)) {
				resultMessage = new ResultMessage(ResultMessage.Fail,"转移分类失败:" + str);
			} else {
				String message = ExceptionUtil.getExceptionMessage(ex);
				resultMessage = new ResultMessage(ResultMessage.Fail, message);
			}
		}
		out.print(resultMessage);
	}
	
	@RequestMapping("edit")
	@Action(description="编辑系统分类")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		return getEditView(request);
	}
	
	@RequestMapping("dialog")
	@Action(description="编辑系统分类")
	public ModelAndView dialog(HttpServletRequest request) throws Exception
	{
		return getEditView(request);
	}
	
	/**
	 * 取得编辑视图。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ModelAndView getEditView(HttpServletRequest request) throws Exception{
		int isRoot=RequestUtil.getInt(request,"isRoot",0);
		Long parentId=RequestUtil.getLong(request,"parentId");
		Long typeId=RequestUtil.getLong(request,"typeId");
		int isPrivate=RequestUtil.getInt(request,"isPrivate",0);
		
		String parentName="";
		GlobalType globalType=null;
		boolean isDict=false;
		boolean isAdd=false;
		if(typeId>0){
			globalType= globalTypeService.getById(typeId);	
			parentId=globalType.getParentId();
			isDict=globalType.getCatKey().equals(GlobalType.CAT_DIC);
		}else{
			GlobalType tmpGlobalType=globalTypeService.getInitGlobalType(isRoot,parentId);
			parentName=tmpGlobalType.getTypeName();
			isDict=tmpGlobalType.getCatKey().equals(GlobalType.CAT_DIC);
			globalType=new GlobalType();
			globalType.setType(tmpGlobalType.getType());
			isAdd=true;
		}
		return getAutoView()
				.addObject("globalType",globalType)
				.addObject("parentId",parentId)
				.addObject("isRoot",isRoot)
				.addObject("isAdd",isAdd)
				.addObject("parentName", parentName)
				.addObject("isDict", isDict)
				.addObject("isPrivate", isPrivate);
		
	}
	
	/**
	 * 根据catKey获取分类 。
	 * catKey：分类key
	 * hasRoot：是否有根节点。1，有根节点，0，无根节点。
	 * @param request
	 * @return
	 */
	@RequestMapping("getByCatKey")
	@ResponseBody
	public List<GlobalType> getByCatKey(HttpServletRequest request){
		String catKey=RequestUtil.getString(request,"catKey");
		logger.debug("[catKey]:"+catKey);
		boolean hasRoot=RequestUtil.getInt(request, "hasRoot",1)==1;
		 
		List<GlobalType> list=globalTypeService.getByCatKey(catKey, hasRoot);
		return list;
	}
	
	/**
	 * 根据catKey获取流程分类Bpm。
	 * catKey：分类key
	 * hasRoot：是否有根节点。1，有根节点，0，无根节点。
	 * @param request
	 * @return
	 */
	@RequestMapping("getByCatKeyForBpm")
	@ResponseBody
	public Set<GlobalType> getByCatKeyForBpm(HttpServletRequest request){
		ISysUser curUser=ContextUtil.getCurrentUser();
		//取得当前用户所有的角色Ids
		String roleIds=sysRoleService.getRoleIdsByUserId(curUser.getUserId());
		//取得当前组织
		String orgIds=sysOrgService.getOrgIdsByUserId(curUser.getUserId());
		Set<GlobalType> globalTypeSet=null;
		//非超级管理员需要按权限过滤
//		if(!curUser.getAuthorities().contains(SystemConst.ROLE_GRANT_SUPER)){
//			globalTypeSet=globalTypeService.getByBpmRightCat(curUser.getUserId(), roleIds, orgIds, true);
//		}else{
			globalTypeSet=new HashSet<GlobalType>();
			globalTypeSet.addAll(globalTypeService.getByCatKey(GlobalType.CAT_FLOW, true));
//		}
		
		return globalTypeSet;
	}
	
	/**
	 * 根据catKey获取表单分类
	 * @param request
	 * @return
	 */
	@RequestMapping("getByCatKeyForForm")
	@ResponseBody
	public Set<GlobalType> getByCatKeyForForm(HttpServletRequest request){
		ISysUser curUser=ContextUtil.getCurrentUser();
		//取得当前用户所有的角色Ids
		String roleIds=sysRoleService.getRoleIdsByUserId(curUser.getUserId());
		//取得当前组织
		String orgIds=sysOrgService.getOrgIdsByUserId(curUser.getUserId());
		Set<GlobalType> globalTypeSet=null;
		//非超级管理员需要按权限过滤
		if(!curUser.getAuthorities().contains(SystemConst.ROLE_GRANT_SUPER)){
			globalTypeSet=globalTypeService.getByFormRightCat(curUser.getUserId(), roleIds, orgIds, true);
		}else{
			globalTypeSet=new HashSet<GlobalType>();
			globalTypeSet.addAll(globalTypeService.getByCatKey(GlobalType.CAT_FORM, true));
		}		
		return globalTypeSet;
	}
	
	/**
	 * 取得个人的分类。
	 * @param request
	 * @return
	 */
	@RequestMapping("getPersonType")
	@ResponseBody
	public List<GlobalType> getPersonType(HttpServletRequest request){
		String catKey=RequestUtil.getString(request,"catKey");
		boolean hasRoot=RequestUtil.getInt(request, "hasRoot",1)==1;
		Long userId=ContextUtil.getCurrentUserId();
		 
		List<GlobalType> list=globalTypeService.getPersonType(catKey,userId, hasRoot);
		return list;
	}

}
