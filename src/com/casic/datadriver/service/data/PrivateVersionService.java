package com.casic.datadriver.service.data;

import com.casic.datadriver.dao.data.PrivateVersionDao;
import com.casic.datadriver.model.data.PrivateVersion;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 忠 on 2017/5/17.
 */
@Service
public class PrivateVersionService extends BaseService<PrivateVersion> {

    @Resource
    private PrivateVersionDao privateVersionDao;
    @Override
    protected IEntityDao<PrivateVersion, Long> getEntityDao() {
        return this.privateVersionDao;
    }
    /**
     * 添加一个数据
     */
    public void addPrivateVer(PrivateVersion privateVersion) {
        this.privateVersionDao.addPrivateVer(privateVersion);
    }

    /**
     * 根据版本ID查找私有数据
     */
    public String getListByVerId(Long varsionId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        List<PrivateVersion> privateVersionlist = privateVersionDao.getListByVerId(varsionId);
        for (int i = 0; i < privateVersionlist.size(); i++) {
            PrivateVersion privateVersion = privateVersionlist.get(i);
            jsonObject.put("ddVersionId", privateVersion.getDdVersionId());
            jsonObject.put("dataId", privateVersion.getDdDataId());
            jsonObject.put("dataName", privateVersion.getDdDataName());
            jsonObject.put("filePath", privateVersion.getDdDataPath());
            jsonObject.put("parentId", privateVersion.getDdDataParentId());
            jsonObject.put("taskId", privateVersion.getDdDataTaskId());
            jsonObject.put("dataType", privateVersion.getDdDataType());
            jsonObject.put("isLeaf", privateVersion.getDdDataIsLeaf());
            jsonObject.put("dataDescription", privateVersion.getDdDataDescription());
            jsonObject.put("publishState", privateVersion.getDdDataPublishState());
            jsonObject.put("orderState", privateVersion.getDdDataOrderState());
            jsonObject.put("submitState", privateVersion.getDdDataIsSubmit());
            jsonObject.put("taskName", privateVersion.getDdDataTaskName());
            jsonObject.put("creator", privateVersion.getDdDataCreator());
            jsonObject.put("createTime", privateVersion.getDdDataCreateTime());
            jsonObject.put("projectId", privateVersion.getDdDataProjId());
            jsonObject.put("creatorId", privateVersion.getDdDataCreatorId());
            jsonObject.put("dataUnit", privateVersion.getDdDataUnit());
            jsonObject.put("dataSenMax", privateVersion.getDdDataSenMax());
            jsonObject.put("dataSenMin", privateVersion.getDdDataSenMin());
            jsonObject.put("dataValue", privateVersion.getDdDataLastestValue());
            jsonMembers.add(jsonObject);
        }
        String jsonstring = jsonMembers.toString();
        return jsonstring;
    }

    /**
     * 根据任务ID查找版本
     */
    public String getListBytaskId(Long PrivateVerId) {
        return null;
    }

    /**
     * 根据任务ID删除数据
     */
    public void deleteByTaskId(Long taskId) {
        this.privateVersionDao.deleteByTaskId(taskId);
    }
}
