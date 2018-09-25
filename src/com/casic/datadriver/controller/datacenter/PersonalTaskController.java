package com.casic.datadriver.controller.datacenter;


import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.publicClass.JsonFormat;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.publicClass.QueryParameters;
import com.casic.datadriver.model.data.OrderDataRelation;
import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.model.data.PrivateVersion;
import com.casic.datadriver.model.data.TaskVerMap;
import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.model.task.TaskStart;
import com.casic.datadriver.service.data.OrderDataRelationService;
import com.casic.datadriver.service.data.PrivateDataService;
import com.casic.datadriver.service.data.PrivateVersionService;
import com.casic.datadriver.service.data.TaskVerMapService;
import com.casic.datadriver.service.project.ProjectService;
import com.casic.datadriver.service.task.TaskInfoService;
import com.casic.datadriver.service.task.TaskStartService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 2016/11/14
 */
@Controller
@RequestMapping("/datadriver/personaltask/")
public class PersonalTaskController extends AbstractController {

    @Resource
    private TaskInfoService taskInfoService;
    @Resource
    private PrivateDataService privateDataService;
    @Resource
    private OrderDataRelationService orderDataRelationService;
    @Resource
    private TaskStartService taskStartService;
    @Resource
    private TaskVerMapService taskVerMapService;
    @Resource
    private PrivateVersionService privateVersionService;
    @Resource
    private ProjectService projectService;

    JsonFormat Tjson = new JsonFormat();

    /**
     * 2016/12/4/
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("taskList")
    @Action(description = "个人任务列表")
    public void taskList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<TaskInfo> UserTaskInfo_list = taskInfoService.getListByResponceId(ContextUtil.getCurrentUserId());
        List<TaskInfo> taskInfo_list = new ArrayList<TaskInfo>();

        for (int i = 0; i < UserTaskInfo_list.size(); i++) {
            TaskInfo taskInfo = UserTaskInfo_list.get(i);
            if (taskInfo.getDdTaskState()==1 || taskInfo.getDdTaskState()==2) {
                taskInfo_list.add(taskInfo);
            }
        }
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < taskInfo_list.size(); i++) {
            TaskInfo taskInfo = taskInfo_list.get(i);
            jsonObject.put("ddTaskId", taskInfo.getDdTaskId());
            jsonObject.put("ddTaskPriority", taskInfo.getDdTaskPriority());
            jsonObject.put("ddTaskState", taskInfo.getDdTaskState());
            jsonObject.put("ddTaskProjectName", taskInfo.getDdTaskProjectName());
            jsonObject.put("ddTaskName", taskInfo.getDdTaskName());
            if (taskInfo.getDdTaskPriority() == null) {
                jsonObject.put("priority", "一般");
            } else {

                switch (taskInfo.getDdTaskPriority()) {
                    case 3:
                        jsonObject.put("priority", "紧急");
                        break;
                    case 2:
                        jsonObject.put("priority", "重要");
                        break;
                    default:
                        jsonObject.put("priority", "一般");
                        break;
                }
            }
            switch (taskInfo.getDdTaskState()) {
                case 0:
                    jsonObject.put("state", "新建");
                    break;
                case 1:
                    jsonObject.put("state", "已发布");
                    break;
                case 2:
                    jsonObject.put("state", "审核中");
                    break;
                case 3:
                    jsonObject.put("state", "已完成");
                    break;
                default:
                    jsonObject.put("state", "未知");
                    break;
            }
            jsonMembers.add(jsonObject);
        }

        String jsonstring = Tjson.formatJson(jsonMembers.toString());
        System.out.println(json.toString());
        PrintWriter out = null;
        out = response.getWriter();
        out.append(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("submitpublish")
    @Action(description = "发布数据")
    public ModelAndView querysubmitpublish(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long ddTaskId = RequestUtil.getLong(request, "id");
        List<OrderDataRelation> orderDataRelation_list = this.orderDataRelationService.getPublishDataRelationList(ddTaskId);
        List<PrivateData> privateData_list = new ArrayList<PrivateData>();
        for (int i = 0; i < orderDataRelation_list.size(); i++) {
            OrderDataRelation orderDataRelation = orderDataRelation_list.get(i);
            PrivateData privateData = privateDataService.getById(orderDataRelation.getDdDataId());
            privateData_list.add(privateData);
        }
        ModelAndView mv = this.getAutoView().addObject("privateDataList",
                privateData_list).addObject("taskId", ddTaskId);
        return mv;
    }

    /**
     * 进入任务控制台
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("todotask")
    @Action(description = "进入任务")//1
    public ModelAndView todotask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long taskId = RequestUtil.getLong(request, "id");
        TaskInfo taskInfo = taskInfoService.getById(taskId);
        List<TaskInfo> taskInfoList = taskInfoService.getListByResponceIdAndState1(taskInfo.getDdTaskResponsiblePerson());
        return getAutoView().addObject("TaskInfo", taskInfo)
                .addObject("taskInfoList", taskInfoList);
    }

    /**
     * 模型预览
     *
     * @return
     */
    @RequestMapping("viewModel")
    @Action(description = "模型预览")
    public ModelAndView viewModel(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long dataId = RequestUtil.getLong(request, "id");
        PrivateData privateData = privateDataService.getDataById(dataId);
        return getAutoView().addObject("dataPath", privateData.getDdDataPath());
    }

    /**
     * 进入任务数据发布
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("showdata")
    @Action(description = "显示私有和发布")
    public ModelAndView showdata(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = RequestUtil.getLong(request, "id");
        Long projectId = RequestUtil.getLong(request, "projectId");
        return getAutoView().addObject("taskId", taskId).addObject("projectId", projectId);
    }

    /**
     * 进入任务数据订阅
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("showorder")
    @Action(description = "显示订阅和已订阅")
    public ModelAndView showorder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long taskId = RequestUtil.getLong(request, "id");
        Long projectId = RequestUtil.getLong(request, "projectId");
        return getAutoView().addObject("taskId", taskId).addObject("projectId", projectId);
    }


    @RequestMapping("submittask")
    @Action(description = "提交任务")//4
    public void submittask(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long ddTaskId = RequestUtil.getLong(request, "id");
        try {
            Boolean isSubmit = taskInfoService.submitTask(ddTaskId);
            if (!isSubmit) {
                String resultMsg = null;
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
            }
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    @RequestMapping("recovertask")
    @Action(description = "收回任务")
    public void recovertask(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long ddTaskId = RequestUtil.getLong(request, "id");
        try {
            Boolean isRecycle = taskInfoService.recycleTask(ddTaskId);
            if (!isRecycle) {
                String resultMsg = null;
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
            }
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 可订阅拖拽到订阅实现
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("canOrderToOrder")
    @Action(description = "可订阅与订阅切换")//8
    public void canOrderToOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dataIds = RequestUtil.getString(request, "dataIds");
        Long taskId = RequestUtil.getLong(request, "taskId");
        String parent = RequestUtil.getString(request, "parent");
        try {
            privateDataService.canOrderToOrder(dataIds, parent, taskId);
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 私有和发布数据之间的拖拽
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("createToPublish")
    @Action(description = "私有与发布数据的切换")//8
    public void createToPublish(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dataIds = RequestUtil.getString(request, "dataIds");
        String parent = RequestUtil.getString(request, "parent");
        privateDataService.createToPublish(dataIds, parent);
    }

    /**
     * 私有数据的文件模型上传
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("uploadPrivateFile")
    @Action(description = "私有数据的文件模型上传")//3
    public ModelAndView uploadPrivateFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = RequestUtil.getLong(request, "id");
        ModelAndView mv = this.getAutoView().addObject("id", id);
        return mv;
    }

    public void delprivate(Long privateId) {
        privateDataService.delDataById(privateId);
        if (privateDataService.getDataListByPId(privateId).size() == 0)
            return;
        else {
            Long pid = privateId;
            List<PrivateData> privateDatalist = privateDataService.getDataListByPId(pid);
            privateDataService.delDataById(pid);
            for (int i = 0; i < privateDatalist.size(); i++) {
                delprivate(privateDatalist.get(i).getDdDataId());
            }
        }
    }


    /**
     * 私有数据更新
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("updatePrivateData")
    @Action(description = "更新私有数据")//2
    public void updatePrivateData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String orderJson = RequestUtil.getString(request, "orderJson");
        //解码，为了解决中文乱码
//        String str = URLDecoder.decode(request.getParameter("orderJson"),"UTF-8");
        JSONObject myjson = new JSONObject();
        //将json格式的字符串转换为json数组对象
        JSONArray array = (JSONArray) myjson.fromObject(orderJson).get("rows");
        //TODO:下面那个最好删除
        Long taskId = Long.valueOf(0);
        Date currentTime = new Date();
        for (int i = 0; i < array.size(); i++) {
            JSONObject myjb = (JSONObject) array.get(i);
            PrivateData privateData = new PrivateData();
            Long type = Long.valueOf(myjb.get("type").toString());
            taskId = Long.valueOf(myjb.get("taskId").toString());
            //TODO:增加type类型未定义或者为空值其他值得情况
            if (type == 1) {
                privateData.setDdDataId(Long.valueOf(myjb.get("dataId").toString()));
                privateData.setDdDataName(myjb.get("dataName").toString());
                privateData.setDdDataPath(myjb.get("filePath").toString());

                makeDataType(myjb, privateData);

                privateData.setDdDataDescription(myjb.get("dataDescription").toString());
                privateData.setDdDataUnit(myjb.get("dataUnit").toString());
                privateData.setDdDataLastestValue(myjb.get("dataValue").toString());
                String tempMax = myjb.get("dataSenMax").toString();
                String tempMin = myjb.get("dataSenMin").toString();
                if (tempMax.equals("undefined") == false) {
                    privateData.setDdDataSenMax(myjb.get("dataSenMax").toString());
                }
                if (tempMin.equals("undefined") == false) {
                    privateData.setDdDataSenMin(myjb.get("dataSenMin").toString());
                }
                privateDataService.updateData(privateData);
            } else if (type == 2) {
                //删除
                Long dataId = Long.valueOf(myjb.get("dataId").toString());
                delprivate(dataId);
            } else if (type == 3) {
                //新增
                Long dataId = Long.valueOf(myjb.get("dataId").toString());
                Long projectId = Long.valueOf(myjb.get("projectId").toString());
                String name = myjb.get("dataName").toString();//获得属性值
                privateData.setDdDataId(Long.valueOf(myjb.get("dataId").toString()));
                privateData.setDdDataName(myjb.get("dataName").toString());
                privateData.setDdDataPath(myjb.get("filePath").toString());
                makeDataType(myjb, privateData);
                privateData.setDdDataDescription(myjb.get("dataDescription").toString());
                privateData.setDdDataUnit(myjb.get("dataUnit").toString());
                privateData.setDdDataLastestValue(myjb.get("dataValue").toString());
                privateData.setDdDataParentId(Long.valueOf(myjb.get("parentId").toString()));
                privateData.setDdDataSenMax(myjb.get("dataSenMax").toString());
                privateData.setDdDataSenMin(myjb.get("dataSenMin").toString());
                privateData.setDdDataProjId(projectId);
                if (privateData.getDdDataParentId() > 0) {
                    privateData.setDdDataIsLeaf((short) 1);
                } else if (privateData.getDdDataParentId() == 0) {
                    privateData.setDdDataIsLeaf((short) 0);
                }
                privateData.setDdDataTaskId(Long.valueOf(myjb.get("taskId").toString()));
                privateData.setDdDataPublishState((byte) 0);
                privateData.setDdDataOrderState((short) 0);
                privateData.setDdDataIsSubmit((short) 0);
                privateData.setDdDataCreateTime(currentTime);
                TaskInfo taskInfo = taskInfoService.getById(Long.valueOf(myjb.get("taskId").toString()));
                privateData.setDdDataTaskName(taskInfo.getDdTaskName());
                privateData.setDdDataCreatorId(ContextUtil.getCurrentUserId());
                privateDataService.addSingleData(privateData);
            }
        }
        Long num = taskVerMapService.getVersionNum(taskId);
        if (num == null) {
            num = Long.valueOf(0);
        }
        num = num + 1;
        List<PrivateData> privateDataList = privateDataService.getPrivateByTaskId(taskId);
        TaskVerMap taskVerMap = new TaskVerMap();

        taskVerMap.setDdTaskId(taskId);
        taskVerMap.setDdTaskVerId(UniqueIdUtil.genId());
        taskVerMap.setDdVersionTime(currentTime);
        taskVerMap.setDdVersionNum(Math.toIntExact(num));

        taskVerMapService.addTaskVerMap(taskVerMap);
        for (int a = 0; a < privateDataList.size(); a++) {
            PrivateVersion privateVersion = new PrivateVersion();
            privateVersion.setDdVersionId(UniqueIdUtil.genId());
            privateVersion.setDdDataId(privateDataList.get(a).getDdDataId());
            privateVersion.setDdDataName(privateDataList.get(a).getDdDataName());
            privateVersion.setDdDataType(privateDataList.get(a).getDdDataType());
            privateVersion.setDdDataDescription(privateDataList.get(a).getDdDataDescription());
            privateVersion.setDdDataTaskId(privateDataList.get(a).getDdDataTaskId());
            privateVersion.setDdDataPublishState(privateDataList.get(a).getDdDataPublishState());
            privateVersion.setDdDataOrderState(privateDataList.get(a).getDdDataOrderState());
            privateVersion.setDdDataIsSubmit(privateDataList.get(a).getDdDataIsSubmit());
            privateVersion.setDdDataCreateTime(privateDataList.get(a).getDdDataCreateTime());
            privateVersion.setDdDataCreator(privateDataList.get(a).getDdDataCreator());
            privateVersion.setDdDataCreatorId(privateDataList.get(a).getDdDataCreatorId());
            privateVersion.setDdDataSenMin(privateDataList.get(a).getDdDataSenMin());
            privateVersion.setDdDataSenMax(privateDataList.get(a).getDdDataSenMax());
            privateVersion.setDdDataParentId(privateDataList.get(a).getDdDataParentId());
            privateVersion.setDdDataLastestValue(privateDataList.get(a).getDdDataLastestValue());
            privateVersion.setDdDataUnit(privateDataList.get(a).getDdDataUnit());
            privateVersion.setDdDataTaskName(privateDataList.get(a).getDdDataTaskName());
            privateVersion.setDdDataEngName(privateDataList.get(a).getDdDataEngName());
            privateVersion.setDdDataPath(privateDataList.get(a).getDdDataPath());
            privateVersion.setDdDataNodePath(privateDataList.get(a).getDdDataNodePath());
            privateVersion.setDdDataDepth(privateDataList.get(a).getDdDataDepth());
            privateVersion.setDdDataIsLeaf(privateDataList.get(a).getDdDataIsLeaf());
            privateVersion.setDdDataProjId(privateDataList.get(a).getDdDataProjId());
            privateVersion.setDdDataReserved1(taskVerMap.getDdTaskVerId());
            privateVersion.setDdDataReserved2(privateDataList.get(a).getDdDataReserved2());

            privateVersionService.addPrivateVer(privateVersion);
        }
    }

    private void makeDataType(JSONObject myjb, PrivateData privateData) {
        switch (myjb.get("dataType").toString()) {
            case "数值":
                privateData.setDdDataType((byte) 0);
                break;
            case "模型":
                privateData.setDdDataType((byte) 1);
                break;
            case "文件":
                privateData.setDdDataType((byte) 2);
                break;
            case "结构型数据":
                privateData.setDdDataType((byte) 3);
                break;
            default:
                break;
        }
    }

    /**
     * 删除私有数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("DelPrivateData")
    @Action(description = "删除私有数据")
    public void DelPrivateData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String orderJson = RequestUtil.getString(request, "orderJson");
        //解码，为了解决中文乱码
        JSONObject myjson = new JSONObject();
        //将json格式的字符串转换为json数组对象
        JSONArray array = (JSONArray) myjson.fromObject(orderJson).get("rows");
        //取得json数组中的第一个对象
        for (int i = 0; i < array.size(); i++) {
            JSONObject myjb = (JSONObject) array.get(i);
            Long dataID = Long.valueOf(myjb.get("dataId").toString());
            privateDataService.delById(dataID);
        }
    }

    /**
     * 个人主页
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("mydashboard")
    public ModelAndView mydashboard(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String userName = ContextUtil.getCurrentUser().getFullname();
        String roleName = ContextUtil.getCurrentUser().getShortAccount();
        Long userId = ContextUtil.getCurrentUser().getUserId();
        List<TaskInfo> taskInfoList = taskInfoService.getListByResponceIdAndState1(userId);
        Long tempProjectId = 0l;
        List<Project> projectList = new ArrayList<>();
        for (TaskInfo taskInfo : taskInfoList) {
            if (taskInfo.getDdTaskChildType().equals("createpanel")) {
                continue;
            } else {
                tempProjectId = taskInfo.getDdTaskProjectId();
                if (isHas(tempProjectId)) {
                    Project project = projectService.getById(tempProjectId);
                    List<TaskInfo> taskInfos = taskInfoService.getListByPriority(tempProjectId);
                    project.setTaskInfoList(taskInfos);
                    projectList.add(project);
                }
            }
        }
        //去重
        List<Project> projectList1 = removeDuplicate(projectList);
        ModelAndView mav = getAutoView()
                .addObject("userName", userName)
                .addObject("projectList", projectList1)
                .addObject("roleName", roleName);
        return mav;
    }

    //查询是否有
    private Boolean isHas(Long projectId) {
        List<Project> proLists = projectService.getAll();
        for (Project project : proLists) {
            if (project.getDdProjectId().equals(projectId)) return true;
        }
        return false;
    }

    //过滤重复元素
    public static List<Project> removeDuplicate(List<Project> mList) {
        for (int i = 0; i < mList.size() - 1; i++) {
            for (int j = mList.size() - 1; j > i; j--) {
                if (mList.get(j).getDdProjectId().equals(mList.get(i).getDdProjectId())) {
                    mList.remove(j);
                }
            }
        }
        return mList;
    }
}

