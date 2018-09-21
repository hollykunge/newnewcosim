package com.hotent.platform.service.document;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;


@Service
public class DocumentService  extends BaseService
{
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public void publishFlowById(String businessKey,String defId)
	{
		Long id=UniqueIdUtil.genId();
		String insertSql="Insert into w_receipt(id,F_ARCHIVERID,F_ARCHIVERNO,F_ARCHIVERTYPE,F_ARCHIVERNAME,F_DISPATCHUNIT,";
		insertSql+="F_SOURCES,F_SUBJECTS,F_URGENTLEVEL,F_PRIVACYLEVEL,F_SHORTCONTENT,F_FILENUM,DEFID_,F_ARCHIVERATTACH,";
		insertSql+="F_ARCHIVERCONTENT) select "+id+",F_ARCHIVERID,F_ARCHIVERNO,F_ARCHIVERTYPE,F_ARCHIVERNAME,F_DISPATCHUNIT,";
		insertSql+="F_SOURCES,F_SUBJECTS,F_URGENTLEVEL,F_PRIVACYLEVEL,F_SHORTCONTENT,F_FILENUM,"+defId+",F_ARCHIVERATTACH, ";
		insertSql+="F_ARCHIVERCONTENT from W_DISPATCH where id="+businessKey;
		jdbcTemplate.execute(insertSql);
	}

	@Override
	protected IEntityDao getEntityDao()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
