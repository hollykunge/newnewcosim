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
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.system.GlobalType;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.service.system.GlobalTypeService;
import com.hotent.platform.service.system.SysFileService;
import com.hotent.platform.service.system.SysTypeKeyService;
import com.hotent.platform.service.system.SysUserService;
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
 * @author hollykunge 2016/11/14 0014.
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

    @Resource
    private GlobalTypeService globalTypeService;

    @Resource
    private SysTypeKeyService sysTypeKeyService;

    @Resource
    private SysFileService sysFileService;

    @Resource
    private SysUserService sysUserService;

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
            if (privateData.getDdDataId() == null || privateData.getDdDataId() == 0 || privateData.getDdDataId().equals("0")) {
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
    public void uploadPrivate(MultipartHttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter writer = response.getWriter();
        try {
            Long userId = ContextUtil.getCurrentUserId();
            Long dataId = RequestUtil.getLong(request, "id");
            Long typeId = RequestUtil.getLong(request, "typeId");
            String secretLevel = RequestUtil.getString(request, "secretLevel");
            ISysUser appUser = null;
            if (userId > 0) {
                appUser = sysUserService.getById(userId);
            }
            // 获取附件类型
            GlobalType globalType = null;
            if (typeId > 0) {
                globalType = globalTypeService.getById(typeId);
            }

            Map<String, MultipartFile> files = request.getFileMap();
            Iterator<MultipartFile> it = files.values().iterator();

            while (it.hasNext()) {

                Long fileId = UniqueIdUtil.genId();
                MultipartFile f = it.next();
                String oriFileName = f.getOriginalFilename();
                String extName = FileUtil.getFileExt(oriFileName);
                String fileName = oriFileName + "." + extName;

                //开始写入物理文件
                String filePath = createFilePath(AppUtil.getRealPath("/attachFiles/temp"), fileName);
                FileUtil.writeByte(filePath, f.getBytes());
                // end 写入物理文件
                // 向数据库中添加数据
                SysFile sysFile = new SysFile();
                sysFile.setFileId(fileId);
                //附件名称
                sysFile.setFileName(oriFileName.substring(0, oriFileName.lastIndexOf('.')));

                Calendar cal = Calendar.getInstance();
                Integer year = cal.get(Calendar.YEAR);
                Integer month = cal.get(Calendar.MONTH) + 1;
                //附件路径
                sysFile.setFilePath("attachFiles/temp/" + year + "/" + month + "/" + fileName);
                //附件类型
                if (globalType != null) {
                    sysFile.setTypeId(globalType.getTypeId());
                    sysFile.setFileType(globalType.getTypeName());
                } else {
                    sysFile.setTypeId(sysTypeKeyService.getByKey(GlobalType.CAT_FILE).getTypeId());
                    sysFile.setFileType("-");
                }
                // 上传时间
                sysFile.setCreatetime(new java.util.Date());
                // 扩展名
                sysFile.setExt(extName);
                //字节总数
                sysFile.setTotalBytes(f.getSize());
                // 说明
                sysFile.setNote(FileUtil.getSize(f.getSize()));
                // 当前用户的信息
                if (appUser != null) {
                    sysFile.setCreatorId(appUser.getUserId());
                    sysFile.setCreator(appUser.getUsername());
                } else {
                    sysFile.setCreator(SysFile.FILE_UPLOAD_UNKNOWN);
                }
                //总的字节数
                sysFile.setDelFlag(SysFile.FILE_NOT_DEL);
                sysFileService.add(sysFile);

                if (dataId > 0) {
                    PrivateData privateData = privateDataService.getDataById(dataId);
                    privateData.setDdDataPath(sysFile.getFilePath());
                    privateData.setDdDataLastestValue(sysFile.getFileName());
                    privateData.setDdDataReserved2(secretLevel);
                    privateDataService.update(privateData);
                }
                writer.println("{\"success\":\"true\",\"fileId\":\"" + fileId + "\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("{\"success\":\"false\"}");
        }
    }

    /**
     * 创建文件目录
     *
     * @param tempPath
     * @param fileName 文件名称
     * @return 文件的完整目录
     */
    private String createFilePath(String tempPath, String fileName) {
        File one = new File(tempPath);
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR); // 当前年份
        Integer month = cal.get(Calendar.MONTH) + 1; // 当前月份
        one = new File(tempPath + "/" + year + "/" + month);
        if (!one.exists()) {
            one.mkdirs();
        }
        return one.getPath() + File.separator + fileName;
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

        String path = getServletContext().getRealPath("/");
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path + privateData.getDdDataPath());
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
            // 设置允许用户上传文件大小,单位:位
            fu.setSizeMax(10 * 1024 * 1024);
            // 设置最多只允许在内存中存储的数据,单位:位
            fu.setSizeThreshold(4096);
            // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
            fu.setRepositoryPath(temp);
            // 开始读取上传信息
            TaskInfo taskinfo = taskInfoService.getTaskById(taskId);
            if (file == null) {
                return;
            }

            logger.info(file.getOriginalFilename());

            String name = file.getOriginalFilename();// 获取上传文件名,包括路径
            //name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
            long size = file.getSize();
            if ((name == null || name.equals("")) && size == 0) {
                return;
            }

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
