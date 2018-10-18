package com.casic.datadriver.controller.flow;

import com.casic.cloud.controller.console.ConsoleController;
import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.data.ProjectStart;
import com.casic.datadriver.model.flow.ProcessFlow;
import com.casic.datadriver.model.flow.ProjectProcessAssocia;
import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.ProTaskDependance;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.service.project.ProjectService;
import com.casic.datadriver.service.task.ProTaskDependanceService;
import com.casic.datadriver.service.task.TaskInfoService;
import com.casic.datadriver.service.flow.ProcessFlowService;

import com.hotent.core.annotion.Action;
import com.hotent.core.bpmn20.entity.Process;

import com.hotent.core.bpmn20.entity.Task;

import com.hotent.core.util.ContextUtil;

import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import org.h2.tools.Console;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import com.casic.datadriver.service.flow.ProjectProcessAssociaService;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by dodo on 2016/12/27.
 */
@Controller
@RequestMapping("/datadriver/designflow/")
public class ProcessFlowController extends AbstractController {

    @Resource
    private ProcessFlowService processFlowService;
    @Resource
    private TaskInfoService taskInfoService;
    @Resource
    private ProTaskDependanceService proTaskDependanceService;
    @Resource
    private ProjectProcessAssociaService projectProcessAssociaService;


    @Resource
    private ProjectService projectService;

    /**
     * 进入流程iframe.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("flowframe")
    @Action(description = "根据项目进入流程设计iframe")
    public ModelAndView flowframe(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ProcessFlow processFlow = new ProcessFlow();
        //跳转到流程设计页面
        String url = request.getRequestURI();
        ModelAndView mv = new ModelAndView(new RedirectView("newnewcosim-master_war/datadriver/designflow/projectflow.ht"));
        Long projectId = RequestUtil.getLong(request, "id");
        mv.addObject("projectId", projectId);
        return mv;
    }

    /**
     * 进入流程设计页面.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("projectflow")
    @Action(description = "流程设计")
    public ModelAndView projectflow(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ProcessFlow processFlow = new ProcessFlow();
        ModelAndView mv = new ModelAndView();
        Long projectId = RequestUtil.getLong(request, "projectId");
        int flag = RequestUtil.getInt(request,"flag");
        ProjectProcessAssocia projectProcessAssocia = projectProcessAssociaService.selectByProjectId(projectId);
        if (projectProcessAssocia != null) {
            Long processFlowId = projectProcessAssocia.getDdPrcessId();
            processFlow = processFlowService.getById(processFlowId);
            String tempXml = processFlow.getDdProcessXml();

            //更改xml 进行流程的监控
            if(flag==1)
            {
                Reader in = new StringReader(tempXml);
                Document doc = (new SAXBuilder()).build(in);

                Element mxGraphmodel = doc.getRootElement();
                Element root = mxGraphmodel.getChild("root");
                //String projectID = root.getChild("Layer").getAttributeValue("projectID");
                java.util.List task = root.getChildren("Task");




                for (Iterator i = task.iterator(); i.hasNext(); ) {
                    Element el = (Element) i.next();

                    String taskid = el.getAttributeValue("oracleid");

                    TaskInfo taskInfo = taskInfoService.getById(Long.parseLong(taskid));
                    String ddTaskChildType = taskInfo.getDdTaskChildType();

                    Element mxCell;
                    mxCell = el.getChild("mxCell");
                    String style = mxCell.getAttributeValue("style");

                    if(ddTaskChildType.compareTo("publishpanel")==0) {
                        style += ";strokeColor=red;fontColor=red";
                        mxCell.setAttribute("style",style);
                    }
                    else if(ddTaskChildType.compareTo("checkpanel")==0) {
                        style += ";strokeColor=green;fontColor=green";
                        mxCell.setAttribute("style",style);
                    }
                    else if(ddTaskChildType.compareTo("completepanel")==0) {
                        style += ";strokeColor=blue;fontColor=blue";
                        mxCell.setAttribute("style",style);
                    }
                }

                //输出改造后的xml
                Format format = Format.getCompactFormat();
                format.setEncoding("utf-8");
                format.setIndent(" ");
                XMLOutputter xmlOutputter = new XMLOutputter();
                java.io.StringWriter a = new java.io.StringWriter();
                xmlOutputter.output(doc, a);
                String str = a.toString();

                //要对String str做掐头去尾
                String xml = str.substring(40,str.lastIndexOf('>')+1);
                mv = this.getAutoView().addObject("projectId", projectId)
                        .addObject("processFlowXml", xml).addObject("flag",flag);

            }
            else{
                Reader in = new StringReader(tempXml);
                Document doc = (new SAXBuilder()).build(in);

                Element mxGraphmodel = doc.getRootElement();
                Element root = mxGraphmodel.getChild("root");
                //String projectID = root.getChild("Layer").getAttributeValue("projectID");
                java.util.List task = root.getChildren("Task");




                for (Iterator i = task.iterator(); i.hasNext(); ) {
                    Element el = (Element) i.next();

                    String taskid = el.getAttributeValue("oracleid");

                    TaskInfo taskInfo = taskInfoService.getById(Long.parseLong(taskid));
                    String ddTaskChildType = taskInfo.getDdTaskChildType();

                    Element mxCell;
                    mxCell = el.getChild("mxCell");
                    String style = mxCell.getAttributeValue("style");

                    el.setAttribute("status","publish");

                }

                //输出改造后的xml
                Format format = Format.getCompactFormat();
                format.setEncoding("utf-8");
                format.setIndent(" ");
                XMLOutputter xmlOutputter = new XMLOutputter();
                java.io.StringWriter a = new java.io.StringWriter();
                xmlOutputter.output(doc, a);
                String str = a.toString();

                //要对String str做掐头去尾
                String xml = str.substring(40,str.lastIndexOf('>')+1);
                mv = this.getAutoView().addObject("projectId", projectId)
                        .addObject("processFlowXml", xml).addObject("flag",flag);


            }


        } else {
            mv = this.getAutoView().addObject("projectId", projectId).addObject("flag",flag);
        }
        return mv;
    }


    @RequestMapping("DataSave_zx")
    @Action(description = "根据项目进入流程设计iframe")
    public void DataSave_zx(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取request发送的xml
        request.setCharacterEncoding("utf-8");
        //	String test = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replaceAll("&#xa;", "");
        String test = URLDecoder.decode(request.getParameter("xml"), "UTF-8").replaceAll("\n", "");
        //String xml = (String) request.getParameter("xml");
        //System.out.println(test);

        //解析xml
        Reader in = new StringReader(test);
        Document doc = (new SAXBuilder()).build(in);

        //根据结构 获取所有的task children
        Element mxGraphmodel = doc.getRootElement();
        Element root = mxGraphmodel.getChild("root");

        //String projectID = root.getChild("Layer").getAttributeValue("projectID");
        java.util.List task = root.getChildren("Task");

        //在layer这里获取projectID,假设projectID没有并没有措施防错误
        String projectID = root.getChild("Layer").getAttributeValue("projectId");
        long projectId=0;
        if(projectID!=null)
            projectId = Long.parseLong(projectID);
        else
            System.out.println("can't get projectID");
        Project project = projectService.getById(projectId);
        List<TaskInfo> taskInfoList = project.getTaskInfoList();

        for (Iterator i = task.iterator(); i.hasNext(); ) {
            TaskInfo taskInfo = new TaskInfo();

            Element el = (Element) i.next();
            String tasktype = el.getAttributeValue("任务类型");
            String taskdesp = el.getAttributeValue("任务描述");
            String taskid = el.getAttributeValue("oracleid");
            //考虑null的情况
            if (taskdesp != null)
                taskInfo.setDdTaskDescription(taskdesp);
            if (tasktype != null)
                taskInfo.setDdTaskType(tasktype);
            taskInfo.setDdTaskProjectId(projectId);
            Project project2 = projectService.getById(projectId);
            taskInfo.setDdTaskProjectName(project2.getDdProjectName());
            taskInfo.setDdTaskName(el.getAttributeValue("label"));//任务名称
            taskInfo.setDdTaskResponsiblePerson(ContextUtil.getCurrentUser().getUserId());
            taskInfo.setDdTaskPerson(ContextUtil.getCurrentUser().getFullname());
            taskInfo.setDdTaskState((short) 0);
            //新增任务属性
            taskInfo.setDdTaskChildType("createpanel");
            if (taskid != null) {
                taskInfo.setDdTaskId(Long.parseLong(taskid));
            }
            //项目任务依赖关系
            ProTaskDependance proTaskDependance = new ProTaskDependance();
            proTaskDependance.setDdProjectId(taskInfo.getDdTaskProjectId());
            try {
                if (taskInfo.getDdTaskId() == null || taskInfo.getDdTaskId() == 0) {
                    taskInfo.setDdTaskId(UniqueIdUtil.genId());
                    //任务添加
                    taskInfoService.addDDTask(taskInfo);
                    proTaskDependance.setDdTaskId(UniqueIdUtil.genId());
                    proTaskDependance.setDdTaskId(taskInfo.getDdTaskId());
                    proTaskDependanceService.addDDProTaskDependance(proTaskDependance);
                    //resultMsg = getText("record.added", "添加完成");
                } else {
                    TaskInfo byId;
                    byId = taskInfoService.getById(taskInfo.getDdTaskId());
                    byId.setDdTaskName(el.getAttributeValue("label"));
                    byId.setDdTaskDescription(taskdesp);
                    byId.setDdTaskType(tasktype);

                    taskInfoService.updateDDTask(byId);
                    //resultMsg = getText("record.updated", "更新完成");
                    for(Iterator it =taskInfoList.iterator();it.hasNext();)
                    {
                        TaskInfo ti = (TaskInfo) it.next();
                        if(ti.getDdTaskId()==taskInfo.getDdTaskId())
                            it.remove();



                    }
                }
            } catch (Exception e) {
                writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.Fail);
            }
            el.setAttribute("oracleid", taskInfo.getDdTaskId().toString());
            System.out.println(taskInfo.getDdTaskId().toString());
        }
        /*
        for(Iterator it =taskInfoList.iterator();it.hasNext();)
        {
            TaskInfo ti = (TaskInfo) it.next();
            taskInfoService.delById(ti.getDdTaskId());
        }
*/
        //保存流程XML
        if (projectId != 0)
            saveProcessXml(doc, projectId);
    }

    /**
     * 流程XML存储.
     *
     * @param doc
     * @return void
     * @throws Exception the exception
     */
    private String saveProcessXml(Document doc, long projectId) throws IOException {
        ProjectProcessAssocia projectProcessAssocia ;
        Format format = Format.getCompactFormat();
        format.setEncoding("utf-8");
        format.setIndent(" ");
        XMLOutputter xmlOutputter = new XMLOutputter();
        java.io.StringWriter a = new java.io.StringWriter();
        xmlOutputter.output(doc, a);
        String str = a.toString();

        //要对String str做掐头去尾
        String xml = str.substring(40,str.lastIndexOf('>')+1);

        //需要保存xml 变量str就是xml
        projectProcessAssocia = projectProcessAssociaService.selectByProjectId(projectId);
        //process数据库表存储
        ProcessFlow processflow;
        if(projectProcessAssocia==null){
            processflow = new ProcessFlow();
            processflow.setDdProcessId(UniqueIdUtil.genId());
            processflow.setDdProcessXml(xml);
            processFlowService.addProcessFlow(processflow);
            projectProcessAssocia = new ProjectProcessAssocia();
            //项目和流程映射关系
            projectProcessAssocia.setDdPrcessId(processflow.getDdProcessId());
            projectProcessAssocia.setDdProjectId(projectId);
            saveProjectProcessAssocia(projectProcessAssocia);
        }
        else {
            processflow = processFlowService.getById(projectProcessAssocia.getDdPrcessId());
            processflow.setDdProcessXml(xml);

            processFlowService.update(processflow);

        }
        return xml;
    }

    /**
     * 项目流程映射关系表存储.
     *
     * @param projectProcessAssocia
     * @return void
     * @throws Exception the exception
     */
    private void saveProjectProcessAssocia(ProjectProcessAssocia projectProcessAssocia) {
        projectProcessAssocia.setDdPrcessAssociationId(UniqueIdUtil.genId());
        projectProcessAssociaService.add(projectProcessAssocia);
    }


    @RequestMapping("getXMLbyID")
    @Action(description = "根据项目进入流程设计iframe")
    public void getXMLbyID(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取request发送的xml
        request.setCharacterEncoding("utf-8");
        ProcessFlow processFlow = new ProcessFlow();
        ModelAndView mv = new ModelAndView();


        String test = RequestUtil.getString(request,"xml");
        //解析xml
        Reader in = new StringReader(test);
        Document doc = (new SAXBuilder()).build(in);

        //根据结构 获取所有的task children
        Element mxGraphmodel = doc.getRootElement();
        Element root = mxGraphmodel.getChild("root");

        //String projectID = root.getChild("Layer").getAttributeValue("projectID");
        java.util.List task = root.getChildren("Task");

        //在layer这里获取projectID,假设projectID没有并没有措施防错误
        String projectID = root.getChild("Layer").getAttributeValue("projectId");
        long projectId=0;
        if(projectID!=null)
            projectId = Long.parseLong(projectID);
        else
            System.out.println("can't get projectID");
        Project project = projectService.getById(projectId);
        List<TaskInfo> taskInfoList = project.getTaskInfoList();

        for (Iterator i = task.iterator(); i.hasNext(); ) {
            TaskInfo taskInfo = new TaskInfo();

            Element el = (Element) i.next();
            String tasktype = el.getAttributeValue("任务类型");
            String taskdesp = el.getAttributeValue("任务描述");
            String taskid = el.getAttributeValue("oracleid");
            //考虑null的情况
            if (taskdesp != null)
                taskInfo.setDdTaskDescription(taskdesp);
            if (tasktype != null)
                taskInfo.setDdTaskType(tasktype);
            taskInfo.setDdTaskProjectId(projectId);
            Project project2 = projectService.getById(projectId);
            taskInfo.setDdTaskProjectName(project2.getDdProjectName());
            taskInfo.setDdTaskName(el.getAttributeValue("label"));//任务名称
            taskInfo.setDdTaskResponsiblePerson(ContextUtil.getCurrentUser().getUserId());
            taskInfo.setDdTaskPerson(ContextUtil.getCurrentUser().getFullname());
            //新增任务属性
            taskInfo.setDdTaskChildType("createpanel");
            if (taskid != null) {
                taskInfo.setDdTaskId(Long.parseLong(taskid));
            }
            //项目任务依赖关系
            ProTaskDependance proTaskDependance = new ProTaskDependance();
            proTaskDependance.setDdProjectId(taskInfo.getDdTaskProjectId());
            try {
                if (taskInfo.getDdTaskId() == null || taskInfo.getDdTaskId() == 0) {
                    taskInfo.setDdTaskId(UniqueIdUtil.genId());
                    //任务添加
                    taskInfoService.addDDTask(taskInfo);
                    proTaskDependance.setDdTaskId(UniqueIdUtil.genId());
                    proTaskDependance.setDdTaskId(taskInfo.getDdTaskId());
                    proTaskDependanceService.addDDProTaskDependance(proTaskDependance);
                    //resultMsg = getText("record.added", "添加完成");
                } else {
                    TaskInfo byId;
                    byId = taskInfoService.getById(taskInfo.getDdTaskId());
                    byId.setDdTaskName(el.getAttributeValue("label"));
                    byId.setDdTaskDescription(taskdesp);
                    byId.setDdTaskType(tasktype);

                    taskInfoService.updateDDTask(byId);
                    //resultMsg = getText("record.updated", "更新完成");
                    for(Iterator it =taskInfoList.iterator();it.hasNext();)
                    {
                        TaskInfo ti = (TaskInfo) it.next();
                        if(ti.getDdTaskId()==taskInfo.getDdTaskId())
                            it.remove();



                    }
                }
            } catch (Exception e) {
                writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.Fail);
            }
            el.setAttribute("oracleid", taskInfo.getDdTaskId().toString());
            System.out.println(taskInfo.getDdTaskId().toString());
        }
        /*
        for(Iterator it =taskInfoList.iterator();it.hasNext();)
        {
            TaskInfo ti = (TaskInfo) it.next();
            taskInfoService.delById(ti.getDdTaskId());
        }
*/
        //保存流程XML
        if (projectId != 0)
            saveProcessXml(doc, projectId);

        Long projectIdd = RequestUtil.getLong(request, "projectId");





        ProjectProcessAssocia projectProcessAssocia = projectProcessAssociaService.selectByProjectId(projectIdd);
        if (projectProcessAssocia != null) {
            Long processFlowId = projectProcessAssocia.getDdPrcessId();
            processFlow = processFlowService.getById(processFlowId);
            String tempXml = processFlow.getDdProcessXml();





            try{

                java.io.PrintWriter out = response.getWriter();
                out.append(tempXml);

                out.flush();
                out.close();
            }catch (Exception e)
            {
                String resultMsg = null;
                writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
            }


        }
    }



}
