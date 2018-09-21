package com.casic.datadriver.dao.task;

import com.casic.datadriver.model.task.TaskResRelation;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by blue on 2017/2/6.
 */
@Repository
public class TaskResRelationDao extends BaseDao<TaskResRelation> {

    //    /**
//     * ������Ŀ����ID��ȡ��Ŀ�����˹�ϵ�б�
//     *
//     * @param ddTaskResponceRelationId
//     *            the query filter
//     * @return the list
//     */
//    public List<ProResRelation> queryRelationInfoList(long ddTaskResponceRelationId) {
//        return this.getBySqlKey("queryRelationInfoList", ddTaskResponceRelationId);
//    }

    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.db.GenericDao#getEntityClass()
     */

    @Override
    public Class<?> getEntityClass() {
        return TaskResRelation.class;
    }
}

