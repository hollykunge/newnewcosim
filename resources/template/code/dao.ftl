<#assign package=table.variable.package>
<#assign class=table.variable.class>
<#assign classVar=table.variable.classVar>

package com.hotent.${system}.dao.${package};

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hotent.core.db.BaseDao;
<#if table.isMain!=1>
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
</#if>
import com.hotent.${system}.model.${package}.${class};

@Repository
public class ${class}Dao extends BaseDao<${class}>
{
	@Override
	public Class<?> getEntityClass()
	{
		return ${class}.class;
	}

	<#if table.isMain!=1>
	public List<${class}> getByMainId(Long refId) {
		return this.getBySqlKey("get${class}List", refId);
	}
	
	public void delByMainId(Long refId) {
		this.delBySqlKey("delByMainId", refId);
	}
	</#if>	
}