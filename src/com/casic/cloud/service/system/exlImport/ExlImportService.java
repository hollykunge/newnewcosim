package com.casic.cloud.service.system.exlImport;

import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.appleframe.utils.excel.ExcelImportor;
import com.casic.cloud.model.system.news.News;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

/**
 *<pre>
 * 对象功能:AUTH_NEWS Service类
 * 开发公司:
 * 开发人员:ray
 * 创建时间:2013-04-12 21:43:57
 *</pre>
 */
@Service
public class ExlImportService
{
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public ExlImportService(){
	}
	
	public String importExlWithTransaction(InputStream in, int sheetNum, int headRowNum, String table,String primaryKey){
		ExcelImportor importor = new ExcelImportor();
		return importor.importExl(in, sheetNum, jdbcTemplate, table, primaryKey);
	}
}