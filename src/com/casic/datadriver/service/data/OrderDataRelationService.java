package com.casic.datadriver.service.data;

import com.casic.datadriver.dao.data.OrderDataRelationDao;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.publicClass.QueryParameters;
import com.casic.datadriver.model.data.OrderDataRelation;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * The Class OrderDataRelationService.
 */
@Service
public class OrderDataRelationService extends BaseService<OrderDataRelation> {

    /** The OrderDataRelation dao. */
    @Resource
    private OrderDataRelationDao orderDataRelationDao;

    /**
     * Adds the DD OrderDataRelation.
     *
     * @param OrderDataRelation
     *            the OrderDataRelation
     * @return true, if successful
     */
    public boolean addDDOrderDataRelation(OrderDataRelation OrderDataRelation) {
        this.orderDataRelationDao.add(OrderDataRelation);
        return true;
    }

    public void delDDOrderDataRelation(QueryParameters queryparameters) {
        this.orderDataRelationDao.delDDOrderDataRelation(queryparameters);
    }
    public void delDDPublishDataRelation(QueryParameters queryparameters) {
        this.orderDataRelationDao.delDDPublishDataRelation(queryparameters);
    }

    public  List<OrderDataRelation> getDDOrderDataRelation(QueryParameters queryparameters) {
      return this.orderDataRelationDao.getDDOrderDataRelation(queryparameters);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hotent.core.service.GenericService#getEntityDao()
     */
    @Override
    protected IEntityDao<OrderDataRelation, Long> getEntityDao() {
        return this.orderDataRelationDao;
    }

    /**
     * Query OrderDataRelation basic info list.
     *
     *            the query filter
     * @return the list
     */
    public List<OrderDataRelation> getCanBeOrderDataList(long project) {
        return this.orderDataRelationDao.getCanBeOrderDataList(project);
    }

    public List<OrderDataRelation> getOrderDataRelationbyDataId(Long DataId) {
        return this.orderDataRelationDao.getOrderDataRelationbyDataId(DataId);
    }

    /**
     * Query OrderDataRelation basic info list.
     *
     *            the query filter
     * @return the list
     */
    public List<OrderDataRelation> getOrderDataRelationList(Long id) {
        return this.orderDataRelationDao.getOrderDataRelationList(id);
    }

    public List<OrderDataRelation> getOrderDataRelationListF(PageInfo model) {
        return this.orderDataRelationDao.getOrderDataRelationListF(model);
    }

    public OrderDataRelation getOrderDataRelationById(Long id) {
        return this.orderDataRelationDao.getOrderDataRelationById(id);
    }

    /**
     * Query OrderDataRelation basic info list.
     *
     * @param ddtaskId
     *            the query filter
     * @return the list
     */
    public List<OrderDataRelation> getPublishDataRelationList(Long ddtaskId) {
        return this.orderDataRelationDao.getPublishDataRelationList(ddtaskId);
    }
    public List<OrderDataRelation> getPublishDataRelationListF(PageInfo model) {
        return this.orderDataRelationDao.getPublishDataRelationListF(model);
    }

    public List<OrderDataRelation> queryPublishDataRelationByddTaskIDF(PageInfo model) {
        return this.orderDataRelationDao.queryPublishDataRelationByddTaskIDF(model);
    }
    public List<OrderDataRelation> queryOrderDataRelationByddTaskIDF(PageInfo model){
        return this.orderDataRelationDao.queryOrderDataRelationByddTaskIDF(model);
    }
    /**
     * Adds the DD OrderDataRelation.
     * @param ddDataTaskId
     *            the OrderDataRelation
     * @return true, if successful
     */
    public void delPublishByddDataTaskId(Long ddDataTaskId) {
        this.orderDataRelationDao.delPublishByddDataTaskId(ddDataTaskId);

    }

    /**
     * Adds the DD OrderDataRelation.
     * @param ddDataTaskId
     *            the OrderDataRelation
     * @return true, if successful
     */
    public void delOrderByddDataTaskId(Long ddDataTaskId) {
        this.orderDataRelationDao.delOrderByddDataTaskId(ddDataTaskId);
    }
    public List<OrderDataRelation> getBeOrderDataByDataId(Long dataId){
        return this.orderDataRelationDao.getBeOrderDataByDataId(dataId);
    }
    public void delOrderByddDataId(Long dataId){
        this.orderDataRelationDao.delOrderByddDataId(dataId);
    }
    public void delPublishByddDataId(Long dataId){
        this.orderDataRelationDao.delPublishByddDataId(dataId);
    }
}
