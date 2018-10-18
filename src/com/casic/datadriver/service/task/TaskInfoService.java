package com.casic.datadriver.service.task;

import com.casic.datadriver.dao.data.PrivateDataDao;
import com.casic.datadriver.dao.task.ProTaskDependanceDao;
import com.casic.datadriver.dao.task.TaskInfoDao;
import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.model.task.TaskStart;
import com.casic.datadriver.publicClass.PageInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The Class TaskInfoService.
 */
@Service
public class TaskInfoService extends BaseService<TaskInfo> {

    /**
     * The task dao.
     */
    @Resource
    private TaskInfoDao taskInfoDao;

    @Resource
    private PrivateDataDao privateDataDao;

    @Resource
    private ProTaskDependanceDao proTaskDependanceDao;

    @Resource
    private TaskStartService taskStartService;

    @Resource
    private SysUserService sysUserService;

    /**
     * Adds the DD taskInfo.
     *
     * @param taskInfo the taskInfo
     * @return true, if successful
     */
    public boolean addDDTask(TaskInfo taskInfo) throws Exception {
        this.taskInfoDao.add(taskInfo);
        addSubList(taskInfo);
        return true;
    }

    public boolean updateDDTask(TaskInfo taskInfo) throws Exception {
        update(taskInfo);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hotent.core.service.GenericService#getEntityDao()
     */
    @Override
    protected IEntityDao<TaskInfo, Long> getEntityDao() {
        return this.taskInfoDao;
    }

    /**
     * Query task basic info list.
     * <p>
     * the query filter
     *
     * @return the list
     */
    public List<TaskInfo> queryTaskInfoByProjectId(long ProjectId) {
        return this.taskInfoDao.queryTaskInfoByProjectId(ProjectId);
    }

    /**
     * 获取任务负责人ID
     * <p>
     * the query filter
     *
     * @return the list
     */
    public TaskInfo getUserIdbyTaskId(long taskId) {
        return this.taskInfoDao.getById(taskId);
    }

    /**
     * Query task basic info list.
     * <p>
     * the query filter
     *
     * @return the list
     */
    public List<TaskInfo> getListByResponceId(long ResponceId) {
        return this.taskInfoDao.getListByResponceId(ResponceId);
    }

    public List<TaskInfo> getListByResponceIdAndState1(long ResponceId) {
        return this.taskInfoDao.getListByResponceIdAndState1(ResponceId);
    }
    /**
     * 根据任务信息添加私有数据
     */
    public void addSubList(TaskInfo taskInfo) throws Exception {
        List<PrivateData> privateDataList = taskInfo.getPrivateDataList();
        if (BeanUtils.isNotEmpty(privateDataList)) {
            for (PrivateData privateData : privateDataList) {
                privateData.setDdDataTaskId(taskInfo.getDdTaskId());
                privateData.setDdDataId(UniqueIdUtil.genId());
                privateDataDao.add(privateData);
            }
        }
    }

    public List<PrivateData> getPrivateDataList(Long taskId) {
        return this.privateDataDao.getDataListByTaskId(taskId);
    }

    /**
     * 删除关系表
     */
    private void delByPk(Long id) {
        proTaskDependanceDao.delByTaskId(id);
    }

    /**
     * 删除任务和关系表
     */
    public void delAll(Long[] lAryId) {
        for (Long id : lAryId) {
            delByPk(id);
            taskInfoDao.delById(id);
        }
    }

    public List<TaskInfo> getByProIdAndUserIdF(PageInfo pageInfo) {
        return this.taskInfoDao.getByProIdAndUserIdF(pageInfo);
    }

    public TaskInfo onChangeTaskInfo(Long taskId, String taskInfoJson, Integer eventId) throws ParseException {

//        String obj = JSONObject.fromObject(taskInfoJson);
//        Iterator<String> sIterator = obj.keys();
//        String key = sIterator.next();
        TaskStart taskStart = taskStartService.getByTaskId(taskId);
        TaskInfo taskInfo = getById(taskId);
        switch (eventId) {
            case 0:
//                long temp0 = obj.getLong("0");
                if (taskStart != null) {
                    taskStart.setDdTaskResponcePerson(Long.valueOf(taskInfoJson));
                    taskStartService.update(taskStart);
                }
                taskInfo.setDdTaskResponsiblePerson(Long.valueOf(taskInfoJson));
                ISysUser sysUser = sysUserService.getById(Long.valueOf(taskInfoJson));
                taskInfo.setDdTaskPerson(sysUser.getFullname());
                break;
            case 1:
//                long temp1 = obj.getLong("1");
                taskInfo.setDdTaskPriority(Short.valueOf(taskInfoJson));
                return taskInfo;

            case 2:
//                String temp2 = obj.getString("2");
//                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//                Date parsedDate = fmt.parse(taskInfoJson);
//                String dateString = fmt.format(parsedDate);
                taskInfo.setDdTaskPlanEndTime(taskInfoJson);
                Integer i=taskInfo.getDdTaskPlanStartTime().compareTo(taskInfoJson);
                if(i>0){
                    return null;
                }else {
                    return taskInfo;
                }

            case 3:
//                String temp3 = obj.getString("3");
                taskInfo.setDdTaskDescription(taskInfoJson);
                return taskInfo;

            case 4:
//                String temp2 = obj.getString("2");
//                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//                Date parsedDate = fmt.parse(taskInfoJson);
//                String dateString = fmt.format(parsedDate);
                taskInfo.setDdTaskPlanStartTime(taskInfoJson);
                Integer j=taskInfo.getDdTaskPlanEndTime().compareTo(taskInfoJson);
                if(j<0){
                    return null;
                }else {
                    return taskInfo;
                }
        }
        return null;
    }

    public List<TaskInfo> getListByPriority(Long projectId) {
        List<TaskInfo> taskInfos = taskInfoDao.getListByPriority(projectId);
        return taskInfos;
    }

    /**
     * 任务提交
     */
    public Boolean submitTask(Long taskId) {
        TaskInfo taskInfo = getById(taskId);
        taskInfo.setDdTaskChildType("checkpanel");
        taskInfo.setDdTaskState((short) 2);
        int record = this.taskInfoDao.update(taskInfo);
        if (record != 0)
            return true;
        else
            return false;
    }
    /**
     * 任务收回
     */
    public Boolean recycleTask(Long taskId) {
        TaskInfo taskInfo = getById(taskId);
        taskInfo.setDdTaskChildType("publishpanel");
        taskInfo.setDdTaskState((short) 1);
        int record = this.taskInfoDao.update(taskInfo);
        if (record != 0)
            return true;
        else
            return false;
    }

}
