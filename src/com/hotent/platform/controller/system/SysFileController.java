package com.hotent.platform.controller.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.core.annotion.Action;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.service.system.GlobalTypeService;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.service.system.SysTypeKeyService;
import com.hotent.platform.service.system.SysUserService;
/**
 * 对象功能:附件 控制器类 
 * 开发公司:
 * 开发人员:csx 
 * 创建时间:2011-11-26 18:19:16
 */
@Controller
@RequestMapping("/platform/system/sysFile/")
public class SysFileController extends BaseController {
	private Log logger = LogFactory.getLog(SysFileController.class);
	
	@Resource
	private SysFileService sysFileService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private SysTypeKeyService sysTypeKeyService;
	

	/**
	 * 取得附件分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description = "查看附件分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Long typeId=RequestUtil.getLong(request, "typeId");
		QueryFilter filter=new QueryFilter(request,"sysFileItem");
		if(typeId!=0){
			filter.addFilter("typeId", typeId);
		}
		
		List<SysFile> list = sysFileService.getAll(filter);
		return getAutoView().addObject("sysFileList", list);
	}
	/**
	 * 删除附件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description = "删除附件")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String returnUrl=RequestUtil.getPrePage(request);
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "fileId");
		try {
			for(Long id:lAryId){
				SysFile sysFile=sysFileService.getById(id);
				String filePath=sysFile.getFilePath();
				//删除文件
				FileUtil.deleteFile(AppUtil.getRealPath(filePath));
			}
		    //删除数据库中数据
			sysFileService.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除附件成功");
		} 
		catch (Exception e) 
		{
			message = new ResultMessage(ResultMessage.Fail, "删除附件失败");
		}
		addMessage(message, request);
		response.sendRedirect(returnUrl);
	}
	
	/**
	 * 删除附件。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delByFileId")
	@Action(description = "删除附件")
	public void delByFileId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter();
		Long[] lAryId = RequestUtil.getLongAryByStr(request, "ids");
		try {
			for(Long id:lAryId){
				SysFile sysFile=sysFileService.getById(id);
				String filePath=sysFile.getFilePath();
				//删除文件
				FileUtil.deleteFile(AppUtil.getRealPath(filePath));
			}
		    //删除数据库中数据
			sysFileService.delByIds(lAryId);
			out.print("{\"success\":\"true\"}");
		} 
		catch (Exception e) 
		{
			out.print("{\"success\":\"false\"}");
		}
		
	}
	
	
	@RequestMapping("edit")
	@Action(description = "编辑附件")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Long fileId = RequestUtil.getLong(request, "fileId");
		String returnUrl = RequestUtil.getPrePage(request);
		SysFile sysFile = null;
		
		if (fileId != 0) {
			sysFile = sysFileService.getById(fileId);
		} else {
			sysFile = new SysFile();
		}
		return getAutoView().addObject("sysFile", sysFile).addObject(
				"returnUrl", returnUrl);
	}
	/**
	 * 取得附件明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description = "查看附件明细")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = RequestUtil.getLong(request, "fileId");
		SysFile sysFile = sysFileService.getById(id);
		return getAutoView().addObject("sysFile", sysFile);
	}

	/**
	 * 附件上传操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("fileUpload")
	@Action(description = "文件上传")
	public void fileUpload(MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try{
			// 获取当前用户的id
			long userId =  ContextUtil.getCurrentUserId();
			long typeId =  RequestUtil.getLong(request,"typeId");
			ISysUser appUser = null;
			if(userId>0){			
				appUser = sysUserService.getById(userId);
			}
			// 获取附件类型
			GlobalType globalType = null;
			if(typeId>0){
				globalType = globalTypeService.getById(typeId);
			}
			Map<String, MultipartFile> files = request.getFileMap();
			Iterator<MultipartFile> it = files.values().iterator();
			
			while (it.hasNext()) {
				Long fileId=UniqueIdUtil.genId();
				MultipartFile f = it.next();
				String oriFileName = f.getOriginalFilename();
			    String extName=FileUtil.getFileExt(oriFileName);
			    String fileName= fileId + "." + extName;
			
				//开始写入物理文件
				String filePath = createFilePath(AppUtil.getRealPath("/attachFiles/temp") , fileName);
			    FileUtil.writeByte(filePath, f.getBytes()) ;
				// end 写入物理文件
			    // 向数据库中添加数据 
			    SysFile sysFile = new SysFile();
			    sysFile.setFileId(fileId);
			    //附件名称
			    sysFile.setFileName(oriFileName.substring(0, oriFileName.lastIndexOf('.')));
			    
			    Calendar cal = Calendar.getInstance();
			    Integer year = cal.get(Calendar.YEAR);
			    Integer month = cal.get(Calendar.MONTH) + 1;
			    //附件路径
			    sysFile.setFilePath("attachFiles/temp/" + year + "/" + month + "/" +  fileName);
			    //附件类型
			    if(globalType != null){		
			    	sysFile.setTypeId(globalType.getTypeId());
			    	sysFile.setFileType(globalType.getTypeName());
			    } else {
			    	sysFile.setTypeId(sysTypeKeyService.getByKey(GlobalType.CAT_FILE).getTypeId());
			    	sysFile.setFileType("-");
			    }
			    // 上传时间
			    sysFile.setCreatetime(new java.util.Date());
			    // 扩展名
			    sysFile.setExt(extName);
			    //字节总数
			    sysFile.setTotalBytes(f.getSize());
			    // 说明
			    sysFile.setNote(FileUtil.getSize(f.getSize()));
			    // 当前用户的信息
			    if(appUser != null){
			    	sysFile.setCreatorId(appUser.getUserId());
			    	sysFile.setCreator(appUser.getUsername());
			    } else {
			    	sysFile.setCreator(SysFile.FILE_UPLOAD_UNKNOWN);
			    }
			    //总的字节数
			    sysFile.setDelFlag(SysFile.FILE_NOT_DEL);
			    sysFileService.add(sysFile);
			    // end 向数据库中添加数据
			    writer.println("{\"success\":\"true\",\"fileId\":\""+ fileId +"\"}");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			writer.println("{\"success\":\"false\"}");
		}
	}
	
	/**
	 * 如果文件存在则更新，如果文件不存在则新建文件。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveFile")
	@Action(description = "保存文件")
	public void saveFile(MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try{
			long userId =  ContextUtil.getCurrentUserId(); // 获取当前用户的id
			long typeId =  RequestUtil.getLong(request,"typeId");
			long fileId=RequestUtil.getLong(request, "fileId");
			ISysUser appUser = null;
			if(userId>0){			
				appUser = sysUserService.getById(userId);
			}
			// 获取附件类型
			GlobalType globalType = null;
			if(typeId>0){
				globalType = globalTypeService.getById(typeId);
			}
			Map<String, MultipartFile> files = request.getFileMap();
			Iterator<MultipartFile> it = files.values().iterator();
			
			while (it.hasNext()) {
				boolean isAdd=true;
				SysFile sysFile = null;
				if(fileId==0){
					fileId=UniqueIdUtil.genId();
					sysFile = new SysFile();
					sysFile.setFileId(fileId);
				}
				else{
					sysFile=sysFileService.getById(fileId);
					isAdd=false;
				}
				
				MultipartFile f = it.next();
				String oriFileName = f.getOriginalFilename();
			    String extName=FileUtil.getFileExt(oriFileName);
			    String fileName= fileId + "." + extName;
			
				//开始写入物理文件
				String filePath = createFilePath(AppUtil.getRealPath("/attachFiles/temp") , fileName);
			    FileUtil.writeByte(filePath, f.getBytes()) ;
			    
			    //附件名称
			    sysFile.setFileName(oriFileName.substring(0, oriFileName.lastIndexOf('.')));
			    
			    Calendar cal = Calendar.getInstance();
			    Integer year = cal.get(Calendar.YEAR);
			    Integer month = cal.get(Calendar.MONTH) + 1;
			    //附件路径
			    sysFile.setFilePath("attachFiles/temp/" + year + "/" + month + "/" +  fileName);
			    //附件类型
			    if(globalType != null){		
			    	sysFile.setTypeId(globalType.getTypeId());
			    	sysFile.setFileType(globalType.getTypeName());
			    } else {
			    	sysFile.setTypeId(0L);
			    	sysFile.setFileType("-");
			    }
			    // 上传时间
			    sysFile.setCreatetime(new java.util.Date());
			    // 扩展名
			    sysFile.setExt(extName);
			    //字节总数
			    sysFile.setTotalBytes(f.getSize());
			    // 说明
			    sysFile.setNote(FileUtil.getSize(f.getSize()));
			    // 当前用户的信息
			    if(appUser != null){
			    	sysFile.setCreatorId(appUser.getUserId());
			    	sysFile.setCreator(appUser.getUsername());
			    } else {
			    	sysFile.setCreator(SysFile.FILE_UPLOAD_UNKNOWN);
			    }
			    //总的字节数
			    sysFile.setDelFlag(SysFile.FILE_NOT_DEL);
			    if(isAdd){
			    	sysFileService.add(sysFile);
			    }
			    else{
			    	sysFileService.update(sysFile);
			    }
			    // end 向数据库中添加数据
			    writer.print( fileId);
			}
		}
		catch (Exception e) {
			logger.warn(e.getMessage());
			writer.print( -1);
		}
	} 
	
	

	/**
	 * 创建文件目录
	 * @param tempPath
	 * @param fileName 文件名称
	 * @return 文件的完整目录
	 */
	private String createFilePath(String tempPath, String fileName){
		File one = new File(tempPath);
		Calendar cal = Calendar.getInstance(); 
		Integer year = cal.get(Calendar.YEAR); // 当前年份
		Integer month = cal.get(Calendar.MONTH) + 1; // 当前月份
		one = new File(tempPath + "/" + year + "/" + month);
		if(!one.exists()){
			one.mkdirs();
		}
		return one.getPath() + File.separator+ fileName;
	}
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selector")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView result = getAutoView();
		Long userId=ContextUtil.getCurrentUserId();
		Long typeId=RequestUtil.getLong(request, "typeId");
		QueryFilter filter=new QueryFilter(request,"sysFileItem");
		
		filter.addFilter("userId",userId);
		Long cateTypeId=sysTypeKeyService.getByKey(GlobalType.CAT_FILE).getTypeId();
		if(typeId!=0L&&!typeId.equals(cateTypeId)){
			filter.addFilter("typeId", typeId);
		}else{
			filter.addFilter("typeId", null);
		}
		List<SysFile> list = sysFileService.getFileAttch(filter);
		result.addObject("sysFileList",list);
		int isSingle=RequestUtil.getInt(request, "isSingle",0);
		result.addObject("isSingle", isSingle);
		return result;
	}
	
	
	/**
	 * 附件下载
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("download")
	public  void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException   { 
		
        long fileId=RequestUtil.getLong(request, "fileId",0);
        if(fileId==0) return;
        SysFile sysFile=sysFileService.getById(fileId);
        if(sysFile==null) return;
        String filePath=sysFile.getFilePath();
        if(StringUtil.isEmpty(filePath)) return ;
        
        String fullPath= FileUtil.getRootPath() + filePath.replace("/", File.separator);
        
        String fileName=sysFile.getFileName() +"." + sysFile.getExt();
        
        FileUtil.downLoadFile(response, fullPath,fileName);
		      
	}	
	
	/**
	 * 根据文件id取得附件数据。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getFileById")
	public  void getFileById(HttpServletRequest request, HttpServletResponse response) throws IOException   { 
		
        long fileId=RequestUtil.getLong(request, "fileId",0);
        if(fileId==0) return;
        SysFile sysFile=sysFileService.getById(fileId);
        
        String filePath=sysFile.getFilePath();
        
        String fullPath= FileUtil.getRootPath() + filePath.replace("/", File.separator);
        
		byte[] bytes=FileUtil.readByte(fullPath);
		response.getOutputStream().write(bytes);
		      
	}	
	
}
