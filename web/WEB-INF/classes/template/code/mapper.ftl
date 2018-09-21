<#assign tableName='W_'+table.tableName?upper_case >
<#assign package=table.variable.package>
<#assign class=table.variable.class>
<#assign fieldList=table.fieldList>
<#assign type="com.hotent."+system+".model."+package+"." +class>
<#function getJdbcType dataType>
<#assign dbtype=dataType?lower_case>
<#assign rtn>
<#if  dbtype=="number" >
NUMERIC
<#elseif (dbtype?index_of("char")>-1)  >
VARCHAR
<#elseif (dbtype=="date")>
DATE
<#elseif (dbtype?ends_with("clob")) >
CLOB
</#if></#assign>
 <#return rtn?trim>
</#function>


<#-- 模板开始  -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd"> 
<mapper namespace="${type}">
	<resultMap id="${class}" type="${type}">
		<id property="id" column="ID" jdbcType="NUMERIC"/>
		<result property="curentUserId" column="CURENTUSERID_" jdbcType="NUMERIC"/>
		<result property="curentOrgId" column="CURENTORGID_" jdbcType="NUMERIC"/>
		<#if table.isMain==1>
		<result property="flowRunId" column="FLOWRUNID_" jdbcType="NUMERIC"/>
		<result property="defId" column="DEFID_" jdbcType="NUMERIC"/>
		<result property="createTime" column="CREATETIME" jdbcType="TIMESTAMP"/>
		<#else>
		<result property="refId" column="REFID" jdbcType="NUMERIC"/>
		</#if>
		<#list fieldList as field>
		<result property="${field.fieldName}" column="F_${field.fieldName?upper_case}" jdbcType="${getJdbcType(field.fieldType)}"/>
		</#list>
	</resultMap>

	<sql id="columns">
		ID,CURENTUSERID_,CURENTORGID_,<#if table.isMain==1>FLOWRUNID_,DEFID_,CREATETIME<#else>REFID</#if>,<#list fieldList as field>F_${field.fieldName?upper_case}<#if field_has_next>,</#if></#list>
	</sql>
	
	<sql id="dynamicWhere">
		<where>
			<#list fieldList as field>
			<#if (field.fieldType=="varchar")>
			<if test="@Ognl@isNotEmpty(${field.fieldName})"> AND F_${field.fieldName?upper_case}  LIKE '%<#noparse>${</#noparse>${field.fieldName}}%'  </if>
			<#else>
			<#if (field.fieldType=="date")>
			<if test="@Ognl@isNotEmpty(${field.fieldName})"> AND F_${field.fieldName?upper_case}  =<#noparse>#{</#noparse>${field.fieldName}} </if>
			<if test="@Ognl@isNotEmpty(begin${field.fieldName})"> AND F_${field.fieldName?upper_case}  >=<#noparse>#{</#noparse>begin${field.fieldName},jdbcType=DATE} </if>
			<if test="@Ognl@isNotEmpty(end${field.fieldName})"> AND F_${field.fieldName?upper_case} <![CDATA[ <=<#noparse>#{</#noparse>end${field.fieldName},jdbcType=DATE}]]> </if>
			<#else>
			<if test="@Ognl@isNotEmpty(${field.fieldName})"> AND F_${field.fieldName?upper_case}  =<#noparse>#{</#noparse>${field.fieldName}} </if>
			</#if>
			</#if>
			</#list>
		</where>
	</sql>

	<insert id="add" parameterType="${type}">
		INSERT INTO ${tableName}
		(ID,CURENTUSERID_,CURENTORGID_,<#if table.isMain==1>FLOWRUNID_,DEFID_,CREATETIME<#else>REFID</#if>,
		<#list fieldList as field>F_${field.fieldName?upper_case}<#if field_has_next>,</#if></#list>)
		VALUES
		(<#noparse>#{</#noparse>id,jdbcType=NUMERIC},
		<#noparse>#{</#noparse>curentUserId,jdbcType=NUMERIC},
		<#noparse>#{</#noparse>curentOrgId,jdbcType=NUMERIC},
		<#if table.isMain==1>
		<#noparse>#{</#noparse>flowRunId,jdbcType=NUMERIC},
		<#noparse>#{</#noparse>defId,jdbcType=NUMERIC},
		<#noparse>#{</#noparse>createTime,jdbcType=TIMESTAMP},
		<#else>
		<#noparse>#{</#noparse>refId,jdbcType=NUMERIC},
		</#if>
		<#list fieldList as field><#noparse>#{</#noparse>${field.fieldName},jdbcType=${getJdbcType(field.fieldType)}<#noparse>}</#noparse><#if field_has_next>, </#if></#list>)
	</insert>
	
	<delete id="delById" parameterType="java.lang.Long">
		DELETE FROM ${tableName} 
		WHERE
		ID=<#noparse>#{</#noparse>id}
	</delete>
	
	<update id="update" parameterType="${type}">
		UPDATE ${tableName} SET
		CURENTUSERID_=<#noparse>#{</#noparse>curentUserId,jdbcType=NUMERIC},
		CURENTORGID_=<#noparse>#{</#noparse>curentOrgId,jdbcType=NUMERIC},
		<#if table.isMain==1>
		FLOWRUNID_=<#noparse>#{</#noparse>flowRunId,jdbcType=NUMERIC},
		DEFID_=<#noparse>#{</#noparse>defId,jdbcType=NUMERIC},
		CREATETIME=<#noparse>#{</#noparse>createTime,jdbcType=TIMESTAMP},
		<#else>
		REFID=<#noparse>#{</#noparse>refId,jdbcType=NUMERIC},
		<#noparse>#{</#noparse>refId,jdbcType=NUMERIC},
		</#if>
		<#list fieldList as field>
		F_${field.fieldName?upper_case}=<#noparse>#{</#noparse>${field.fieldName},jdbcType=${getJdbcType(field.fieldType)}<#noparse>}</#noparse><#if field_has_next>,</#if>
		</#list>
		WHERE
		ID=<#noparse>#{</#noparse>id}
	</update>
	
	<#if table.isMain!=1>
	<delete id="delByMainId">
	    DELETE FROM ${tableName}
	    WHERE
	    REFID=<#noparse>#{</#noparse>refId}
	</delete>    
	
	<select id="get${class}List" resultMap="${class}">
	    SELECT <include refid="columns"/>
	    FROM ${tableName} 
	    WHERE REFID=<#noparse>#{</#noparse>refId}
	</select>
	</#if>
		    
	<select id="getById" parameterType="java.lang.Long" resultMap="${class}">
		SELECT <include refid="columns"/>
		FROM ${tableName}
		WHERE
		ID=<#noparse>#{</#noparse>id}
	</select>
	
	<select id="getAll" resultMap="${class}">
		SELECT <include refid="columns"/>
		FROM ${tableName}   
		<include refid="dynamicWhere" />
		<if test="@Ognl@isNotEmpty(orderField)">
		order by <#noparse>${orderField}</#noparse> <#noparse>${orderSeq}</#noparse>
		</if>
		<if test="@Ognl@isEmpty(orderField)">
		order by ID  desc
		</if>
	</select>
</mapper>
