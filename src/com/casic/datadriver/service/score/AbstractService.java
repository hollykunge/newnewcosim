package com.casic.datadriver.service.score;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;

/**
 * 所有的业务实现类均需要从此类继承
 *
 * @Author: workhub
 */

public abstract class AbstractService<E, PK extends Serializable> {

    protected Logger log = LoggerFactory.getLogger(AbstractService.class);

    /**
     * 需要被子类覆盖
     *
     * @return dao层句柄
     */
    protected abstract IEntityDao<E, PK> getEntityDao();

    /**
     * 添加对象
     */
    public void add(E entity) {
        getEntityDao().add(entity);
    }

    /**
     * 根据主键删除对象
     */
    public void delById(PK id) {
        getEntityDao().delById(id);
    }

    /**
     * 根据主键批量删除对象
     */
    public void delByIds(PK[] ids) {
        if (BeanUtils.isEmpty(ids)) {
            return;
        }
        for (PK p : ids) {
            delById(p);
        }
    }

    /**
     * 修改对象
     */
    public void update(E entity) {
        getEntityDao().update(entity);
    }

    /**
     * 根据主键Id获取对象
     */
    @SuppressWarnings("unchecked")
    public E getById(PK id) {
        return (E) getEntityDao().getById(id);
    }

    /**
     * 取得分页
     */
    public List<E> getList(String statementName, PageBean pb) {
        return getEntityDao().getList(statementName, pb);
    }

    /**
     * 返回所有记录
     */
    public List<E> getAll() {
        return getEntityDao().getAll();
    }

    /**
     * 按过滤器查询记录列表
     */
    public List<E> getAll(QueryFilter queryFilter) {
        return getEntityDao().getAll(queryFilter);
    }
}