/**
 * 
 */
package com.casic.cloud.controller.pub.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.appleframe.utils.file.FileUtils;
import com.casic.cloud.pub.util.CompressPicUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;

/**
 * 上传文件的控制器类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cloud/pub/image")
public class BusinessChanceImageController extends BaseController{
	public static long FILE_MAX_SIZE = 100 * 1024;
			
	@RequestMapping("toUpload")
	public ModelAndView toUpload(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnUrl=RequestUtil.getPrePage(request);
		return getAutoView().addObject("returnUrl", returnUrl);
	};
	
	@RequestMapping("toUploadFile")
	public ModelAndView toUploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String returnUrl=RequestUtil.getPrePage(request);
		
		return getAutoView().addObject("returnUrl", returnUrl);
	};
	
	@RequestMapping("upload")
	public ModelAndView upload(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resultMsg = "文件上传成功";
		ServletContext sc = request.getSession().getServletContext();
		 
		//图片保存目录
		String dir = sc.getRealPath("/upload/") + "\\" +  ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId();
		//图片存放临时目录
		String tmpDir = sc.getRealPath("/upload/") + "\\" + "tmp";
	 
	    String uuid = UUID.randomUUID().toString();  
		String str=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
		String filename=uuid+str;
		
		//图片保存路径
		String path = sc.getRealPath("/upload/") +"\\"+ ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId() +"\\"+ filename;
		
		//图片相对目录
		String fileDir =  "/upload/" + ContextUtil.getCurrentOrgInfoFromSession().getSysOrgInfoId() + "/";
		
		try{		
			//没有则创建路径
			FileUtils.mkdirs(sc.getRealPath("/upload"));			
			FileUtils.mkdirs(dir);			
			FileUtils.mkdirs(tmpDir);
						
		  if(file.getSize() > FILE_MAX_SIZE){
				resultMsg = "图片过大,系统将自动压缩成缩略图";
				
				//存放到临时路径				
				String tmpPath = tmpDir + "\\" + filename;
				saveFileFromInputStream(file.getInputStream(), tmpPath);
				File tmpFile = new File(tmpPath);
				CompressPicUtil compressPic = new CompressPicUtil();
				compressPic.compressPic(tmpFile, dir + "\\", filename, 200, 200, true);
				
				//删除图片
				FileUtils.delete(tmpFile);
			}else{//直接保存图片
				saveFileFromInputStream(file.getInputStream(),path);
			}
		}catch(Exception e){
			resultMsg = "文件上传错误" + e.getMessage();
		}
		 
		return getAutoView().addObject("resultMsg",resultMsg)
							.addObject("_callback",request.getParameter("_callback"))
							.addObject("filePath", fileDir + filename);
	}
	
	
	/**保存文件
    * @param stream
    * @param path
    * @param filename
    * @throws IOException
    */
   public void saveFileFromInputStream(InputStream stream,String path) throws IOException{      
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
