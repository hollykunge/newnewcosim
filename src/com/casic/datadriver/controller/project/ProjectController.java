package com.casic.datadriver.controller.project;

import com.casic.datadriver.controller.task.TaskInfoController;
import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.model.flow.ProcessFlow;
import com.casic.datadriver.model.flow.ProjectProcessAssocia;
import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.ProTaskDependance;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.model.task.TaskStart;

import com.casic.datadriver.publicClass.JsonFormat;
import com.casic.datadriver.service.data.OrderDataRelationService;
import com.casic.datadriver.service.data.PrivateDataService;
import com.casic.datadriver.service.flow.ProcessFlowService;
import com.casic.datadriver.service.flow.ProjectProcessAssociaService;
import com.casic.datadriver.service.project.ProjectService;
import com.casic.datadriver.service.project.ProjectStartService;
import com.casic.datadriver.service.task.ProTaskDependanceService;
import com.casic.datadriver.service.task.TaskInfoService;
import com.casic.datadriver.service.task.TaskStartService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.service.system.ResourcesService;
import com.hotent.platform.service.system.SubSystemService;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目管理
 *
 * @author dodo 2016/11/14 0014.
 */
@Controller
@RequestMapping("/datadriver/project/")
public class ProjectController extends BaseController {

    /**
     * The project service.
     */
    @Resource
    private TaskInfoController taskInfoController;
    @Resource
    private ResourcesService resourcesService;
    @Resource
    private SubSystemService subSystemService;
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectStartService projectStartService;
    @Resource
    private TaskInfoService taskInfoService;
    @Resource
    private ProTaskDependanceService proTaskDependanceService;
    @Resource
    private TaskStartService taskStartService;
    @Resource
    private ProjectProcessAssociaService projectProcessAssociaService;
    @Resource
    private ProcessFlowService processFlowService;
    @Resource
    private OrderDataRelationService orderDataRelationService;
    @Resource
    private PrivateDataService privateDataService;

    /**
     * 保存项目
     *
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */
    @RequestMapping("save")
    @Action(description = "添加或更新project")//6
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        Project project = getFormObject(request);
        try {
            if (project.getDdProjectId() == null || project.getDdProjectId() == 0) {
                project.setDdProjectId(UniqueIdUtil.genId());
                //获取当前用户
                ISysUser sysUser = ContextUtil.getCurrentUser();

                project.setDdProjectCreatorId(sysUser.getUserId());

                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentTime);

                project.setDdProjectCreateDatatime(dateString);

                projectService.addDDProject(project);
                resultMsg = getText("added", "项目信息");
            } else {
                projectService.updateAll(project);
                resultMsg = getText("updated", "项目信息");
            }
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 取得对象实体
     *
     * @param request
     * @return
     * @throws Exception
     */
    protected Project getFormObject(HttpServletRequest request) throws Exception {

        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[]{"yyyy-MM-dd"})));

        String json = RequestUtil.getString(request, "json");
        JSONObject obj = JSONObject.fromObject(json);

        Map<String, Class> map = new HashMap<String, Class>();
        map.put("taskInfoList", TaskInfo.class);
        Project project = (Project) JSONObject.toBean(obj, Project.class, map);
        return project;
    }


    /**
     * 查询项目列表
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("projectList")
    @Action(description = "根据条件查询项目基本信息列表")
    public void projectList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long userId = ContextUtil.getCurrentUserId();
        List<Project> allProjectList = this.projectService.queryProjectBasicInfoList(userId);
        String projectName = RequestUtil.getString(request, "name");

        for (int i = 0; i < allProjectList.size(); i++) {
            Project nowProject = allProjectList.get(i);
            List<ProTaskDependance> proTaskDependanceList = proTaskDependanceService.getProTaskDependanceList(nowProject.getDdProjectId());
            int listLengthStart = 0;
            int listLengthComplete = 0;
            for (int j = 0; j < proTaskDependanceList.size(); j++) {
                TaskInfo nowTask = taskInfoService.getById(proTaskDependanceList.get(j).getDdTaskId());
                if (nowTask.getDdTaskChildType().equals("publishpanel") || nowTask.getDdTaskChildType().equals("checkpanel")) {
                    listLengthStart++;
                }
                if (nowTask.getDdTaskChildType().equals("completepanel")) {
                    listLengthComplete++;
                }
            }
            if (listLengthComplete == proTaskDependanceList.size() && listLengthComplete != 0) {
                allProjectList.get(i).setDdProjectPhaseId(Project.complete);
            } else {
                if (listLengthStart > 0) {
                    allProjectList.get(i).setDdProjectPhaseId(Project.start);
                } else {
                    allProjectList.get(i).setDdProjectPhaseId(Project.unstart);
                }
            }
        }
        List<Project> projectList = new ArrayList<>();

        if (projectName == "") {
            projectList = allProjectList;

        } else {
            for (int i = 0; i < allProjectList.size(); i++) {
                if (allProjectList.get(i).getDdProjectName().contains(projectName)) {
                    projectList.add(allProjectList.get(i));
                }
            }
        }
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < projectList.size(); i++) {
            Project project = projectList.get(i);
            jsonObject.put("projectId", project.getDdProjectId());
            jsonObject.put("projectName", project.getDdProjectName());
            jsonObject.put("projectPhase", project.getDdProjectPhaseId());


            switch (project.getDdProjectPhaseId()) {
                case 2:
                    jsonObject.put("phase", "未启动");
                    break;
                case 0:
                    jsonObject.put("phase", "已启动");
                    break;
                default:
                    jsonObject.put("phase", "已完成");
                    break;
            }
            if (project.getDdProjectSecretLevel() != null) {
                switch (project.getDdProjectSecretLevel()) {

                    case "jm":
                        jsonObject.put("projectSecretLevel", "机密");
                        break;
                    case "mm":
                        jsonObject.put("projectSecretLevel", "秘密");
                        break;
                    case "fm":
                        jsonObject.put("projectSecretLevel", "非密");
                        break;
                    default:
                        jsonObject.put("projectSecretLevel", "内部");
                        break;
                }
            }
            if (project.getDdProjectSecretLevel() == null) {
                jsonObject.put("projectSecretLevel", "非密");
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

    /**
     * Del.
     *
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */
    @RequestMapping("del")
    @Action(description = "删除project")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String preUrl = RequestUtil.getPrePage(request);
        Long projectId = RequestUtil.getLong(request, "id");
        String resultMsg = null;
        // 删除项目下的所有任务
        List<TaskInfo> taskInfoList = taskInfoService.queryTaskInfoByProjectId(projectId);
        if (taskInfoList.size() > 0) {
            for (TaskInfo taskItem : taskInfoList) {
                Long taskId = taskItem.getDdTaskId();
                try {
                    taskInfoService.delById(taskId);
                    taskStartService.delByTaskId(taskId);
                    proTaskDependanceService.delByTaskId(taskId);
                } catch (Exception e) {
                    writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
                }
            }
        }
        try {
            projectService.delById(projectId);
            resultMsg = getText("added", "项目信息");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
        response.sendRedirect(preUrl);
    }

    /**
     * @param bin the bin
     */
    @InitBinder
    public void initBinder(ServletRequestDataBinder bin) {
        bin.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }


    /**
     * 编辑项目信息
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("edit")
    @Action(description = "编辑项目信息")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        Long id = RequestUtil.getLong(request, "id");
        String returnUrl = RequestUtil.getPrePage(request);
        Project project = projectService.getById(id);
        List<TaskInfo> taskInfoList = projectService.getTaskInfoList(id);

        return getAutoView().addObject("Project", project)
                .addObject("taskInfoList", taskInfoList)
                .addObject("returnUrl", returnUrl);
    }


    /**
     * 取得dd_project明细
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("get")
    @Action(description = "查看明细")
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = RequestUtil.getLong(request, "id");
        Project Project = projectService.getById(id);
        return getAutoView().addObject("Project", Project);
    }

    /**
     * 项目设置
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("setup")
    @Action(description = "项目设置")
    public ModelAndView setup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = RequestUtil.getLong(request, "id");
        Project Project = projectService.getById(id);
        String creatorName = ContextUtil.getCurrentUser().getFullname();
        Long creatorId = ContextUtil.getCurrentUser().getUserId();
        return getAutoView().addObject("Project", Project).addObject("creatorName", creatorName).addObject("creatorId", creatorId);
    }

    /**
     * 项目创建
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("create")
    @Action(description = "项目创建")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String psnSecretLevel = ContextUtil.getCurrentUser().getPsnSecretLevel();
        Integer psnSecretLevelCode = Integer.parseInt(psnSecretLevel);
        return getAutoView().addObject("psnSecretLevelCode", psnSecretLevelCode);
    }

    /**
     * 项目指标
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("indexedit")
    @Action(description = "查看明细")
    public ModelAndView indexedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = RequestUtil.getLong(request, "id");
        Project Project = projectService.getById(id);
        return getAutoView().addObject("Project", Project);
    }

    /**
     * 完成项目
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("done")
    @Action(description = "完成项目")
    public void done(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        Long id = RequestUtil.getLong(request, "id");
        Project project = projectService.getById(id);
        List<TaskInfo> taskInfoList = taskInfoService.queryTaskInfoByProjectId(id);
        if (taskInfoList.isEmpty()) {
            resultMsg = getText("没有任务，不能完成项目！", "没有任务，不能完成项目！");
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
            return;
        }
        for (TaskInfo taskInfo : taskInfoList) {
            if (taskInfo.getDdTaskState() != 3) {
                resultMsg = getText("有未完成的任务，不能完成项目！", "有未完成的任务，不能完成项目！");
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Fail);
                return;
            }
        }
        project.setDdProjectState((short) 1);
        projectService.updateAll(project);
        resultMsg = getText("完成项目", "完成项目");
        writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
    }

    /**
     * 进入项目控制台
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("stepinto")
    @Action(description = "进入项目")
    public ModelAndView stepinto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long projectId = RequestUtil.getLong(request, "id");
        Project project = projectService.getById(projectId);
        Long userId = project.getDdProjectCreatorId();
        //根据用户ID获取当前用户拥有项目列表
        List<Project> projectListbyUser = projectService.queryProjectBasicInfoList(userId);
        return getAutoView().addObject("Project", project)
                .addObject("projectListbyUser", projectListbyUser);
    }

    /**
     * 进入项目控制台
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("showtask")
    @Action(description = "项目显示")
    public ModelAndView showtask(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long projectId = RequestUtil.getLong(request, "id");
        Project project = projectService.getById(projectId);
        Long userId = project.getDdProjectCreatorId();

        List<TaskInfo> createTaskInfoList = new ArrayList<TaskInfo>();
        List<TaskInfo> publishTaskInfoList = new ArrayList<TaskInfo>();
        List<TaskInfo> checkTaskInfoList = new ArrayList<TaskInfo>();
        List<TaskInfo> completeTaskInfoList = new ArrayList<TaskInfo>();

        List<TaskInfo> taskInfoList = taskInfoService.queryTaskInfoByProjectId(projectId);

        for (TaskInfo taskInfo : taskInfoList) {
            if (taskInfo.getDdTaskChildType().equals("publishpanel")) {
                publishTaskInfoList.add(taskInfo);
            }
            if (taskInfo.getDdTaskChildType().equals("createpanel")) {
                createTaskInfoList.add(taskInfo);
            }
            if (taskInfo.getDdTaskChildType().equals("checkpanel")) {
                checkTaskInfoList.add(taskInfo);
            }
            if (taskInfo.getDdTaskChildType().equals("completepanel")) {
                completeTaskInfoList.add(taskInfo);
            }
        }

        //根据用户ID获取当前用户拥有项目列表
        List<Project> projectListbyUser = projectService.queryProjectBasicInfoList(userId);
        return getAutoView().addObject("Project", project)
                .addObject("projectListbyUser", projectListbyUser);
    }

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("kanban")
    @Action(description = "看板数据")
    public void kanbanData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long projectId = RequestUtil.getLong(request, "projectId");
        String jsonString = taskInfoService.getKanbanDataByProjectId(projectId);
        PrintWriter out = response.getWriter();
        out.append(jsonString);
        out.flush();
        out.close();
    }

    /**
     * 任务从新建拖拽到发布
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("movetask")
    @Action(description = "任务拖拽到发布")
    public void createtopublish(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long taskId = RequestUtil.getLong(request, "id");
        String parent = RequestUtil.getString(request, "parent");
        TaskStart taskStart1 = taskStartService.getByTaskId(taskId);
        String resultMsg = null;
        TaskInfo taskInfo = taskInfoService.getById(taskId);
        //发布任务
        if (taskInfo.getDdTaskChildType().equals("createpanel") && parent.equals("publishpanel")) {
            if (taskStart1 == null) {
                TaskStart taskStart2 = new TaskStart();
                taskStart2.setDdTaskStartId(UniqueIdUtil.genId());
                taskStart2.setDdTaskId(taskId);
                taskStart2.setDdTaskStatus(TaskStart.publishpanel);
                taskStart2.setDdTaskResponcePerson(taskInfo.getDdTaskResponsiblePerson());
                taskStartService.taskStart(taskStart2);
                //更新taskinfo
                taskInfo.setDdTaskChildType("publishpanel");
                taskInfo.setDdTaskState(TaskInfo.publishpanel);
                taskInfoService.update(taskInfo);
                resultMsg="任务发布成功";
            }
        }
        //收回任务
        if (taskInfo.getDdTaskChildType().equals("publishpanel") && parent.equals("createpanel")) {
            //更新taskinfo?????createpanel属性是否应该放到taskstart里面

            List<PrivateData> privateDataList = privateDataService.getPrivateByTaskId(taskId);

            if (privateDataList.isEmpty()) {
                taskStartService.delByTaskId(taskId);

                taskInfo.setDdTaskChildType("createpanel");
                taskInfo.setDdTaskState(TaskInfo.createpanel);
                taskInfoService.update(taskInfo);
                resultMsg="收回成功";
            } else {
                resultMsg="任务中有私有数据，不能收回";
            }
        }
        //驳回任务
        if (taskInfo.getDdTaskChildType().equals("checkpanel") && parent.equals("publishpanel")) {
            //更新taskinfo?????createpanel属性是否应该放到taskstart里面
            taskInfo.setDdTaskChildType("publishpanel");
            taskInfo.setDdTaskState(TaskInfo.publishpanel);
            taskInfoService.update(taskInfo);

            taskStart1.setDdTaskStatus(TaskStart.publishpanel);
            taskStartService.update(taskStart1);
            resultMsg="任务驳回成功";
        }
        //审核通过
        if (taskInfo.getDdTaskChildType().equals("checkpanel") && parent.equals("completepanel")) {
            //更新taskinfo?????createpanel属性是否应该放到taskstart里面
            taskInfo.setDdTaskChildType("completepanel");
            taskInfo.setDdTaskState(TaskInfo.completepanel);
            taskInfoService.update(taskInfo);

            taskStart1.setDdTaskStatus(TaskStart.completepanel);
            taskStartService.update(taskStart1);
            resultMsg="任务审核通过";
        }
        //从已完成到待审核
        if (taskInfo.getDdTaskChildType().equals("completepanel") && parent.equals("checkpanel")) {
            //更新taskinfo?????createpanel属性是否应该放到taskstart里面
            taskInfo.setDdTaskChildType("checkpanel");
            taskInfo.setDdTaskState(TaskInfo.checkpanel);
            taskInfoService.update(taskInfo);

            taskStart1.setDdTaskStatus(TaskStart.checkpanel);
            taskStartService.update(taskStart1);
            resultMsg="任务退回待审核";
        }
        PrintWriter out = response.getWriter();
        out.append(resultMsg);
        out.flush();
        out.close();
    }

    /**
     * 一键发布
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("onepunchsend")
    @Action(description = "一键发布")
    public void onepunchsend(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String resultMsg = null;
            String json = request.getParameter("strJson");
            String projectId = RequestUtil.getString(request, "projectId");
            String parent = RequestUtil.getString(request, "parent");
            JSONArray jsonArray = JSONArray.fromObject(json);

            if (jsonArray.isEmpty()) {
                writeResultMessage(response.getWriter(), resultMsg, 2);
            } else {
                TaskStart taskStart = new TaskStart();

                for (int i = 0; i < jsonArray.size(); i++) {
                    Object ddTaskId = jsonArray.get(i);
                    Long taskId = (Long) ddTaskId;

                    TaskInfo taskInfo = taskInfoService.getById(taskId);

                    taskStart.setDdTaskStartId(UniqueIdUtil.genId());
                    taskStart.setDdTaskId(taskId);
                    taskStart.setDdTaskStatus(TaskStart.publishpanel);

                    //更新taskinfo
                    taskInfo.setDdTaskChildType("publishpanel");
                    taskInfo.setDdTaskState(TaskInfo.publishpanel);
                    taskInfoService.update(taskInfo);
                    //添加taskstart
                    long userId = taskInfo.getDdTaskResponsiblePerson();
                    taskStart.setDdTaskResponcePerson(userId);

                    taskStartService.taskStart(taskStart);
                    writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
                }
            }
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 一键收回
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("onepunchback")
    @Action(description = "一键收回")
    public void onepunchback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String resultMsg = null;
            String strJsonBack = request.getParameter("strJsonBack");
            String projectId = RequestUtil.getString(request, "projectId");
            String parent = RequestUtil.getString(request, "parent");
            JSONArray jsonArrayBack = JSONArray.fromObject(strJsonBack);
            if (jsonArrayBack.isEmpty()) {
                writeResultMessage(response.getWriter(), "isEmpty", 2);
            } else {
                TaskStart taskStart = new TaskStart();

                for (int i = 0; i < jsonArrayBack.size(); i++) {
                    Object ddTaskId = jsonArrayBack.get(i);
                    Long taskId = (Long) ddTaskId;

                    TaskInfo taskInfo = taskInfoService.getById(taskId);
                    //更新taskinfo?????createpanel属性是否应该放到taskstart里面
                    taskInfo.setDdTaskChildType("createpanel");
                    taskInfo.setDdTaskState(TaskInfo.createpanel);
                    taskInfoService.update(taskInfo);

                    taskStartService.delByTaskId(taskInfo.getDdTaskId());
                }
                writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
            }
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 项目统计
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("statis")
    @Action(description = "统计")
    public ModelAndView statis(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProcessFlow processFlow = new ProcessFlow();
        ModelAndView mv = new ModelAndView();
        Long projectId = RequestUtil.getLong(request, "id");
        ProjectProcessAssocia projectProcessAssocia = projectProcessAssociaService.selectByProjectId(projectId);
        if (projectProcessAssocia != null) {
            Long processFlowId = projectProcessAssocia.getDdPrcessId();
            processFlow = processFlowService.getById(processFlowId);
            String tempXml = processFlow.getDdProcessXml();
            mv = this.getAutoView().addObject("projectId", projectId)
                    .addObject("processFlowXml", tempXml);
        } else {
            mv = this.getAutoView().addObject("projectId", projectId);
        }
        return mv;
    }

}