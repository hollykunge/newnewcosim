package com.casic.datadriver.dao.project;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.casic.datadriver.model.project.Project;
import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;

/**
 * The Class ProjectDao.
 */
@Repository
public class ProjectDao extends BaseDao<Project> {

	/**
	 * 根据用户Id获取项目列表
	 *
	 * @param userId
	 *            the query filter
	 * @return the list
	 */
	public List<Project> queryProjectBasicInfoList(long userId) {
		return this.getBySqlKey("getProjectListbyUserId", userId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hotent.core.db.GenericDao#getEntityClass()
	 */
	@Override
	public Class<?> getEntityClass() {
		return Project.class;
	}


	/**
	 * 20170107
	 */
	public List<Project> queryProjectlistByRes(Long ddProjectResponsiblePersonId) {
		return this.getBySqlKey("queryProjectlistByRes", ddProjectResponsiblePersonId);
	}

	public List<Project> getByUserId(Long userId){
		return this.getBySqlKey("getProjectListbyUserId", userId);
	}
}
