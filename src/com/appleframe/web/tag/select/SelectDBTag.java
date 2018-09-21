package com.appleframe.web.tag.select;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.util.AppUtil;

/**
 * 从数据库中直接读取成select,
 * 前提是必须配置数据库模板
 */
@SuppressWarnings("unchecked")
public class SelectDBTag extends SelectTag{
	
	private static final long serialVersionUID = 141243768956987958L;
	
	/** 数据库对应的表名 **/
	private String table;
	
	/** 数据库对应的where语句 **/
	private String where;
	
	/** 对应的Option的value值 **/
	private String optionValue;
	
	/** 对应的Option的text值 **/
	private String optionText;

	private JdbcTemplate jdbcTemplate;
	
	public int doStartTag() throws JspException{
		jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
		return EVAL_BODY_BUFFERED;
	}
	
	public int doEndTag() throws JspException{
		try {
			Select select = new Select(this);
			
			String sql = "select * from " + table + " where " + where;
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
			
			//默认显示值
			if(this.getDefaultText() != null && this.getDefaultValue() != null){
				Option o = new Option(this.getDefaultValue(), this.getDefaultText());
				select.addOption(o);
			}
			for(Map m : list){
				Option o = new Option(m.get(optionValue).toString(),m.get(optionText).toString());
				o.setSelectedValue(this.getSelectedValue());
				o.setSelectedText(this.getSelectedText());
				
				select.addOption(o);
			}
			pageContext.getOut().println(select.show());
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return EVAL_PAGE;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
}
