/**
 * 
 */
package com.casic.cloud.service.taskfile;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.casic.cloud.dao.taskfile.TaskFileDao;
import com.casic.cloud.dao.taskfile.TaskFileNodeDao;
import com.casic.cloud.model.taskfile.TaskFile;
import com.casic.cloud.model.taskfile.TaskFileNode;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;

/**
 * @author FangYun
 * 
 */
@Service
public class TaskFileService extends BaseService<TaskFile> {
	@Resource
	private TaskFileDao taskFileDao;
	@Resource
	private TaskFileNodeDao taskFileNodeDao;

	@Override
	protected IEntityDao<TaskFile, Long> getEntityDao() {
		return taskFileDao;
	}

	public void addTaskFile(TaskFile taskFile, Long setId) {
		super.add(taskFile);
		TaskFileNode tfn = new TaskFileNode();
		tfn.setTaskfileNodeId(UniqueIdUtil.genId());
		tfn.setTaskfileId(taskFile.getFileId());
		tfn.setSetId(setId);
		taskFileNodeDao.add(tfn);
	}
}