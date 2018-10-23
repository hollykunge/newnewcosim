package com.casic.datadriver.dao.data;

import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.publicClass.QueryParameters;
import com.casic.datadriver.model.data.OrderDataRelation;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class OrderDataRelationDao.
 */
@Repository
public class OrderDataRelationDao extends BaseDao<OrderDataRelation> {

    /**
     * Query OrderDataRelation basic info list.
     * <p>
     * the query filter
     *
     * @return the list
     */
    public List<OrderDataRelation> getCanBeOrderDataList(long projectId) {
        return this.getBySqlKey("getCanBeOrderDataList", projectId);
    }

    public List<OrderDataRelation> getOrderDataRelationbyDataId(Long DataId) {
        return this.getBySqlKey("getOrderDataRelationbyDataId", DataId);
    }

    public void delDDOrderDataRelation(QueryParameters queryparameters) {
        this.getBySqlKey("delDDOrderDataRelation", queryparameters);
    }

    public void delDDPublishDataRelation(QueryParameters queryparameters) {
        this.getBySqlKey("delDDPublishDataRelation", queryparameters);
    }

    public List<OrderDataRelation> getDDOrderDataRelation(QueryParameters queryparameters) {
        return this.getBySqlKey("getDDOrderDataRelation", queryparameters);
    }

    /**
     * Query OrderDataRelation basic info list.
     * <p>
     * the query filter
     *
     * @return the list
     */
    public List<OrderDataRelation> getOrderDataRelationList(Long id) {
        return this.getBySqlKey("getOrderDataRelationList", id);
    }

    public List<OrderDataRelation> getOrderDataRelationListF(PageInfo model) {
        return this.getBySqlKey("getOrderDataRelationListF", model);
    }


    /**
     * Query OrderDataRelation basic info list.
     *
     * @param ddtaskId the query filter
     * @return the list
     */
    public List<OrderDataRelation> getPublishDataRelationList(Long ddtaskId) {
        return this.getBySqlKey("getPublishDataRelationList", ddtaskId);
    }

    public List<OrderDataRelation> getPublishDataRelationListF(PageInfo model) {
        return this.getBySqlKey("getPublishDataRelationListF", model);
    }

    public List<OrderDataRelation> queryPublishDataRelationByddTaskIDF(PageInfo model) {
        return this.getBySqlKey("queryPublishDataRelationByddTaskIDF", model);
    }

    public List<OrderDataRelation> queryOrderDataRelationByddTaskIDF(PageInfo model) {
        return this.getBySqlKey("queryOrderDataRelationByddTaskIDF", model);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.db.GenericDao#getEntityClass()
     */
    @Override
    public Class<?> getEntityClass() {
        return OrderDataRelation.class;
    }

    public void delPublishByddDataTaskId(Long ddDataTaskId) {
        this.getBySqlKey("delPublishByddDataTaskId", ddDataTaskId);
    }


    public void delOrderByddDataTaskId(Long ddDataTaskId) {
        this.getBySqlKey("delOrderByddDataTaskId", ddDataTaskId);
    }

    public OrderDataRelation getOrderDataRelationById(Long id) {
        return this.getUnique("getOrderDataRelationById", id);
    }

    public List<OrderDataRelation> getBeOrderDataByDataId(Long dataId) {
        return this.getBySqlKey("getBeOrderDataByDataId", dataId);
    }

    public void delOrderByddDataId(Long dataId) {
        this.delBySqlKey("delOrderByddDataId", dataId);
    }

    public void delPublishByddDataId(Long dataId) {
        this.getBySqlKey("delPublishByddDataId", dataId);
    }

    public void addToOrder(List<OrderDataRelation> orderDataRelationList) {
        this.getBySqlKey("addToOrder", orderDataRelationList);
    }

}
