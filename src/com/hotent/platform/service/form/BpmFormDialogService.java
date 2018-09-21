package com.hotent.platform.service.form;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.impl.Page;
import org.springframework.stereotype.Service;

import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.customertable.DialectFactoryBean;
import com.hotent.core.customertable.SqlTypeConst;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.db.JdbcHelper;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.DB2Dialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;
import com.hotent.core.mybatis.dialect.OracleDialect;
import com.hotent.core.mybatis.dialect.SQLServer2005Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.model.form.BpmFormDialog;
import com.hotent.platform.model.form.BpmFormTemplate;
import com.hotent.platform.model.form.DialogField;
import com.hotent.platform.model.system.SysDataSource;
import com.hotent.platform.service.util.ServiceUtil;
import com.hotent.platform.dao.form.BpmFormDialogDao;
import com.hotent.platform.dao.system.SysDataSourceDao;

/**
 * 对象功能:通用表单对话框 Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2012-06-25 11:05:09
 */
@Service
public class BpmFormDialogService extends BaseService<BpmFormDialog>
{
	@Resource
	private BpmFormDialogDao dao;
	

	
	
	@Resource
	private GroovyScriptEngine  groovyScriptEngine;
	
	
	public BpmFormDialogService()
	{
	}
	
	@Override
	protected IEntityDao<BpmFormDialog, Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 检查模板别名是否唯一
	 * @param alias
	 * @return
	 */
	public boolean isExistAlias(String alias){
		return dao.isExistAlias(alias)>0;
	}
	
	/**
	 * 检查模板别名是否唯一。
	 * @param alias
	 * @return
	 */
	public boolean isExistAliasForUpd(Long id, String alias){
		return dao.isExistAliasForUpd(id,alias)>0;
	}
	
	
	/**
	 * 根据别名获取对话框对象。
	 * @param alias
	 * @return
	 */
	public BpmFormDialog getByAlias(String alias){
		return dao.getByAlias(alias);
	}
	
	/**
	 * 返回树型结构的数据。
	 * @param alias
	 * @return
	 * @throws Exception
	 */
	public List getTreeData(String alias) throws Exception{
		BpmFormDialog bpmFormDialog= dao.getByAlias(alias);
		JdbcHelper jdbcHelper= ServiceUtil.getJdbcHelper(bpmFormDialog.getDsalias());
		String sql=getTreeSql(bpmFormDialog);
		List list=jdbcHelper.queryForList(sql,null);
		
		return list;
	}
	
	
	/**
	 * 根据别名获取对应对话框的数据。
	 * @param alias		对话框别名。
	 * @param params	参数集合。
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public BpmFormDialog getData(String alias,Map<String, Object> params) throws Exception{
		BpmFormDialog bpmFormDialog= dao.getByAlias(alias);
		JdbcHelper jdbcHelper= ServiceUtil.getJdbcHelper(bpmFormDialog.getDsalias());
		
		List<DialogField> displayList=bpmFormDialog.getDisplayList();
		List<DialogField> conditionList=bpmFormDialog.getConditionList();
		String  objectName=bpmFormDialog.getObjname();
		
		//是否是列表
		if(bpmFormDialog.getStyle()==0){
			//是否需要分页。
			if(bpmFormDialog.getNeedpage()==1){
				int currentPage=1;
				Object pageObj=params.get(BpmFormDialog.Page);
				if(pageObj!=null){
					currentPage=Integer.parseInt( params.get(BpmFormDialog.Page).toString());
				}
				int pageSize=bpmFormDialog.getPagesize();
				Object pageSizeObj=params.get(BpmFormDialog.PageSize);
				if(pageSizeObj!=null){
					pageSize=Integer.parseInt( params.get(BpmFormDialog.PageSize).toString());
				}
				String sql = ServiceUtil.getSql(objectName,null, displayList, conditionList, params);
				
				PageBean pageBean=new PageBean(currentPage,pageSize);
				
				List list= jdbcHelper.getPage(currentPage, pageSize, sql, params,pageBean);
				
				bpmFormDialog.setList(list);
				bpmFormDialog.setPageBean(pageBean);
			}
			else{
				String sql = ServiceUtil.getSql(objectName, null,displayList, conditionList, params);
				List<Map<String, Object>> list=jdbcHelper.queryForList(sql, params);
				bpmFormDialog.setList(list);
			}
		}

		
		return bpmFormDialog;
		
	}
	
	
	
	/**
	 *  {"id":"id","pid":"fatherId","displayName":"name"}
	 * @param displayField
	 * @param objName
	 * @return
	 */
	private String getTreeSql(BpmFormDialog bpmFormDialog){

		String objName=bpmFormDialog.getObjname();
		List<DialogField> conditionList=bpmFormDialog.getConditionList();
		Map<String,Object> params=new HashMap<String, Object>();
		//获取条件的SQL语句
		String sqlWhere=ServiceUtil.getWhere(conditionList, params);

		String sqlSelect = getSelectSQl(bpmFormDialog);
		String sql="SELECT "+sqlSelect +" FROM " + objName + sqlWhere;
		
		return sql;
	}
	
	
	
	/**
	 * 从DisplayField和ReturnField中取得Select字段，用于拼接SQL语句
	 * @param bpmFormDialog
	 * @return
	 */
	private String getSelectSQl(BpmFormDialog bpmFormDialog) {
		String displayField=bpmFormDialog.getDisplayfield();
		JSONObject jsonObj=JSONObject.fromObject(displayField);
		String id=jsonObj.getString("id");
		String pid=jsonObj.getString("pid");
		String displayName=jsonObj.getString("displayName");
		List<DialogField> returnFields=bpmFormDialog.getReturnList();
		String sqlSelect= id +","+ pid +"," +displayName;
		for(DialogField field:returnFields){
			String name = field.getFieldName();
			if(name.equalsIgnoreCase(id)||name.equalsIgnoreCase(pid)||name.equalsIgnoreCase(displayName)){
				continue;
			}
			sqlSelect+=","+name;
		}
		return sqlSelect;
	}

	
}
