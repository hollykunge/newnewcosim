package com.casic.cloud.pub.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载Servlet,将文件转化为二进制流
 * zouping,2013-05-18
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取文件相对路径
		String filePath = request.getParameter("filePath");
		if(filePath==null)
			return;
		
		//转化成绝对路径
		filePath = request.getRealPath(filePath);
		
		//生成二进制流
		try{
			InputStream in = new FileInputStream(filePath);
			byte[] b = new byte[1024];
			int i = 0;
			while((i = in.read(b)) > 0){
				response.getOutputStream().write(b,0,i);
			} 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
