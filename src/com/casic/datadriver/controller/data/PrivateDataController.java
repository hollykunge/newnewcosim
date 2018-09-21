package com.casic.datadriver.controller.data;

import com.casic.datadriver.controller.AbstractController;

import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.model.modelcenter.ModelCenterModel;
import com.casic.datadriver.model.task.TaskInfo;
import com.casic.datadriver.service.ModelCenterService;
import com.casic.datadriver.service.data.DataSnapShotIdService;

import com.casic.datadriver.service.data.PrivateDataService;
import com.casic.datadriver.service.task.TaskInfoService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.fileupload.DiskFileUpload;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hotent.core.util.AppUtil.getServletContext;

/**
 * 输出数据列表
 *
 * @author 杜宇坤 2016/11/14 0014.
 */
@Controller
@RequestMapping("/datadriver/privatedata/")
public class PrivateDataController extends AbstractController {

    /**
     * The privateData service.
     */
    @Resource
    private PrivateDataService privateDataService;

    @Resource
    private TaskInfoService taskInfoService;

    @Resource
    private DataSnapShotIdService dataSnapShotIdService;

    @Resource
    private ModelCenterService modelCenterService;

    /**
     * ?????????.
     *
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */
    @RequestMapping("save")
    @Action(description = "保存privateData")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        PrivateData privateData = getFormObject(request);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long id = RequestUtil.getLong(request, "id");
        TaskInfo taskInfo = taskInfoService.getById(privateData.getDdDataTaskId());

        ISysUser sysUser = ContextUtil.getCurrentUser();
        String sysName = sysUser.getFullname();
        String dateString = formatter.format(currentTime);

        try {
            if (privateData.getDdDataId() == null || privateData.getDdDataId() == 0||privateData.getDdDataId().equals("0")) {
                privateData.setDdDataId(UniqueIdUtil.genId());
                privateData.setDdDataTaskName(taskInfo.getDdTaskName());
                privateData.setDdDataPublishState((byte) 0);
                privateDataService.addSingleData(privateData);

                for (int i = 0; i < privateData.getPrivateDataList().size(); i++) {
                    privateData.getPrivateDataList().get(i).setDdDataId(UniqueIdUtil.genId());
                    privateData.getPrivateDataList().get(i).setDdDataTaskId(privateData.getDdDataTaskId());
                    privateData.getPrivateDataList().get(i).setDdDataParentId(privateData.getDdDataId());
                    privateData.getPrivateDataList().get(i).setDdDataPublishState((byte) 0);
                    privateData.getPrivateDataList().get(i).setDdDataOrderState((short) 0);
                    privateData.getPrivateDataList().get(i).setDdDataIsSubmit((short) 0);
                    privateData.getPrivateDataList().get(i).setDdDataIsLeaf((short) 0);
                    privateData.getPrivateDataList().get(i).setDdDataCreateTime(currentTime);
                    privateData.getPrivateDataList().get(i).setDdDataTaskName(taskInfo.getDdTaskName());
                    privateData.getPrivateDataList().get(i).setDdDataCreatorId(ContextUtil.getCurrentUserId());

                    privateDataService.addSingleData(privateData.getPrivateDataList().get(i));
                }
                resultMsg = getText("record.added", "数据信息");
            } else {
                privateDataService.updateData(privateData);
                resultMsg = getText("record.updated", "数据信息");
            }
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    private PrivateData getFormObject(HttpServletRequest request) throws Exception {
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[]{"yyyy-MM-dd"})));
        String name = RequestUtil.getString(request, "ddDataName1");
//        String name1 = RequestUtil.getString(request, "ddDataName");
        String json = RequestUtil.getString(request, "json");
        JSONObject obj = JSONObject.fromObject(json);

        Map classMap = new HashMap();
        classMap.put("privateDataList", PrivateData.class);
        PrivateData privateData = (PrivateData) JSONObject.toBean(obj, PrivateData.class, classMap);
        privateData.setDdDataName(name);

        return privateData;
    }

    /**
     * Query privateData basic info list.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */

    @RequestMapping("list")
    @Action(description = "私有数据列表")
    public ModelAndView queryPrivateDataBasicInfoList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long id = RequestUtil.getLong(request, "id");
        List<PrivateData> privateDataInfoList = new ArrayList<PrivateData>();
        TaskInfo taskInfo = taskInfoService.getById(id);
        if (id == null || id == 0) {
            privateDataInfoList = privateDataService.getAll();
        } else {
//            privateDataInfoList = privateDataService.queryPrivateDataByddTaskID(id);
        }
        ModelAndView mv = this.getAutoView().addObject("privateDataList", privateDataInfoList)
                .addObject("taskInfo", taskInfo);
        return mv;

    }


    /**
     * 添加私有数据
     *
     * @param request  the request
     * @param response the response
     * @return the add
     * @throws Exception the exception
     */
    @RequestMapping("addprivatedata")
    @Action(description = "添加私有")
    public ModelAndView addprivatedata(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String resultMessage = "私有数据添加";
        ModelAndView mv = new ModelAndView();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Long id = RequestUtil.getLong(request, "id");
            TaskInfo taskInfo = taskInfoService.getById(id);

            ISysUser sysUser = ContextUtil.getCurrentUser();
            String sysName = sysUser.getFullname();

            String dateString = formatter.format(currentTime);
            List<ModelCenterModel> modelCenterModelList = modelCenterService.getByTaskId(id);
            mv = this.getAutoView().addObject("taskInfo", taskInfo)
                    .addObject("currentTime", dateString)
                    .addObject("sysName", sysName)
                    .addObject("modelCenterModelList", modelCenterModelList);
            writeResultMessage(response.getWriter(), resultMessage, ResultMessage.Success);
        } catch (Exception ex) {
            writeResultMessage(response.getWriter(), resultMessage + "," + ex.getMessage(), ResultMessage.Fail);
        }
        return mv;
    }

    /**
     * 上传私有数据文件
     *
     * @param request  the request
     * @param response the response
     * @return the add
     * @throws Exception the exception
     */
    @RequestMapping("uploadPrivate")
    @Action(description = "上传文件")
    public void uploadPrivate(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long dataId = RequestUtil.getLong(request, "id");

        PrivateData privateData = privateDataService.getDataById(dataId);

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //记录上传过程起始时的时间，用来计算上传时间
                //   int pre = (int) System.currentTimeMillis();
                //取得上传文件
                MultipartFile file1 = multiRequest.getFile(iter.next());

                if (file1 != null) {
                    //取得当前上传文件的文件名称
                    String myFileName = file1.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        System.out.println(myFileName);
                        //重命名上传后的文件名
                        String fileName = file1.getOriginalFilename();
                        //定义上传路径//
                        ////
                        //取得根目录路径
//                        String rootPath=getClass().getResource("/").getFile().toString();

                        ////
                        String realPath = getServletContext().getRealPath("/");
                        String path = realPath + "/uploadPrivateData/" + dataId + "/" + myFileName;
//                        String path ="d:"+ "/major/" + major + "/" + ddToolVersion +"_"+myFileName;
                        privateData.setDdDataPath("/uploadPrivateData/" + dataId + "/" + myFileName);
                        privateData.setDdDataLastestValue(myFileName);
//                        privateData.setdd(myFileName);
                        File file = new File(path);
                        //创建目录

                        if (!file.exists() && !file.isDirectory()) {
                            System.out.println("//不存在");
                            file.mkdirs();
                        } else {
                            System.out.println("//目录存在");
                        }
                        File localFile = new File(path);
                        file1.transferTo(localFile);

                    }
                    privateDataService.update(privateData);
//                    m.setDdToolId(UniqueIdUtil.genId());
////                    m.setDdToolName(myFileName);
//
//                    tservice.add(m);

//                    response.sendRedirect("toollist1.ht?major="+major);//根据实际情况跳转w
                } else {

                }
                //记录上传该文件后的时间
                //    int finaltime = (int) System.currentTimeMillis();
                //     System.out.println(finaltime - pre);
            }
        }
//        ModelAndView mv = this.getAutoView().addObject("dataStructId", dataId);
//        return mv;
    }

    /**
     * 下载私有数据文件
     *
     * @param request  the request
     * @param response the response
     * @return the add
     * @throws Exception the exception
     */

    @RequestMapping("getPrivatefile")
    @Action(description = "下载数据文件")
    public void getPrivatefile(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
        Long dataId = RequestUtil.getLong(request, "id");
        PrivateData privateData = privateDataService.getDataById(dataId);
//

        String path = getServletContext().getRealPath("/");
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path+privateData.getDdDataPath());
            // 取得文件名。
            String filename = file.getName();
            filename = URLEncoder.encode(filename, "UTF-8");
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 编辑任务
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("edit")
    @Action(description = "数据编辑")
    public ModelAndView edit(HttpServletRequest request) throws Exception {

        String time = "2017年12月10日";
        Long id = RequestUtil.getLong(request, "id");
        PrivateData privateData = privateDataService.getById(id);
        long taskId = privateData.getDdDataTaskId();
        TaskInfo taskInfo = taskInfoService.getById(taskId);
        List<TaskInfo> taskInfoList = taskInfoService.getListByResponceIdAndState1(taskInfo.getDdTaskResponsiblePerson());

        List<ModelCenterModel> modelCenterModelList = modelCenterService.getByTaskId(taskId);
        return getAutoView().addObject("privateData", privateData)
                .addObject("taskInfoList", taskInfoList)
                .addObject("modelCenterModelList", modelCenterModelList);
    }

    /**
     * Del.
     *
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */
    @RequestMapping("del")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.del(request, response, this.privateDataService);
    }


    /**
     * ???????????.
     *
     * @param bin the bin
     */
    @InitBinder
    public void initBinder(ServletRequestDataBinder bin) {
        bin.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @RequestMapping("importBrandSort")
    @Action(description = "导入EXCEL文件")
    public void importBrandSort(@RequestParam("filename") MultipartFile file,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
        Long taskId = RequestUtil.getLong(request, "id");
        Long projectId = RequestUtil.getLong(request, "projectId");
        String preUrl = RequestUtil.getPrePage(request);
        try {
            String temp = request.getSession().getServletContext()
                    .getRealPath(File.separator)
                    + "temp"; // 临时目录
            File tempFile = new File(temp);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            DiskFileUpload fu = new DiskFileUpload();
            fu.setSizeMax(10 * 1024 * 1024); // 设置允许用户上传文件大小,单位:位
            fu.setSizeThreshold(4096); // 设置最多只允许在内存中存储的数据,单位:位
            fu.setRepositoryPath(temp); // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
            // 开始读取上传信息
            TaskInfo taskinfo = taskInfoService.getUserIdbyTaskId(taskId);
            if (file == null)
                return;
            logger.info(file.getOriginalFilename());

            String name = file.getOriginalFilename();// 获取上传文件名,包括路径
            //name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
            long size = file.getSize();
            if ((name == null || name.equals("")) && size == 0)
                return;
            InputStream in = file.getInputStream();
            int count = privateDataService
                    .importBrandPeriodSort(in, taskId, projectId, taskinfo.getDdTaskName());
//            int count = BrandMobileInfos.size();

            message = new ResultMessage(ResultMessage.Success, "成功导入" + count + "条");
        } catch (Exception ex) {
            // 改为人工刷新缓存KeyContextManager.clearPeriodCacheData(new
            // PeriodDimensions());// 清理所有缓存
            message = new ResultMessage(ResultMessage.Fail, "导入失败" + ex.getMessage());
        }

        addMessage(message, request);
        response.sendRedirect(preUrl);
    }



    /**
     * 输入数据.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("inputData")
    @Action(description = "输入数据")
    public void inputData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long taskId = RequestUtil.getLong(request, "taskId");
        Long projectId = RequestUtil.getLong(request, "projectId");
        String jsonString = privateDataService.getInputDataByTaskId(projectId, taskId);
        PrintWriter out = response.getWriter();
        out.append(jsonString);
        out.flush();
        out.close();
    }

    /**
     * 输出数据.
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("outputData")
    @Action(description = "输出数据")
    public void outputData(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Long taskId = RequestUtil.getLong(request, "taskId");
        String jsonString = privateDataService.getOutputDataByTaskId(taskId).toString();
        PrintWriter out = response.getWriter();
        out.append(jsonString);
        out.flush();
        out.close();
    }
}
