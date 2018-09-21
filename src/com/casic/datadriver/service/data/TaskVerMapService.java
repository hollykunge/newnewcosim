package com.casic.datadriver.service.data;

import com.casic.datadriver.dao.data.TaskVerMapDao;
import com.casic.datadriver.model.data.TaskVerMap;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.service.task.TaskInfoService;
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
public class TaskVerMapService extends BaseService<TaskVerMap> {

    @Resource
    private TaskVerMapDao taskVerMapDao;
    @Resource
    private TaskInfoService taskInfoService;

    @Override
    protected IEntityDao<TaskVerMap, Long> getEntityDao() {
        return this.taskVerMapDao;
    }

    /**
     * 添加一个数据
     */
    public void addTaskVerMap(TaskVerMap taskVerMap) {
        this.taskVerMapDao.addTaskVerMap(taskVerMap);
    }

    /**
     * 获取任务当前数据版本号
     */
    public Long getVersionNum(Long taskId) {
       return this.taskVerMapDao.getVersionNum(taskId);
    }


    /**
     * 根据任务ID查找版本列表
     */
    public String getListByTaskId(Long taskId) {
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        List<TaskVerMap> taskVerMaplist = taskVerMapDao.getListByTaskId(taskId);
        TaskInfo taskInfo = taskInfoService.getById(taskId);
        for (int i = 0; i < taskVerMaplist.size(); i++) {
            TaskVerMap taskVerMap = taskVerMaplist.get(i);
            jsonObject.put("ddTaskId", taskVerMap.getDdTaskId());
            jsonObject.put("ddTaskName", taskInfo.getDdTaskName());
            jsonObject.put("ddTaskVerId", taskVerMap.getDdTaskVerId());
            jsonObject.put("ddVersionDescription", taskVerMap.getDdVersionDescription());
            jsonObject.put("ddVersionTime", taskVerMap.getDdVersionTime());
            jsonObject.put("ddVersionNum", taskVerMap.getDdVersionNum());
            jsonMembers.add(jsonObject);
        }
//        json.put("total", taskVerMaplist.size());
//        json.put("rows", jsonMembers);
        String jsonstring = jsonMembers.toString();
        return jsonstring;
    }
    /**
     * 根据任务ID批量删除数据
     */
    //TODO:根据任务ID删除数据还没弄好
    public void delVerByTaskId(Long dataId) {
        this.taskVerMapDao.delVerByTaskId(dataId);
    }
}
