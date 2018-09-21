package com.casic.cloud.service.config.materialCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.casic.cloud.model.config.materialCatalog.MaterialCatalog;
import com.casic.cloud.dao.config.materialCatalog.MaterialCatalogDao;

/**
 *<pre>
 * 对象功能:cloud_material_catalog Service类
 * 开发公司:中国航天科工集团
 * 开发人员:hollykunge
 * 创建时间:2013-06-17 11:41:35
 *</pre>
 */
@Service
public class MaterialCatalogService extends BaseService<MaterialCatalog>
{
	@Resource
	private MaterialCatalogDao dao;
	
	
	
	public MaterialCatalogService()
	{
	}
	
	@Override
	protected IEntityDao<MaterialCatalog, Long> getEntityDao() 
	{
		return dao;
	}
	public List getAllChildByParentId(long parentId)
	{
		List ChildList = dao.getChildByParentId(parentId);
		Properties prop = (Properties)AppUtil.getBean("configproperties");
		int level = Integer.parseInt(prop.getProperty("posExpandLevel", "0"));
		int childSize = ChildList.size();
		if (level == 0)
		{
			for (int i = 0; i < childSize; i++)
				//if (((MaterialCatalog)ChildList.get(i)).getIsLeaf().intValue() != 1)
				{
					List MoreList = getAllChildByParentId(((MaterialCatalog)ChildList.get(i)).getId().longValue());
					ChildList.addAll(MoreList);
				}

		}
		if (level > 1)
		{
			level--;
			for (int i = 0; i < childSize; i++)
				//if (((MaterialCatalog)ChildList.get(i)).getIsLeaf().intValue() != 1)
				{
					List MoreList = getAllChildByParentId(((MaterialCatalog)ChildList.get(i)).getId(), level);
					ChildList.addAll(MoreList);
				}

		}
		return ChildList;
	}
	
	
	public MaterialCatalog getParentPositionByParentId(long parentId)
	{
		MaterialCatalog parent = (MaterialCatalog)dao.getById(Long.valueOf(parentId));
		if (parentId == 0L || parent == null)
		{
			parent = new MaterialCatalog();
			parent.setId(Long.valueOf(0L));
			parent.setParentId(Long.valueOf(-1L));
			parent.setName("物品分类");
			return parent;
		} else
		{
			return parent;
		}
	}
	
	public List getChildByParentId(long parentId)
	{
		return dao.getChildByParentId(parentId);
	}
	
	private List getAllChildByParentId(Long parentId, int level)
	{
		List<MaterialCatalog> ChildList = new ArrayList<MaterialCatalog>();
		if (level > 0)
		{
			ChildList = dao.getChildByParentId(parentId.longValue());
			level--;
			int childSize = ChildList.size();
			for (int i = 0; i < childSize; i++)
				//if (((MaterialCatalog)ChildList.get(i)).getIsLeaf().intValue() != 1)
				{
					List MoreList = getAllChildByParentId(((MaterialCatalog)ChildList.get(i)).getId(), level);
					ChildList.addAll(MoreList);
				}

		}
		return ChildList;
	}
}
