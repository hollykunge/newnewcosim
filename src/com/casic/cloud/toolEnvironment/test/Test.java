package com.casic.cloud.toolEnvironment.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.servlet.ServletContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

import com.casic.cloud.toolEnvironment.DB.HibernateUtil;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_inputFile;
import com.casic.cloud.toolEnvironment.bean.Cloud_tool_user_parasmapper;
import com.casic.cloud.toolEnvironment.communication.CommServer;
import com.casic.cloud.toolEnvironment.util.FileUtil;
import com.casic.cloud.toolEnvironment.util.LinuxUtil;

public class Test {

	public static void main(String[] args) {

		// 插入数据
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		SQLQuery sqlQuery = session
//				.createSQLQuery("select * from Cloud_tool_user_parasmapper where cloud_tool_user_id = 10000021140243");
//		sqlQuery.addEntity(Cloud_tool_user_parasmapper.class);
//		List<Cloud_tool_user_parasmapper> paras = sqlQuery.list();
//		
//		for (Cloud_tool_user_parasmapper para : paras) {
//			Cloud_tool_user_parasmapper cloud_tool_user_parasmapper = new Cloud_tool_user_parasmapper();
//			cloud_tool_user_parasmapper.setChinese_mapper_name(para
//					.getChinese_mapper_name());
//			cloud_tool_user_parasmapper
//					.setCloud_tool_user((Cloud_tool_user) session.get(
//							Cloud_tool_user.class, new Long("10000021140238")));
//			cloud_tool_user_parasmapper.setForm_mapper_name(para
//					.getForm_mapper_name());
//			cloud_tool_user_parasmapper.setName(para.getName());
//			cloud_tool_user_parasmapper.setType(para.getType());
//			session.save(cloud_tool_user_parasmapper);
//		}
//
//		session.getTransaction().commit();
		// try {
		// Class.forName("com.casic.cloud.toolEnvironment.communication.CommServer");
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// //
		
		
	}

}
