<#assign package=table.variable.package>
<#assign class=table.variable.class>
<#assign subtables=table.subTableList>
<#assign classVar=table.variable.classVar>
<#assign table=table.subTableList>

package com.hotent.${system}.service.${package};

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;

import com.hotent.core.service.GenericService;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.${system}.model.${package}.${class};
import com.hotent.${system}.dao.${package}.${class}Dao;
<#if subtables?size != 0>
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
	<#list subtables as subtable>
import com.hotent.${system}.model.${subtable.variable.package}.${subtable.variable.class};
import com.hotent.${system}.dao.${subtable.variable.package}.${subtable.variable.class}Dao;
	</#list>
</#if>


@Service
public class ${class}Service extends BaseService<${class}>
{
	@Resource
	private ${class}Dao dao;
	
	<#if subtables?size != 0>
		<#list subtables as subtable>
	@Resource
	private ${subtable.variable.class}Dao ${subtable.variable.classVar}Dao;
		</#list>
	</#if>
	public ${class}Service()
	{
	}
	
	@Override
	protected IEntityDao<${class},Long> getEntityDao() 
	{
		return dao;
	}
	
	<#if subtables?size != 0>
	private void delByPk(Long id){
	    <#list subtables as subtable>
		${subtable.variable.classVar}Dao.delByMainId(id);
	    </#list>
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(${class} ${classVar}) throws Exception{
		add(${classVar});
		addSubList(${classVar});
	}
	
	public void updateAll(${class} ${classVar}) throws Exception{
		update(${classVar});
		delByPk(${classVar}.getId());
		addSubList(${classVar});
	}
	
	public void addSubList(${class} ${classVar}) throws Exception{
	<#list subtables as subtable>
	<#assign vars=subtable.variable>
		List<${vars.class}> ${vars.classVar}List=${classVar}.get${vars.classVar?cap_first}List();
		if(BeanUtils.isNotEmpty(${vars.classVar}List)){
			for(${vars.class} ${vars.classVar}:${vars.classVar}List){
				${vars.classVar}.setRefId(${classVar}.getId());
				Long id=UniqueIdUtil.genId();
				${vars.classVar}.setId(id);
				${vars.classVar}Dao.add(${vars.classVar});
			}
		}
	</#list>
	}
	
	<#list subtables as subtable>
	<#assign vars=subtable.variable>
	public List<${vars.class}> get${vars.classVar?cap_first}List(Long id) {
		return ${vars.classVar}Dao.getByMainId(id);
	}
	</#list>
	
	</#if>
}
