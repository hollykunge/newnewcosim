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
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.SysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private SysUserService sysUserService;
    @Resource
    private TaskVerMapService taskVerMapService;
    @Resource
    private PrivateVersionService privateVersionService;

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
            if (taskInfo.getDdTaskState() == 1 || taskInfo.getDdTaskState() == 2) {
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
            jsonObject.put("ddTaskSecretLevel", taskInfo.getDdSecretLevel());
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
            if (taskInfo.getDdSecretLevel() == null) {
                jsonObject.put("priority", "非密");
            } else {

                switch (taskInfo.getDdSecretLevel()) {
                    case "fm":
                        jsonObject.put("ddTaskSecretLevel", "非密");
                        break;
                    case "mm":
                        jsonObject.put("ddTaskSecretLevel", "秘密");
                        break;
                    case "jm":
                        jsonObject.put("ddTaskSecretLevel", "机密");
                        break;
                    default:
                        jsonObject.put("ddTaskSecretLevel", "非密");
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

        String jsonstring = JsonFormat.formatJson(jsonMembers.toString());
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
        Long taskId = RequestUtil.getLong(request, "id");
        Long type = RequestUtil.getLong(request, "type");
        //形成第一个版本
        if (taskVerMapService.getVersionNum(taskId) == null && privateDataService.getPrivateByTaskId(taskId).size() != 0) {
            savePrivateVer(taskId,true);
        }
        TaskInfo taskInfo = taskInfoService.getById(taskId);
        ISysUser sysUser = sysUserService.getById(taskInfo.getDdTaskResponsiblePerson());
        String account = sysUser.getAccount();
        List<TaskInfo> taskInfoList = taskInfoService.getListByResponceIdAndState1(taskInfo.getDdTaskResponsiblePerson());
        return getAutoView().addObject("TaskInfo", taskInfo)
                .addObject("type", type)
                .addObject("account", account)
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
        Integer type = RequestUtil.getInt(request, "type");
        if (type == null || type == 0) {
            type = 1;
        }
        TaskInfo taskInfo = taskInfoService.getById(taskId);
        Long projectId = RequestUtil.getLong(request, "projectId");
        return getAutoView().addObject("taskId", taskId)
                .addObject("projectId", projectId)
                .addObject("type", type)
                .addObject("taskName", taskInfo.getDdTaskName());
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
    @Action(description = "显示可订阅和已订阅")
    public ModelAndView showorder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = RequestUtil.getLong(request, "id");
        Integer type = RequestUtil.getInt(request, "type");
        if (type == null || type == 0) {
            type = 1;
        }
        Long projectId = RequestUtil.getLong(request, "projectId");
        return getAutoView().addObject("taskId", taskId)
                .addObject("projectId", projectId)
                .addObject("type", type);
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
    @Action(description = "私有与发布数据的切换")
    public void createToPublish(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = "";
        String dataIds = RequestUtil.getString(request, "dataIds");
        String parent = RequestUtil.getString(request, "parent");
        resultMsg = privateDataService.createToPublish(dataIds, parent);
        PrintWriter out = response.getWriter();
        out.append(resultMsg);
        out.flush();
        out.close();
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

    /**
     * 数据导入
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("importPrivateData")
    @Action(description = "数据导入")//3
    public ModelAndView importPrivateData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = RequestUtil.getLong(request, "id");
        Long projectId = RequestUtil.getLong(request, "projectId");
        ModelAndView mv = this.getAutoView().addObject("taskId", id)
                .addObject("projectId", projectId);
        return mv;
    }

    /**
     * 删除所有的任务数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("delAllData")
    @Action(description = "删除全部数据")//3
    public ModelAndView delAllData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = RequestUtil.getLong(request, "id");
        ModelAndView mv = this.getAutoView().addObject("taskId", id);
        return mv;
    }


    /**
     * 删除私有数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("delPrivateData")
    @Action(description = "删除私有数据")//2
    @ResponseBody
    public void delPrivateData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            String dataId = RequestUtil.getString(request, "dataId");
            String taskId = RequestUtil.getString(request, "taskId");
            String[] dataIds = dataId.split("[,|，]");
            List<String> tempIds = Arrays.asList(dataIds);
            for (String tempId : tempIds){
                if (orderDataRelationService.getBeOrderDataByDataId(Long.valueOf(tempId)).size()==0){
                    privateDataService.delprivate(Long.valueOf(tempId));
                }
            }
            //删除数据时更新一个版本.
            savePrivateVer(Long.valueOf(taskId),false);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    /**
     * 更新除私有数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("updatePrivateData")
    @Action(description = "更新除私有数据")//2
    @ResponseBody
    public void updatePrivateData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String resultMsg = "";
        try {
            String uid = RequestUtil.getString(request, "uid");
            String dataId = RequestUtil.getString(request, "dataId");
            String dataName = RequestUtil.getString(request, "dataName");
            String filePath = RequestUtil.getString(request, "filePath");
            String parentId = RequestUtil.getString(request, "parentId");
            String taskId = RequestUtil.getString(request, "taskId");
            String isLeaf = RequestUtil.getString(request, "isLeaf");
            String dataType = RequestUtil.getString(request, "dataType");
            String dataDescription = RequestUtil.getString(request, "dataDescription");
            String publishState = RequestUtil.getString(request, "publishState");
            String orderState = RequestUtil.getString(request, "orderState");
            String submitState = RequestUtil.getString(request, "submitState");
            String taskName = RequestUtil.getString(request, "taskName");
            String creator = RequestUtil.getString(request, "creator");
            String createTime = RequestUtil.getString(request, "createTime");
            String projectId = RequestUtil.getString(request, "projectId");
            String creatorId = RequestUtil.getString(request, "creatorId");
            String dataUnit = RequestUtil.getString(request, "dataUnit");
            String dataValue = RequestUtil.getString(request, "dataValue");
            String dataSenMax = RequestUtil.getString(request, "dataSenMax");
            String dataSenMin = RequestUtil.getString(request, "dataSenMin");
            String type = RequestUtil.getString(request, "type");
            PrivateData privateData2 = privateDataService.getDataById(Long.valueOf(uid));
            PrivateData privateData = new PrivateData();
            String ddDataLastestValue = privateData2.getDdDataLastestValue();
            if (privateData2 != null) {
                privateData2.setDdDataName(dataName);
                privateData2.setDdDataPath(filePath);
                if (parentId == ""){
                    parentId = "0";
                }
                privateData2.setDdDataParentId(Long.valueOf(parentId));
                privateData2.setDdDataTaskId(Long.valueOf(taskId));
                privateData2.setDdDataIsLeaf(Short.valueOf(isLeaf));

                makeDataType(dataType, privateData2);

                privateData2.setDdDataDescription(dataDescription);

                privateData2.setDdDataPublishState(Byte.valueOf(publishState));

                privateData2.setDdDataOrderState(Short.valueOf(orderState));
                privateData2.setDdDataIsSubmit(Short.valueOf(submitState));
                privateData2.setDdDataTaskName(taskName);
                privateData2.setDdDataCreator(creator);
                privateData2.setDdDataCreateTime(new Date());
                privateData2.setDdDataProjId(Long.valueOf(projectId));
                privateData2.setDdDataCreatorId(Long.valueOf(creatorId));
                privateData2.setDdDataUnit(dataUnit);
                privateData2.setDdDataLastestValue(dataValue);
                privateData2.setDdDataSenMax(dataSenMax);
                privateData2.setDdDataSenMin(dataSenMin);
                privateDataService.updateData(privateData2);
                if(ddDataLastestValue == null){
                    ddDataLastestValue = "";
                }
                if(!dataValue.equals(ddDataLastestValue)){
                    System.out.println("++++++++++++++++"+(dataValue.equals(ddDataLastestValue))+"+++++++++++++++++++++++=");
                //更新一个版本
                savePrivateVer(Long.valueOf(taskId),false);
                }
                resultMsg = String.valueOf(privateData2.getDdDataId());
            }
        } catch (Exception e) {
            resultMsg = "failed";
            logger.info(e.getMessage());
        }
        PrintWriter out = response.getWriter();
        out.append(resultMsg);
        out.flush();
        out.close();
    }

    /**
     * 添加私有数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("addPrivateData")
    @Action(description = "添加私有数据")
    @ResponseBody
    public void addPrivateData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String resultMsg = "";
        try {
            String dataId = RequestUtil.getString(request, "dataId");
            String dataName = RequestUtil.getString(request, "dataName");

            String parentId = RequestUtil.getString(request, "parentId");
            String taskId = RequestUtil.getString(request, "taskId");
            String isLeaf = RequestUtil.getString(request, "isLeaf");
            String dataType = RequestUtil.getString(request, "dataType");

            String taskName = RequestUtil.getString(request, "taskName");

            String projectId = RequestUtil.getString(request, "projectId");

            String dataSenMax = RequestUtil.getString(request, "dataSenMax");
            String dataSenMin = RequestUtil.getString(request, "dataSenMin");
            String type = RequestUtil.getString(request, "type");

            PrivateData privateData = new PrivateData();

            privateData.setDdDataId(UniqueIdUtil.genId());
            privateData.setDdDataName(dataName);
            privateData.setDdDataPath(null);
            makeDataType(dataType, privateData);
            privateData.setDdDataDescription("新创建的数据");
            privateData.setDdDataUnit("无");
            privateData.setDdDataLastestValue(null);
            privateData.setDdDataSenMax(dataSenMax);
            privateData.setDdDataSenMin(dataSenMin);
            privateData.setDdDataCreateTime(new Date());
            privateData.setDdDataPublishState((byte) 0);
            privateData.setDdDataOrderState((short) 0);
            privateData.setDdDataIsSubmit((short) 0);
            privateData.setDdDataTaskId(Long.valueOf(taskId));
            privateData.setDdDataCreatorId(ContextUtil.getCurrentUserId());
            privateData.setDdDataParentId(Long.valueOf(parentId));
            privateData.setDdDataProjId(Long.valueOf(projectId));
            privateData.setDdDataIsLeaf(Short.valueOf(isLeaf));
            privateData.setDdDataTaskName(taskName);
            privateDataService.addDDPrivateData(privateData);

            //没有数据时，判断是否有版本信息，没有版本在新建第一条数据时形成第一个版本
            Long versionNum = taskVerMapService.getVersionNum(Long.valueOf(taskId));
            if (versionNum == null) {
                savePrivateVer(Long.valueOf(taskId),true);
            }else{
                savePrivateVer(Long.valueOf(taskId),false);
            }

            resultMsg = String.valueOf(privateData.getDdDataId());
        } catch (Exception e) {
            resultMsg = "failed";
        }
        PrintWriter out = response.getWriter();
        out.append(resultMsg);
        out.flush();
        out.close();
    }

    private void makeDataType(String dataType, PrivateData privateData) {
        switch (dataType) {
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
        Long userId = ContextUtil.getCurrentUser().getUserId();
        List<TaskInfo> taskInfoList = taskInfoService.getListByResponceIdAndState1(userId);

        List<TaskInfo> taskInfos = new ArrayList<>();
        for (TaskInfo taskInfo : taskInfoList) {
            if (taskInfo.getDdTaskChildType().equals("createpanel")) {
                continue;
            } else {
                taskInfos.add(taskInfo);
            }
        }
        ModelAndView mav = getAutoView()
                .addObject("taskInfos", taskInfos);
        return mav;
    }

    @RequestMapping("exportData")
    @Action(description = "导出文件对话框")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = RequestUtil.getLong(request, "id");
        String dataId = RequestUtil.getString(request, "dataIds");
        if (dataId != "") {
            String[] dataIds = dataId.split("[,|，]");
            List<String> tempIds = Arrays.asList(dataIds);
            ModelAndView mv = this.getAutoView().addObject("taskId", id)
                    .addObject("dataIds", tempIds);
            return mv;
        } else {
            ModelAndView mv = this.getAutoView().addObject("taskId", id);
            return mv;
        }
    }

    /**
     *
     * @param taskId
     * @param flag 为true时形成第一个版本，为false时更新版本
     */
    private void savePrivateVer(Long taskId,boolean flag){
        Long versionNum = taskVerMapService.getVersionNum(Long.valueOf(taskId));
        TaskVerMap taskVerMap = new TaskVerMap();
        taskVerMap.setDdTaskVerId(UniqueIdUtil.genId());
        taskVerMap.setDdVersionTime(new Date());
        taskVerMap.setDdTaskId(Long.valueOf(taskId));
        if (flag) {
            taskVerMap.setDdVersionNum(1);
        }else {
            taskVerMap.setDdVersionNum(Integer.valueOf(String.valueOf(versionNum)) + 1);
        }

        taskVerMapService.addTaskVerMap(taskVerMap);
        for (PrivateData privateData : privateDataService.getPrivateByTaskId(taskId)) {
            PrivateVersion privateVersion = new PrivateVersion();
            privateVersion.setDdVersionId(UniqueIdUtil.genId());
            privateVersion.setDdDataCreateTime(privateData.getDdDataCreateTime());
            privateVersion.setDdDataCreator(privateData.getDdDataCreator());
            privateVersion.setDdDataCreatorId(privateData.getDdDataCreatorId());
            privateVersion.setDdDataDepth(privateData.getDdDataDepth());
            privateVersion.setDdDataDescription(privateData.getDdDataDescription());
            privateVersion.setDdDataEngName(privateData.getDdDataEngName());
            privateVersion.setDdDataId(privateData.getDdDataId());
            privateVersion.setDdDataIsLeaf(privateData.getDdDataIsLeaf());
            privateVersion.setDdDataIsSubmit(privateData.getDdDataIsSubmit());
            privateVersion.setDdDataLastestValue(privateData.getDdDataLastestValue());
            privateVersion.setDdDataName(privateData.getDdDataName());
            privateVersion.setDdDataNodePath(privateData.getDdDataNodePath());
            privateVersion.setDdDataOrderState(privateData.getDdDataOrderState());
            privateVersion.setDdDataParentId(privateData.getDdDataParentId());
            privateVersion.setDdDataPath(privateData.getDdDataPath());
            privateVersion.setDdDataProjId(privateData.getDdDataProjId());
            privateVersion.setDdDataPublishState(privateData.getDdDataPublishState());
            privateVersion.setDdDataReserved2(privateData.getDdDataReserved2());
            privateVersion.setDdDataSenMax(privateData.getDdDataSenMax());
            privateVersion.setDdDataSenMin(privateData.getDdDataSenMin());
            privateVersion.setDdDataTaskId(privateData.getDdDataTaskId());
            privateVersion.setDdDataTaskName(privateData.getDdDataTaskName());
            privateVersion.setDdDataType(privateData.getDdDataType());
            privateVersion.setDdDataUnit(privateData.getDdDataUnit());
            privateVersion.setDdDataReserved1(taskVerMap.getDdTaskVerId());
            privateVersionService.addPrivateVer(privateVersion);
        }

    }
}