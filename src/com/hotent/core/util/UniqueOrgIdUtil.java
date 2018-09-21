package com.hotent.core.util;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.FileUtil;


/**
 * 产生一个唯一ID
 */
public class UniqueOrgIdUtil {
	/**
	 * ID校正（集群部署时的应用服务器编号）
	 */
	private static long adjust = 1;
	/**
	 * ID当前值
	 */
	private static long nextId = 0;
	/**
	 * 当前ID的上限
	 */
	private static long lastId = -1;
	/**
	 * ID校正的范围,10万家企业
	 */
	private static long adjust_scale = 10000L;
	
	private static JdbcTemplate jdbcTemplate;
	
	
	private static void init(){
		jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplateSN");
		String path = FileUtil.getClassesPath() + "conf/app.properties".replace("/", File.separator);
		String strAdjust=FileUtil.readFromProperties(path, "genId.adjust");
		if(strAdjust!=null){
			adjust = Integer.parseInt(strAdjust);
		}
	}

	/**
	 * 获取下一段ID范围，并记录该ID计算机的bound
	 */
	private static  void getNextIdBlock() {
		if(jdbcTemplate==null){
			init();
		}
		Long bound=-1L;
		Integer incremental=-1;
		String sql="SELECT bound,incremental FROM SYS_DB_ORGID T WHERE T.ID=?";
		String upSql="UPDATE SYS_DB_ORGID  SET BOUND=? WHERE ID=?";
		try{
			Map map = jdbcTemplate.queryForMap(sql, new Object[] { adjust });
			bound = Long.parseLong(map.get("bound").toString());
			incremental = Integer.parseInt(map.get("incremental").toString());
			nextId = bound;
			lastId = bound + incremental;
			jdbcTemplate.update(upSql, new Object[] { lastId , adjust });
		}
		catch(org.springframework.dao.EmptyResultDataAccessException e){
			insertNewComputer();
		}
	}
	
	/**
	 * 不存在该计算机编号的则插入一条记录
	 */
	private static void insertNewComputer(){
		try{
			lastId = 10000;
			String sql="INSERT INTO SYS_DB_ID (id,incremental,bound) VALUES("+adjust+",10000,"+lastId+")";
			jdbcTemplate.update(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 产生一个唯一ID。 使用同步，防止重复，测试方法见main方法
	 * 
	 */
	public static synchronized long genId() {
		if (lastId <= nextId) {
			getNextIdBlock();
		}
		long _nextId = nextId++;
		return _nextId + adjust_scale;
	}

	/**
	 * 产生guid
	 * 
	 * @return
	 */
	public static final String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 测试类 使用两个线程进行测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		

		// final Set<Long> set=Collections.synchronizedSet(new HashSet<Long>());
		// //final Set<Long> set=new HashSet<Long>();
		// Thread td1=new Thread(){
		// public void run() {
		// try {
		// int i=0;
		// while(i<10000)
		// {
		// Long id=genId();
		// //Long id=new Long(i);
		// //demo.add(id);
		// set.add(id);
		// i++;
		//
		// }
		// } catch (Exception e) {
		// System.out.println("td1:" + e.getMessage());
		// }
		// }
		// };
		// //
		// Thread td2=new Thread(){
		// public void run() {
		// try {
		// int i=0;
		// while(i<10000)
		// {
		// Long id=genId();
		// //Long id=new Long(i);
		// set.add(id);
		// i++;
		// }
		// } catch (Exception e) {
		// System.out.println("td2:" + e.getMessage());
		// }
		// }
		// };
		// System.out.println("start");
		// long start=System.currentTimeMillis();
		// td1.start();
		// td2.start();
		// td1.join();
		// td2.join();
		// long end=System.currentTimeMillis();
		//
		// System.out.println((end-start) +"," + set.size() +"," + nextValue);

	}
	

}