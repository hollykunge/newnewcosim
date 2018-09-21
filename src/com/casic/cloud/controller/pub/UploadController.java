/**
 * 
 */
package com.casic.cloud.controller.pub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.appleframe.utils.file.FileUtils;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;

/**
 * 上传文件的控制器类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cloud/pub/")
public class UploadController extends BaseController{
	@RequestMapping("toUpload")
	public ModelAndView toUpload(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnUrl=RequestUtil.getPrePage(request);
		
		return getAutoView().addObject("returnUrl", returnUrl);
	};
	
	@RequestMapping("personalRegtoUpload")
	public ModelAndView personalRegtoUpload(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnUrl=RequestUtil.getPrePage(request);
		
		return getAutoView().addObject("returnUrl", returnUrl);
	};
	
	@RequestMapping("toUploadFile")
	public ModelAndView toUploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnUrl=RequestUtil.getPrePage(request);
		
		return getAutoView().addObject("returnUrl", returnUrl);
	};
	
	@RequestMapping("uploadFile")
	public ModelAndView uploadFile(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resultMsg = "文件上传成功";
		ServletContext sc = request.getSession().getServletContext();
		ModelAndView mav = new ModelAndView();
		try{			
			FileUtils.mkdirs(sc.getRealPath("/upload"));
			FileUtils.mkdirs(sc.getRealPath("/upload/"+ContextUtil.getCurrentOrg().getOrgId()));
			
			//按企业ID存放,避免文件名相同
			String path = sc.getRealPath("/upload/") +"\\"+ ContextUtil.getCurrentOrg().getOrgId() +"\\"+ file.getOriginalFilename();
			SaveFileFromInputStream(file.getInputStream(),path);
			mav.addObject("filePath", "/upload/"+ ContextUtil.getCurrentOrg().getOrgId() +"/" + file.getOriginalFilename());
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			resultMsg = "文件上传错误" + e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
		mav.addObject("resultMsg",resultMsg);
		
		return getAutoView().addObject("resultMsg",resultMsg)
				.addObject("_callback",request.getParameter("_callback"))
				.addObject("fileName",file.getOriginalFilename())
				.addObject("filePath", "/upload/"+ ContextUtil.getCurrentOrg().getOrgId()+"/" + file.getOriginalFilename());
	}
	
	@RequestMapping("upload")
	public ModelAndView upload(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resultMsg = "文件上传成功";
		ServletContext sc = request.getSession().getServletContext();
		ModelAndView mav = new ModelAndView();
		try{			
			FileUtils.mkdirs(sc.getRealPath("/upload"));
			FileUtils.mkdirs(sc.getRealPath("/upload/"+ContextUtil.getCurrentOrg().getOrgId()));
			
			//按企业ID存放,避免文件名相同
			String path = sc.getRealPath("/upload/") +"\\"+ ContextUtil.getCurrentOrg().getOrgId() +"\\"+ file.getOriginalFilename();
			if(FileUtils.exists(path)){
				resultMsg = "文件名已存在";
			}else if(file.getSize()>2000000){
				resultMsg = "文件大小不能超过2M";
			}else{
				SaveFileFromInputStream(file.getInputStream(),path);
				mav.addObject("filePath", "/upload/"+ ContextUtil.getCurrentOrg().getOrgId() + file.getOriginalFilename());
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			resultMsg = "文件上传错误" + e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
		mav.addObject("resultMsg",resultMsg);
		 
		return getAutoView().addObject("resultMsg",resultMsg)
							.addObject("_callback",request.getParameter("_callback"))
							.addObject("filePath", "/upload/"+ ContextUtil.getCurrentOrg().getOrgId()+"/" + file.getOriginalFilename());
	}
	
	@RequestMapping("personalRegUpload")
	public ModelAndView personalRegUpload(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resultMsg = "文件上传成功";
		ServletContext sc = request.getSession().getServletContext();
		ModelAndView mav = new ModelAndView();
		try{			
			FileUtils.mkdirs(sc.getRealPath("/upload"));
			FileUtils.mkdirs(sc.getRealPath("/upload/"+"personalReg"));
			
			//按企业ID存放,避免文件名相同
			String path = sc.getRealPath("/upload/") +"\\"+ "personalReg" +"\\"+ file.getOriginalFilename();
			if(FileUtils.exists(path)){
				resultMsg = "文件名已存在";
			}else if(file.getSize()>2000000){
				resultMsg = "文件大小不能超过2M";
			}else{
				SaveFileFromInputStream(file.getInputStream(),path);
				mav.addObject("filePath", "/upload/"+ "personalReg" + file.getOriginalFilename());
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			resultMsg = "文件上传错误" + e.getMessage();
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
		mav.addObject("resultMsg",resultMsg);
		 
		return getAutoView().addObject("resultMsg",resultMsg)
							.addObject("_callback",request.getParameter("_callback"))
							.addObject("filePath", "/upload/"+ "personalReg"+"/" + file.getOriginalFilename());
	}
	
	
	/**保存文件
    * @param stream
    * @param path
    * @param filename
    * @throws IOException
    */
   public void SaveFileFromInputStream(InputStream stream,String path) throws IOException{      
       FileOutputStream fs=new FileOutputStream( path );
       byte[] buffer =new byte[1024*1024];
       int bytesum = 0;
       int byteread = 0; 
       while ((byteread=stream.read(buffer))!=-1){
          bytesum+=byteread;
          fs.write(buffer,0,byteread);
          fs.flush();
       } 
       fs.close();
       stream.close();      
   }       

}
