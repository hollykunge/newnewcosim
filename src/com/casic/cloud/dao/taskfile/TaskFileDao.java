package com.casic.cloud.dao.taskfile;

import org.springframework.stereotype.Repository;

import com.casic.cloud.model.taskfile.TaskFile;
import com.hotent.core.db.BaseDao;

/**
 * @author FangYun
 * 
 */
@Repository
public class TaskFileDao extends BaseDao<TaskFile> {

	@Override
	public Class<?> getEntityClass() {
		return TaskFile.class;
	}
}
