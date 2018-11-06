package com.casic.datadriver.controller.datacenter;


import com.casic.datadriver.controller.AbstractController;
import com.casic.datadriver.model.data.*;
import com.casic.datadriver.model.project.Project;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.publicClass.JsonFormat;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.service.data.*;
import com.casic.datadriver.service.project.ProjectService;
import com.casic.datadriver.service.task.TaskInfoService;
import com.casic.datadriver.service.task.TaskStartService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 2016/11/14 0014.
 */
@Controller
@RequestMapping("/datadriver/datacenter/")
public class DataCenterController extends AbstractController {

    @Resource
    private TaskInfoService taskInfoService;
    @Resource
    private PrivateDataService privateDataService;
    @Resource
    private OrderDataRelationService orderDataRelationService;
    @Resource
    private TaskStartService taskStartService;
    @Resource
    private ProjectService projectService;
    @Resource
    private DataSnapshotService dataSnapshotService;
    @Resource
    private DataSnapInfoIdService dataSnapInfoIdService;
    @Resource
    private PrivateVersionService privateVersionService;
    @Resource
    private TaskVerMapService taskVerMapService;

    JsonFormat Tjson = new JsonFormat();
    /**
     * 2016/12/19/修改
     * 取得项目树
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("projectTree")
    public void projectTree(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String projectTreeJson = projectService.getProjectTree();
        PrintWriter out = response.getWriter();
        out.append(projectTreeJson);
        out.flush();
        out.close();
    }

    /**
     * 排序
     */
    class desc implements Comparator<OrderDataRelation> {
        @Override
        public int compare(OrderDataRelation u1, OrderDataRelation u2) {
            return -(u1.getDdDataName().compareTo(u2.getDdDataName()));
        }
    }

    class asc implements Comparator<OrderDataRelation> {
        @Override
        public int compare(OrderDataRelation u1, OrderDataRelation u2) {
            return u1.getDdDataName().compareTo(u2.getDdDataName());
        }
    }


    @RequestMapping("getReleasedatanew")
    @Action(description = "获得发布数据列表")
    public void getReleasedatanew(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long ddTaskId = RequestUtil.getLong(request, "id");
        Long pageSize = RequestUtil.getLong(request, "pageSize");
        Long pageNumber = RequestUtil.getLong(request, "pageNumber");
        Long a = pageSize * (pageNumber - 1);
        Long b = pageSize * (pageNumber);

        int DataTypenum = RequestUtil.getInt(request, "DataTypenum");
        String search = new String(RequestUtil.getString(request, "searchText").getBytes("ISO-8859-1"), "UTF-8");
        String DataType = "";
        switch (DataTypenum) {
            case 1:
                DataType = "模型";
                break;
            case 2:
                DataType = "文件";
                break;
            case 3:
                DataType = "结构型数据";
                break;
            case 4:
                DataType = "";
                break;
        }


        PageInfo pageinfo = new PageInfo();
        pageinfo.setId(ddTaskId);
        pageinfo.setBf2(DataType);

        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();


        List<OrderDataRelation> orderDataRelation_list = this.orderDataRelationService.getPublishDataRelationList(ddTaskId);

        List<PrivateData> taskPrivateDatas = null;
        int allnum = 0;

        if (DataType == null || DataType.length() <= 0) {
            allnum = taskPrivateDatas.size();
        } else {
            allnum = taskPrivateDatas.size();
        }
        if (b > taskPrivateDatas.size()) {
            b = Long.valueOf(taskPrivateDatas.size());
        }

        for (int i = Math.toIntExact(a); i < b; i++) {
            PrivateData mymode = taskPrivateDatas.get(i);
            jsonObject.put("DdDataName", mymode.getDdDataName());
            jsonObject.put("DdDataLastestValue", mymode.getDdDataLastestValue());
            switch (mymode.getDdDataType()) {
                case 3:
                    DataType = "结构型数据";
                    break;
                case 2:
                    DataType = "文件";
                    break;
                case 1:
                    DataType = "模型";
                    break;
                case 4:
                    DataType = "";
                    break;
            }
            jsonObject.put("DdDataType", DataType);
            jsonObject.put("DdDataPath", mymode.getDdDataPath());
            jsonObject.put("DdDataCreateTime", mymode.getDdDataCreateTime());
            jsonObject.put("DdDataDescription", mymode.getDdDataDescription());
            jsonObject.put("DdDataId", mymode.getDdDataId());
            jsonMembers.add(jsonObject);
        }

        json.put("total", allnum);
        json.put("rows", jsonMembers);
        String jsonstring = JsonFormat.formatJson(json.toString());
        System.out.println(json.toString());
        PrintWriter out = null;
        out = response.getWriter();
        out.append(jsonstring);
        out.flush();
        out.close();

    }

    /**
     * 分页函数
     * by 杜
     * 数据库分页
     */
    private PageInfo pagination(Long pageSize, Long pageNumber, Long id) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize((pageNumber - 1) * pageSize);
        pageInfo.setPageNumber(pageSize);
        pageInfo.setId(id);
        return pageInfo;
    }

    @RequestMapping("getReleasedata")
    @Action(description = "获得发布数据列表")
    public void getOrderdata(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String A = RequestUtil.getString(request, "sortName");
        Long ddTaskId = RequestUtil.getLong(request, "id");
        Long pageSize = RequestUtil.getLong(request, "pageSize");
        Long pageNumber = RequestUtil.getLong(request, "pageNumber");

        PageInfo pageinfo = pagination(pageSize, pageNumber, ddTaskId);

        List<OrderDataRelation> orderDataRelation_list = this.orderDataRelationService.getPublishDataRelationListF(pageinfo);
        int allnum = this.orderDataRelationService.getPublishDataRelationList(ddTaskId).size();
        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();

        for (OrderDataRelation orderDataRelation : orderDataRelation_list) {
            Long ddDataId = orderDataRelation.getDdDataId();

            List<PrivateData> taskPrivateDatas = null;

            for (int i = 0; i < taskPrivateDatas.size(); i++) {
                PrivateData mymode = taskPrivateDatas.get(i);
                jsonObject.put("DdDataName", mymode.getDdDataName());
                jsonObject.put("DdDataLastestValue", mymode.getDdDataLastestValue());
                jsonObject.put("DdDataType", mymode.getDdDataType());
                jsonObject.put("DdDataCreateTime", mymode.getDdDataCreateTime());
                jsonObject.put("DdDataDescription", mymode.getDdDataDescription());
                jsonObject.put("DdDataId", mymode.getDdDataId());
                jsonMembers.add(jsonObject);
            }

        }
        json.put("total", allnum);
        json.put("rows", jsonMembers);
        String jsonstring = JsonFormat.formatJson(json.toString());
        System.out.println(json.toString());
        PrintWriter out = null;
        out = response.getWriter();
        out.append(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("getOrderdata")
    @Action(description = "获得订购数据列表")
    public void getReleasedata(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long ddTaskId = RequestUtil.getLong(request, "id");
        //获得订购数据列表
        Long pageSize = RequestUtil.getLong(request, "pageSize");
        Long pageNumber = RequestUtil.getLong(request, "pageNumber");
        PageInfo pageinfo = new PageInfo();
        pageinfo.setPageSize((pageNumber - 1) * pageSize);
        pageinfo.setPageNumber((pageNumber - 1) * pageSize + pageSize);
        pageinfo.setId(ddTaskId);

        List<OrderDataRelation> orderDataRelation_list = this.orderDataRelationService.getOrderDataRelationListF(pageinfo);
        List<PrivateData> privateData = new ArrayList<PrivateData>();

        JSONObject jsonObject = new JSONObject();
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();

        for (OrderDataRelation orderDataRelation : orderDataRelation_list) {
            Long ddDataId = orderDataRelation.getDdDataId();

            List<PrivateData> taskPrivateDatas = null;

            for (int i = 0; i < taskPrivateDatas.size(); i++) {
                PrivateData mymodel = taskPrivateDatas.get(i);
                jsonObject.put("DdDataName", mymodel.getDdDataName());
                jsonObject.put("DdDataLastestValue", mymodel.getDdDataLastestValue());
                jsonObject.put("DdDataType", mymodel.getDdDataType());
                jsonObject.put("DdDataCreateTime", mymodel.getDdDataCreateTime());
                jsonObject.put("DdDataDescription", mymodel.getDdDataDescription());
                jsonObject.put("DdDataId", mymodel.getDdDataId());
                jsonMembers.add(jsonObject);
            }

        }
        json.put("total", orderDataRelation_list.size());
        json.put("rows", jsonMembers);
        String jsonstring = JsonFormat.formatJson(json.toString());
        System.out.println(json.toString());
        PrintWriter out = null;
        out = response.getWriter();
        out.append(jsonstring);
        out.flush();
        out.close();
    }

    /**
     * 项目快照
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("datasnapshot")
    @Action(description = "私有数据数据快照")
    public void datasnapshot(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String preUrl = RequestUtil.getPrePage(request);
        Long projectId = RequestUtil.getLong(request, "projectId");
        DataSnapInfoId dataSnapInfoId = new DataSnapInfoId();

        dataSnapInfoId.setDdDataSnapShotId(UniqueIdUtil.genId());
        dataSnapInfoId.setDdSnapShotPersonId(ContextUtil.getCurrentUser().getUserId());

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        dataSnapInfoId.setDdSnapShotTime(dateString);

        dataSnapInfoId.setDdProjectId(projectId);

        dataSnapInfoIdService.add(dataSnapInfoId);
        List<TaskInfo> taskInfoList = taskInfoService.queryTaskInfoByProjectId(projectId);

        for (int i = 0; i < taskInfoList.size(); i++) {
            List<PrivateData> privateDataList = null;
            for (int j = 0; j < privateDataList.size(); j++) {
                DataSnapshot dataSnapshot = new DataSnapshot();

                dataSnapshot.setDdDataSnapshotId(UniqueIdUtil.genId());
                dataSnapshot.setDdDataId(privateDataList.get(j).getDdDataId());
                dataSnapshot.setDdDataValue(privateDataList.get(j).getDdDataLastestValue());
                dataSnapshot.setDdSnapshotTime(dateString);

                dataSnapshotService.add(dataSnapshot);
            }
        }
        response.sendRedirect(preUrl);
    }


    /**
     * 数据快照列表.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("datasnapshotlist")
    @Action(description = "根据条件查询项目基本信息列表")
    public ModelAndView datasnapshotlist(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long ddTaskId = RequestUtil.getLong(request, "ddTaskId");
        String ddSnapShotTime = RequestUtil.getString(request, "ddSnapShotTime");

        List<PrivateData> privateDataList = null;
        List<DataSnapshot> dataSnapshotList = new ArrayList<DataSnapshot>();

        ModelAndView mv = this.getAutoView().addObject("dataSnapshotList",
                dataSnapshotList);
        return mv;
    }


    /**
     * ?????????.
     *
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */

    @RequestMapping("showdatashot")
    @Action(description = "查看数据快照")
    public ModelAndView showdatashot(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;


        Long ddDataSnapshotId = RequestUtil.getLong(request, "ddDataSnapshotId");
        DataSnapshot dataSnapshot = dataSnapshotService.getById(ddDataSnapshotId);
        Long ddDataId = dataSnapshot.getDdDataId();
        PrivateData privateData = privateDataService.getById(ddDataId);

        DataShot dataShot = new DataShot();
        dataShot.setDdSnapshotPersonId(dataSnapshot.getDdSnapshotPersonId());
        dataShot.setDdDataValue(dataSnapshot.getDdDataValue());
        dataShot.setDdDataTag(dataSnapshot.getDdDataTag());
        dataShot.setDdSnapshotTime(dataSnapshot.getDdSnapshotTime());

        ModelAndView mv = this.getAutoView().addObject("dataShot", dataShot);
        return mv;

    }

    /**
     * 数据快照列表.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("snapshotlist")
    @Action(description = "返回所有快照列表")
    public ModelAndView snapshotlist(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        QueryFilter queryFilter = new QueryFilter(request, "ProjectItem");
        List<DataSnapInfoId> dataSnapInfoIdList = this.dataSnapInfoIdService.getAll();

        ModelAndView mv = this.getAutoView().addObject("snapshotList",
                dataSnapInfoIdList);
        return mv;
    }

    /**
     * 数据快照列表.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("snapshottasklist")
    @Action(description = "返回查看项目的任务列表")
    public ModelAndView snapshottasklist(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long ddDataSnapShotId = RequestUtil.getLong(request, "ddDataSnapShotId");

        DataSnapInfoId dataSnapInfoId = dataSnapInfoIdService.getById(ddDataSnapShotId);

        List<TaskInfo> taskInfoList = taskInfoService.queryTaskInfoByProjectId(dataSnapInfoId.getDdProjectId());
        ModelAndView mv = this.getAutoView().addObject("snapshotTaskList",
                taskInfoList).addObject("ddSnapShotTime", dataSnapInfoId.getDdSnapShotTime());
        return mv;
    }

    /**
     * 项目数据订阅关系.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("proorderrelation")
    @Action(description = "返回查看项目的任务列表")
    public ModelAndView proorderrelation(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long projectId = RequestUtil.getLong(request, "id");
        ModelAndView mv = this.getAutoView().addObject("projectId", projectId);
        return mv;
    }

    /**
     * 任务树跳转列表
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("publishorderdata")
    @Action(description = "版本信息")
    public ModelAndView publishorderdata(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long taskId = RequestUtil.getLong(request, "taskId");
        ModelAndView mv = this.getAutoView().addObject("taskId", taskId);
        return mv;

    }

    /**
     * 数据中心，根据任务id获取数据版本信息.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("getVersionList")
    @Action(description = "版本信息")
    public void getVersionList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long taskId = RequestUtil.getLong(request, "taskId");
        String jsonString = taskVerMapService.getListByTaskId(taskId);
        PrintWriter out = response.getWriter();
        out.append(jsonString);
        out.flush();
        out.close();
    }
    /**
     * 数据版本跳转列表
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("dataversion")
    @Action(description = "版本信息")
    public ModelAndView dataversion(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long versionId = RequestUtil.getLong(request, "versionId");
        ModelAndView mv = this.getAutoView().addObject("versionId", versionId);
        return mv;
    }
    /**
     * 数据中心，根据版本id获取数据信息.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("getListByVerId")
    @Action(description = "根据版本ID获取数据信息")
    public void getListByVerId(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long versionId = RequestUtil.getLong(request, "versionId");
        String jsonString = privateVersionService.getListByVerId(versionId);
        PrintWriter out = response.getWriter();
        out.append(jsonString);
        out.flush();
        out.close();
    }

}
