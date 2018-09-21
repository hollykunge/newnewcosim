package com.hotent.platform.service.form;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.customertable.ColumnModel;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.platform.dao.form.BpmFormFieldDao;
import com.hotent.platform.model.form.BpmFormField;


/**
 * 对象功能:自定义表字段 Service类 
 * 开发公司:
 * 开发人员:xwy 
 * 创建时间:2011-11-30 15:20:14
 */
@Service
public class BpmFormFieldService extends BaseService<BpmFormField>
{
	@Resource
	private BpmFormFieldDao dao;

	public BpmFormFieldService()
	{
	}

	@Override
	protected IEntityDao<BpmFormField, Long> getEntityDao()
	{
		return dao;
	}

	/**
	 * 通过tableId查找
	 * 
	 * @param tableId 自定义表Id
	 * @return
	 */
	public List<BpmFormField> getByTableId(Long tableId)
	{
		return dao.getByTableId(tableId);
	}
	
	/**
	 * 通过tableId查找所有（包括已删除的）
	 * 
	 * @param tableId 自定义表Id
	 * @return
	 */
	public List<BpmFormField> getAllByTableId(Long tableId)
	{
		return dao.getAllByTableId(tableId);
	}
	
	/**
	 * 根据流程定义ID获取流程变量。
	 * @param defId		流程定义ID
	 * @return
	 */
	public List<BpmFormField> getFlowVarByFlowDefId(Long defId)
	{
		List<BpmFormField> list =dao.getFlowVarByFlowDefId(defId);
		list.addAll(getCommonFields());
		return list;
	}
	
	/**
	 * 获取所有流程默认自带的变量
	 * @return
	 */
	private List<BpmFormField> getCommonFields(){
		List<BpmFormField> list = new ArrayList<BpmFormField>();
		
		BpmFormField startUser=new BpmFormField();
		startUser.setFieldName("startUser");
		startUser.setFieldDesc("发起人ID");
		startUser.setFieldType(ColumnModel.COLUMNTYPE_NUMBER);
		list.add(startUser);
		
		BpmFormField businessKey=new BpmFormField();
		businessKey.setFieldName("businessKey");
		businessKey.setFieldDesc("表单主键");
		businessKey.setFieldType(ColumnModel.COLUMNTYPE_VARCHAR);
		list.add(businessKey);
		
		BpmFormField flowRunId=new BpmFormField();
		flowRunId.setFieldName("flowRunId");
		flowRunId.setFieldDesc("流程运行ID");
		flowRunId.setFieldType(ColumnModel.COLUMNTYPE_INT);
		list.add(flowRunId);
		
		return list;
	}
}
