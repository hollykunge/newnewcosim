package com.casic.datadriver.service.task;

import com.casic.datadriver.dao.data.PrivateDataDao;
import com.casic.datadriver.dao.task.ProTaskDependanceDao;
import com.casic.datadriver.dao.task.TaskInfoDao;
import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.model.task.TaskStart;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.publicClass.QueryParameters;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.service.system.SysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

    @Resource
    private SysOrgDao sysOrgDao;

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


    public String getKanbanDataByProjectId(Long projectId) throws Exception {
        List<TaskInfo> allTaskData = taskInfoDao.queryTaskInfoByProjectId(projectId);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonMembers = new JSONArray();

        for (int i = 0; i < allTaskData.size(); i++) {
            TaskInfo taskData = allTaskData.get(i);
            jsonObject.put("taskId", taskData.getDdTaskId());
            jsonObject.put("taskName", taskData.getDdTaskName());
            jsonObject.put("endTime", taskData.getDdTaskPlanEndTime());
            jsonObject.put("master", taskData.getDdTaskPerson());
            switch (taskData.getDdTaskPriority()) {
                case 1:
                    jsonObject.put("color", "gray");
                    break;
                case 2:
                    jsonObject.put("color", "#ec971f");
                    break;
                case 3:
                    jsonObject.put("color", "#c9302c");
                    break;
                default:
                    break;
            }
            switch (taskData.getDdTaskChildType()) {
                case "createpanel":
                    jsonObject.put("state", "createpanel");
                    break;
                case "publishpanel":
                    jsonObject.put("state", "publishpanel");
                    break;
                case "checkpanel":
                    jsonObject.put("state", "checkpanel");
                    break;
                case "completepanel":
                    jsonObject.put("state", "completepanel");
                    break;
                default:
                    break;
            }
            jsonMembers.add(jsonObject);
        }
        return jsonMembers.toString();
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
    public List<TaskInfo> queryTaskInfoByProjectId(Long ProjectId) {
        return this.taskInfoDao.queryTaskInfoByProjectId(ProjectId);
    }

    /**
     * 获取任务负责人ID
     * <p>
     * the query filter
     *
     * @return the list
     */
    public TaskInfo getTaskById(Long taskId) {
        return this.taskInfoDao.getById(taskId);
    }

    /**
     * Query task basic info list.
     * <p>
     * the query filter
     *
     * @return the list
     */
    public List<TaskInfo> getListByResponceId(Long ResponceId) {
        return this.taskInfoDao.getListByResponceId(ResponceId);
    }

    public List<TaskInfo> getListByResponceIdAndState1(Long ResponceId) {
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
        TaskStart taskStart = taskStartService.getByTaskId(taskId);
        TaskInfo taskInfo = getById(taskId);
        switch (eventId) {
            case 0:
                if (taskStart != null) {
                    taskStart.setDdTaskResponcePerson(Long.valueOf(taskInfoJson));
                    taskStartService.update(taskStart);
                }
                taskInfo.setDdTaskResponsiblePerson(Long.valueOf(taskInfoJson));
                ISysUser sysUser = sysUserService.getById(Long.valueOf(taskInfoJson));
                taskInfo.setDdTaskPerson(sysUser.getFullname());
                break;
            case 1:
                taskInfo.setDdTaskPriority(Short.valueOf(taskInfoJson));
                break;
            case 2:
                taskInfo.setDdTaskPlanEndTime(taskInfoJson);
                break;
            case 3:
                taskInfo.setDdTaskDescription(taskInfoJson);
                break;
            case 4:
                taskInfo.setDdTaskPlanStartTime(taskInfoJson);
                break;
            default:
                break;
        }
        return taskInfo;
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
        if (record != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 任务收回
     */
    public Boolean recycleTask(Long taskId) {
        TaskInfo taskInfo = getById(taskId);
        taskInfo.setDdTaskChildType("publishpanel");
        taskInfo.setDdTaskState((short) 1);
        int record = this.taskInfoDao.update(taskInfo);
        if (record != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 该任务是否是本部分项目负责人派发的
     * @param taskId 任务id
     */
    public Boolean isSameDept(Long taskId){

        TaskInfo taskInfo = getById(taskId);

        if(taskInfo != null) {
            Long projectOrg = sysOrgDao.getOrgsByUserId(
                    taskInfo.getDdTaskCreatorId()).get(0).getOrgId();

            Long taskOrg = sysOrgDao.getOrgsByUserId(
                    taskInfo.getDdTaskResponsiblePerson()).get(0).getOrgId();

            return (projectOrg.equals(taskOrg));
        }
        return false;
    }

    /**
     * 该任务是否是本人派发的
     * @param taskId 任务id
     */
    public Boolean isSamePerson(Long taskId){

        TaskInfo taskInfo = getById(taskId);

        if(taskInfo != null) {

            Long projectUser = taskInfo.getDdTaskCreatorId();

            Long taskUser = taskInfo.getDdTaskResponsiblePerson();

            return (projectUser.equals(taskUser));
        }
        return false;
    }

    /**
     * 判断同一用户不同任务使用同一名称
     */
    public Boolean hasSameNameTask(Long taskId) {

        TaskInfo taskInfo = getById(taskId);

        if(taskInfo != null) {
            Long userId = taskInfo.getDdTaskResponsiblePerson();
            String taskName = taskInfo.getDdTaskName();
            Long projectId = taskInfo.getDdTaskProjectId();
            String projectName = taskInfo.getDdTaskProjectName();

            List<TaskInfo> tasks = taskInfoDao.getAll();
            for(TaskInfo ti : tasks){
                if(ti.getDdTaskResponsiblePerson().equals(userId)) {
                    //同项目名
                    Boolean projectSame = (ti.getDdTaskProjectName().equals(projectName))
                            && (!ti.getDdTaskProjectId().equals(projectId));
                    //同任务名，这里默认跨项目同名也不允许
                    Boolean taskSame = (ti.getDdTaskName().equals(taskName))
                            && (!ti.getDdTaskId().equals(taskId));
                    if(projectSame || taskSame) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean isMultiOrgProject(Long projectId) {

        List<TaskInfo> tasks = taskInfoDao.queryTaskInfoByProjectId(projectId);

        for(TaskInfo ti : tasks) {

            Long projectOrg = sysOrgDao.getOrgsByUserId(
                    ti.getDdTaskCreatorId()).get(0).getOrgId();

            Long taskOrg = sysOrgDao.getOrgsByUserId(
                    ti.getDdTaskResponsiblePerson()).get(0).getOrgId();

            //首先看是否有跨部门任务
            if(!projectOrg.equals(taskOrg)) {
                //再看该跨部门任务有没有数据
                //TODO：任务数据压根没放到任务里
                //if(ti.getPrivateDataList().size() > 0) {
                    return true;
                //}
            }
        }
        return false;
    }

    public Boolean isMultiUserProject(Long projectId) {

        List<TaskInfo> tasks = taskInfoDao.queryTaskInfoByProjectId(projectId);

        for(TaskInfo ti : tasks) {

            Long projectUser = ti.getDdTaskCreatorId();

            Long taskUser = ti.getDdTaskResponsiblePerson();

            //首先看是否有跨部门任务
            if(!projectUser.equals(taskUser)) {
                //再看该跨部门任务有没有数据
                //TODO：任务数据压根没放到任务里
                //if(ti.getPrivateDataList().size() > 0) {
                return true;
                //}
            }
        }
        return false;
    }
}
