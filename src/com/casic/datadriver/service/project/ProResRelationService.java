package com.casic.datadriver.service.project;

import com.casic.datadriver.dao.project.ProResRelationDao;

import com.casic.datadriver.model.project.ProResRelation;


import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by blue on 2017/2/6.
 */
@Service
public class ProResRelationService extends BaseService<ProResRelation> {


    /** The ProResRelation dao. */
    @Resource
    private ProResRelationDao proResRelationDao;





    public boolean addProResRelationDao(ProResRelation proResRelation) {
        this.proResRelationDao.add(proResRelation);
        return true;
    }


    protected IEntityDao<ProResRelation, Long> getEntityDao() {
        return this.proResRelationDao;
    }



}
