package com.casic.cloud.controller.system.exlImport;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.appleframe.utils.excel.ExcelSheet;
import com.appleframe.utils.excel.ExcelUtils;
import com.casic.cloud.service.config.info.InfoService;
import com.casic.cloud.service.system.exlImport.ExlImportService;
import com.hotent.core.annotion.Action;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.platform.model.system.SysOrgInfo;
import com.hotent.platform.service.system.SysOrgInfoService;
import com.hotent.platform.service.system.SysUserService;
@Controller
@RequestMapping("/cloud/system/exlImport")
public class ExlImportController extends BaseController{
	@Resource
	private ExlImportService importManager;

	@RequestMapping("toImport")
	public ModelAndView toImport() throws Exception{
		return this.getAutoView();
	}
	@RequestMapping("toImportByUser")
	public ModelAndView toImportByUser() throws Exception{
		return this.getAutoView();
	}
	@Resource
	private InfoService infoService;
	
	@Resource
	private SysOrgInfoService sysOrgInfoService;
	
	@Resource
	private SysUserService sysUserService;

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importExl")
	@Action(description="导入数据")
	public void importExl(
			@RequestParam("table") String table,
			@RequestParam("key") String key,
			@RequestParam("sheetNum") int sheetNum,
			@RequestParam("excel") MultipartFile excel,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg = "";
		try{
			InputStream in = excel.getInputStream();
			resultMsg = importManager.importExlWithTransaction(in, sheetNum, 0, table, key);			
		}catch(Exception ex){
			resultMsg = ex.getMessage();
		}
		writeResultMessage(response.getWriter(),resultMsg, ResultMessage.Success);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("importExl1")
	@Action(description="导入数据")
	public void importExl1(
			@RequestParam("table") String table,
			@RequestParam("key") String key,
			@RequestParam("sheetNum") int sheetNum,
			@RequestParam("headRowNum") int headRowNum,
			@RequestParam("excel") MultipartFile excel,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultMsg = "";
		try{
			InputStream in = excel.getInputStream();
			resultMsg = importManager.importExlWithTransaction(in, sheetNum, headRowNum, table, key);			
		}catch(Exception ex){
			resultMsg = ex.getMessage();
		}
		writeResultMessage(response.getWriter(),resultMsg, ResultMessage.Success);
	}
	
	@RequestMapping("importCompany")
	public void importCompany(
			@RequestParam("excel") MultipartFile excel,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExcelSheet sheet = ExcelUtils.readExl(excel.getInputStream(), 0);
		
		String resultMsg = "", failureMsg = "";
		int i = 0, j = 0, count=0, currentRow = 1;
		for(Map<String,Object> map : sheet.getRows()){	
			try{
				SysOrgInfo sysOrgInfo = new SysOrgInfo();
				sysOrgInfo.setSysOrgInfoId(Long.parseLong(map.get("编号").toString()));
				sysOrgInfo.setEmail(map.get("邮箱").toString());
				sysOrgInfo.setName(map.get("名称").toString());
				sysOrgInfo.setIndustry(map.get("行业1").toString());
				sysOrgInfo.setIndustry2(map.get("行业2").toString());
				sysOrgInfo.setAddress(map.get("地址").toString());
				sysOrgInfo.setPostcode(map.get("邮编").toString());
				sysOrgInfo.setConnecter(map.get("企业联系人").toString());
				sysOrgInfo.setFax(map.get("传真").toString());
				sysOrgInfo.setCountry(map.get("国家").toString());
	//			sysOrgInfo.setProvince(map.get("省份").toString());
	//			sysOrgInfo.setCity(map.get("市区").toString());
	//			sysOrgInfo.setWebsite(map.get("企业网址").toString());
				sysOrgInfo.setFlaglogo(sysUserService.getFlagLogoByCountry(sysOrgInfo.getCountry()));
				sysOrgInfo.setRegisttime(new Date());
				if(sysOrgInfoService.getById(sysOrgInfo.getSysOrgInfoId()) != null){//更新
					sysOrgInfoService.update(sysOrgInfo);		
				}else{//新增
					sysUserService.registerSysOrg(sysOrgInfo, "123456");
				}
				i++;
			}catch(Exception e){
				j++;
				failureMsg += currentRow + ",[" + e.getMessage() + "]<br/>";
			}finally{
				currentRow++;
			}
		}
		count = sheet.getRows().size();
		resultMsg = "此次导入共读取" + count + "条,其中成功" + i + "条,失败" + j + "条<br/>";
		if(j>1)
			resultMsg += "失败信息:<br/>" + failureMsg;
		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
	}
}
