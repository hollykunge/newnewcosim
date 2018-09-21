package com.casic.cloud.controller.cloudResource.approvalResource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.casic.cloud.controller.cloudResource.resourceManagement.CloudResourceController;
import com.casic.cloud.model.cloudResource.approvalResource.ApprovalResource;
import com.casic.cloud.service.cloudResource.approvalResource.ApprovalResourceService;
import com.hotent.core.web.ResultMessage;
/**
 *<pre>
 * 对象功能:cloud_resource_approval 控制器类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-27 10:27:37
 *</pre>
 */
@Controller
@RequestMapping("/cloud/cloudResource/approvalResource/")
public class ApprovalResourceController extends BaseController
{
	@Resource
	private ApprovalResourceService approvalResourceService;
	
	HttpURLConnection urlConn = null;
    URL url = null;
	
	private static String lsf= "";
	StringBuilder sb = new StringBuilder();
	static {
		//get Remote IP 
				InputStream fis =ApprovalResourceController.class.getClassLoader().getResourceAsStream("conf/custom.properties");
				Properties p = new Properties();
				try {
					p.load(fis);
					lsf=p.getProperty("engineframeAddress");
				} catch (IOException e) {
					e.printStackTrace();
				}
		
	}
	
	/**
	 * 添加或更新cloud_resource_approval。
	 * @param request
	 * @param response
	 * @param approvalResource 添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新cloud_resource_approval")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg=null;		
		ApprovalResource approvalResource=getFormObject(request);
		try{
			if(approvalResource.getId()==null||approvalResource.getId()==0){
				approvalResource.setId(UniqueIdUtil.genId());
				approvalResourceService.add(approvalResource);
				resultMsg=getText("record.added","cloud_resource_approval");
			}else{
			    approvalResourceService.update(approvalResource);
				resultMsg=getText("record.updated","cloud_resource_approval");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得 ApprovalResource 实体 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    protected ApprovalResource getFormObject(HttpServletRequest request) throws Exception {
    
    	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
    
		String json=RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		
		ApprovalResource approvalResource = (ApprovalResource)JSONObject.toBean(obj, ApprovalResource.class);
		
		return approvalResource;
    }
	
	/**
	 * 取得cloud_resource_approval分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看cloud_resource_approval分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ApprovalResource> list=approvalResourceService.getAll(new QueryFilter(request,"approvalResourceItem"));
		ModelAndView mv=this.getAutoView().addObject("approvalResourceList",list);
		
		return mv;
	}
	
	/**
	 * 删除cloud_resource_approval
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除cloud_resource_approval")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
			approvalResourceService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除cloud_resource_approval成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑cloud_resource_approval
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑cloud_resource_approval")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		ApprovalResource approvalResource=approvalResourceService.getById(id);
		approvalResource.setApprovalData(new Date());
		return getAutoView().addObject("approvalResource",approvalResource).addObject("returnUrl", returnUrl);
	}

	/**
	 * 	编辑cloud_resource_approval
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("create")
	@Action(description="申请使用资源")
	public ModelAndView create(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		Long resourceID = RequestUtil.getLong(request, "resourceID");
		String resourceName = RequestUtil.getString(request, "resourceName");
		ApprovalResource approvalResource=approvalResourceService.getById(id);
		if(approvalResource==null){
			approvalResource = new ApprovalResource();
			//approvalResource.setId(UniqueIdUtil.genId());
			approvalResource.setEnpId(ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
			approvalResource.setUserId(ContextUtil.getCurrentUser().getUserId());
			approvalResource.setResId(resourceID);
			approvalResource.setResName(resourceName);
			approvalResource.setApplyDate(new Date());
			approvalResource.setState(0);
		}
		return getAutoView().addObject("approvalResource",approvalResource);
	}
	/**
	 * 检查使用资源权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getResource")
	public ModelAndView getResource(HttpServletRequest request) throws Exception
	{
		String returnUrl=RequestUtil.getPrePage(request);
		//得到资源的url和id
		String resourceUrl = RequestUtil.getStringValues(request, "resourceUrl");
			String result = getDocumentAt(resourceUrl);
			if("unreachable".equals(result)){
				return new ModelAndView("/cloud/cloudResource/approvalResource/resourceError.jsp")
				.addObject("error", "连接资源服务器失败");
			}else{
				return new ModelAndView("/cloud/cloudResource/redirect.jsp")
				.addObject("url", resourceUrl)
				.addObject("returnUrl", returnUrl)
				.addObject("lsf", lsf)
				.addObject("userName","jhadmin")
				.addObject("userPw","jhadmin");
		}
	}
	
	/**
	 * 直接获取资源权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkResource")
	public ModelAndView checkResource(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		//得到资源的url和id
		String resourceUrl = RequestUtil.getStringValues(request, "resourceUrl");
		Long resourceID = RequestUtil.getLong(request, "resourceID");
		String resourceName = RequestUtil.getString(request, "resourceName");
		//判断是否申请使用资源
		QueryFilter queryFilter = new QueryFilter(request, "approvalResource");		
		queryFilter.getFilters().put("enpId", ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId());
		queryFilter.getFilters().put("resId", resourceID);
		queryFilter.getFilters().put("state", 1);
		
		List<ApprovalResource> approvalResource = approvalResourceService.getAll(queryFilter);
		if(approvalResource==null || approvalResource.size()==0){
			return new ModelAndView("redirect:/cloud/cloudResource/approvalResource/create.ht?resourceID="+resourceID+"&resourceName="+resourceName+"")
			.addObject("returnUrl", returnUrl);
		}else{
			String result = getDocumentAt(resourceUrl);
			if("unreachable".equals(result)){
				return new ModelAndView("/cloud/cloudResource/approvalResource/resourceError.jsp")
				.addObject("error", "连接资源服务器失败");
			}else{
				return new ModelAndView("/cloud/cloudResource/redirect.jsp")
				.addObject("url", resourceUrl)
				.addObject("returnUrl", returnUrl)
				.addObject("userName","jhadmin")
				.addObject("userPw","jhadmin");
			}
			
		}
	}
	
	
	public String getDocumentAt(String urlString) {
		String result = "";
		try {
            url = new URL(urlString);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);        //设置输入流采用字节流
            urlConn.setDoOutput(true);        //设置输出流采用字节流
            urlConn.setRequestMethod("POST");
            urlConn.setUseCaches(false);    //设置缓存
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Charset", "utf-8");
            urlConn.setConnectTimeout(10000);
            urlConn.connect();
            
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());

            dos.writeBytes("tqpadmac="+URLEncoder.encode("B407F9D67C80", "utf-8"));
            dos.writeBytes("tqpadver="+URLEncoder.encode("1", "utf-8"));
            dos.flush();
            dos.close();
            
           //这句：new InputStreamReader(urlConn.getInputStream(), "gbk")  设置编码  
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"gbk"));
            String line = "";
            
            while(null != (line=br.readLine())){
                result += line;
            }
            System.out.println("接到的数据: " + result);
            br.close();
            urlConn.disconnect();
        } catch (Exception e) {
        	result = "unreachable";
            e.printStackTrace();
        }
		  return result;
		}
	/**
	 * 取得cloud_resource_approval明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看cloud_resource_approval明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		long id=RequestUtil.getLong(request,"id");
		ApprovalResource approvalResource = approvalResourceService.getById(id);	
		return getAutoView().addObject("approvalResource", approvalResource);
	}
	
}
