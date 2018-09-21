package com.casic.datadriver.dao;

import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.model.tool.ToolCenterModel;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 忠 on 2017/1/13.
 */
@Repository
public class ToolCenterDao  extends BaseDao<ToolCenterModel> {

    @Override
    public Class getEntityClass() {
        return ToolCenterModel.class;
    }


    public List<ToolCenterModel> querytoolBymajor(String major) {
        return this.getBySqlKey("querytoolBymajor", major);
    }

    public List<ToolCenterModel> querytoolByname(String toolname) {
           return this.getBySqlKey("querytoolByname", toolname);}

    public List<ToolCenterModel> querytoolBymajorF(PageInfo model) {
        return this.getBySqlKey("querytoolBymajorF", model);}

    public void del(Long id) {
        this.getBySqlKey("deltool", id);}
}
