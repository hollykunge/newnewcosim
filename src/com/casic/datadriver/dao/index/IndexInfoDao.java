package com.casic.datadriver.dao.index;

import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.model.index.IndexInfo;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndexInfoDao extends BaseDao <IndexInfo> {

    @Override
    public Class getEntityClass() {
        // TODO Auto-generated method stub
        return IndexInfo.class;
    }

    public List<IndexInfo> getByProjectId(long projectId){
        return this.getBySqlKey("getIndexInfoList", projectId);
    }

    public List<IndexInfo> getByProjectIdF(PageInfo pageInfo){
        return this.getBySqlKey("getByProjectIdF", pageInfo);
    }
}