package com.casic.datadriver.dao.project;

import com.casic.datadriver.model.project.ProResRelation;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by blue on 2017/2/6.
 */
@Repository
public class ProResRelationDao extends BaseDao<ProResRelation>  {



//    /**
//     * ������Ŀ����ID��ȡ��Ŀ�����˹�ϵ�б�
//     *
//     * @param ddProjectResponceRelationId
//     *            the query filter
//     * @return the list
//     */
//    public List<ProResRelation> queryRelationInfoList(long ddProjectResponceRelationId) {
//        return this.getBySqlKey("queryRelationInfoList", ddProjectResponceRelationId);
//    }

    /*

     * (non-Javadoc)
     *
     * @see com.hotent.core.db.GenericDao#getEntityClass()
     */
    @Override
    public Class<?> getEntityClass() {
        return ProResRelation.class;
    }



}
