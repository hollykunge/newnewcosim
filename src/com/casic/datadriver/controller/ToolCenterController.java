package com.casic.datadriver.controller;

import com.casic.datadriver.model.major.Major;
import com.casic.datadriver.model.tool.ToolCenterModel;
import com.casic.datadriver.publicClass.JsonFormat;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.service.ToolCenterService;
import com.casic.datadriver.service.major.MajorService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.hotent.core.util.AppUtil.getServletContext;

/**
 * Created by 忠 on 2017/1/13.
 */
@Controller
@RequestMapping("/datadriver/tool/")
public class ToolCenterController extends BaseController {
    @Resource
    private ToolCenterService tservice;

    @Resource
    private MajorService majormservice;

    JsonFormat Tjson = new JsonFormat();

    @RequestMapping("save")
    @Action(description = "保存工具")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String major = RequestUtil.getString(request, "major");
        String ddToolName = RequestUtil.getString(request, "ddToolName");
        String ddToolVersion = RequestUtil.getString(request, "ddToolVersion");
        String ddToolBf = RequestUtil.getString(request, "ddToolBf");
        String ddToolBf2 = RequestUtil.getString(request, "ddToolBf2");

        ToolCenterModel m = new ToolCenterModel();

        m.setDdToolName(ddToolName);
        m.setDdToolVersion(ddToolVersion);
        m.setDdToolBf(ddToolBf);
        m.setDdToolBf2(ddToolBf2);
        m.setDdToolMajor(major);

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
                //取得上传文件
                MultipartFile file1 = multiRequest.getFile(iter.next());

                if (file1 != null) {
                    //取得当前上传文件的文件名称
                    String myFileName = file1.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        System.out.println(myFileName);
                        //重命名上传后的文件名
                        String fileName = "demoUpload" + file1.getOriginalFilename();
                        //定义上传路径//
                        String realPath = getServletContext().getRealPath("/");
                        String path = realPath + "/major/" + major + "/" + ddToolVersion + "_" + myFileName;
//                        String path ="d:"+ "/major/" + major + "/" + ddToolVersion +"_"+myFileName;
                        m.setDdToolUrl("/major/" + major + "/" + ddToolVersion + "_" + myFileName);
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
                    m.setDdToolId(UniqueIdUtil.genId());
                    tservice.add(m);
                }
            }
        }
    }

    protected <T> T getFormObject(HttpServletRequest request, Class<T> clazz) throws Exception {
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[]{"yyyy-MM-dd"})));
        String json = RequestUtil.getString(request, "json");
        JSONObject obj = JSONObject.fromObject(json);
        return (T) JSONObject.toBean(obj, clazz);
    }

    private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
    boolean flag = false;
    File file;

    public boolean DeleteFolder(String deletePath) {// 根据路径删除指定的目录或文件，无论存在与否
        flag = false;
        if (deletePath.matches(matches)) {
            file = new File(deletePath);
            if (!file.exists()) {// 判断目录或文件是否存在
                return flag; // 不存在返回 false
            } else {

                if (file.isFile()) {// 判断是否为文件
                    return deleteFile(deletePath);// 为文件时调用删除文件方法
                } else {
                    return deleteDirectory(deletePath);// 为目录时调用删除目录方法
                }
            }
        } else {
            System.out.println("要传入正确路径！");
            return false;
        }
    }

    public boolean deleteFile(String filePath) {// 删除单个文件
        flag = false;
        file = new File(filePath);
        if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
            file.delete();// 文件删除
            flag = true;
        }
        return flag;
    }

    public boolean deleteDirectory(String dirPath) {// 删除目录（文件夹）以及目录下的文件
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        File dirFile = new File(dirPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
        for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
            if (files[i].isFile()) {// 删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                System.out.println(files[i].getAbsolutePath() + " 删除成功");
                if (!flag)
                    break;// 如果删除失败，则跳出
            } else {// 运用递归，删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;// 如果删除失败，则跳出
            }
        }
        if (!flag)
            return false;
        if (dirFile.delete()) {// 删除当前目录
            return true;
        } else {
            return false;
        }
    }

    // 创建单个文件
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {// 判断文件是否存在
            System.out.println("目标文件已存在" + filePath);
            return false;
        }
        if (filePath.endsWith(File.separator)) {// 判断文件是否为目录
            System.out.println("目标文件不能为目录！");
            return false;
        }
        if (!file.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
            // 如果目标文件所在的文件夹不存在，则创建父文件夹
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {// 判断创建目录是否成功
                System.out.println("创建目标文件所在的目录失败！");
                return false;
            }
        }
        try {
            if (file.createNewFile()) {// 创建目标文件
                System.out.println("创建文件成功:" + filePath);
                return true;
            } else {
                System.out.println("创建文件失败！");
                return false;
            }
        } catch (IOException e) {// 捕获异常
            e.printStackTrace();
            System.out.println("创建文件失败！" + e.getMessage());
            return false;
        }
    }

    // 创建目录
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            System.out.println("创建目录失败，目标目录已存在！");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + destDirName);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }

    // 创建临时文件
    public static String createTempFile(String prefix, String suffix,
                                        String dirName) {
        File tempFile = null;
        if (dirName == null) {// 目录如果为空
            try {
                tempFile = File.createTempFile(prefix, suffix);// 在默认文件夹下创建临时文件
                return tempFile.getCanonicalPath();// 返回临时文件的路径
            } catch (IOException e) {// 捕获异常
                e.printStackTrace();
                System.out.println("创建临时文件失败：" + e.getMessage());
                return null;
            }
        } else {
            // 指定目录存在
            File dir = new File(dirName);// 创建目录
            if (!dir.exists()) {
                // 如果目录不存在则创建目录
                if (createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                tempFile = File.createTempFile(prefix, suffix, dir);// 在指定目录下创建临时文件
                return tempFile.getCanonicalPath();// 返回临时文件的路径
            } catch (IOException e) {// 捕获异常
                e.printStackTrace();
                System.out.println("创建临时文件失败!" + e.getMessage());
                return null;
            }
        }
    }

    @RequestMapping("add")
    @Action(description = "增加工具")
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {


    }

    @RequestMapping("showtree")
    @Action(description = "专业树形结构显示")
    @ResponseBody
    public void showtree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jsonTree = majormservice.getJsonTree();
        PrintWriter out = response.getWriter();
        out.append(jsonTree);
        out.flush();
        out.close();
    }

    public List<Major> buildTree(List<Major> root) {
        for (int i = 0; i < root.size(); i++) {
            List<Major> children = majormservice.findByPid(root.get(i).getDdMajorId()); //查询某节点的子节点（获取的是list）
            buildTree(children);
            root.get(i).setChildren(children);
        }
        return root;

    }

    @RequestMapping("remove")
    @Action(description = "删除工具 ")
    public void remove(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Long id = RequestUtil.getLong(request, "id");
        this.tservice.deltool(id);
    }


    /**
     * 2016/12/19/修改
     * 返回任务发布订购数据列表
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("toollist")
    @Action(description = "返回任务发布订购数据列表")
    public ModelAndView querysubmitpublish(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String major = new String(RequestUtil.getString(request, "major").getBytes("ISO-8859-1"), "UTF-8");
        List<ToolCenterModel> toolList = tservice.querytoolBymajor(major);
        int toolLength = toolList.size();
        List<ToolCenterModel> toolList1 = null;

        ModelAndView mv = this.getAutoView().addObject("ToolList", toolList1);
        return mv;
    }

    @RequestMapping("gettool")
    @Action(description = "工具列表")
    public void gettool(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
//        String path = servletContext.getRealPath("/");
//        String major= new String(RequestUtil.getString(request, "major").getBytes("ISO-8859-1"));
        String major = new String(RequestUtil.getString(request, "major").getBytes("ISO-8859-1"), "UTF-8");
        String name = new String(RequestUtil.getString(request, "name").getBytes("ISO-8859-1"), "UTF-8");
        String path = getServletContext().getRealPath("/");
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path + major);
            // 取得文件名。
            String filename = file.getName();
            filename = URLEncoder.encode(filename, "UTF-8");
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path + major));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
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

    @RequestMapping("showtools")
    @Action(description = "工具列表")
    public void querysubmitpublish1(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();

        String search = new String(RequestUtil.getString(request, "searchText").getBytes("ISO-8859-1"), "UTF-8");

        String major = new String(RequestUtil.getString(request, "major").getBytes("ISO-8859-1"),"UTF-8");
//        String major= new String(RequestUtil.getString(request, "major").getBytes("ISO-8859-1"),"UTF-8");
        Long pageSize = RequestUtil.getLong(request, "pageSize");
        Long pageNumber = RequestUtil.getLong(request, "pageNumber");
        int son = RequestUtil.getInt(request, "son");
        response.setContentType("application/json");
        ToolCenterModel temp;

        PageInfo pageinfo = new PageInfo();
        pageinfo.setName(major);
        pageinfo.setBf2(search);
        pageinfo.setPageSize((pageNumber - 1) * pageSize);
        pageinfo.setPageNumber(pageSize);
        int Allnum = 0;
        try {
            List<ToolCenterModel> mylist = new ArrayList<ToolCenterModel>();
            List<ToolCenterModel> toolList1 = new ArrayList<ToolCenterModel>();
            JSONObject jsonObject = new JSONObject();
            if (son == 1) {
                Allnum = this.tservice.querytoolBymajor(major).size();
                //mylist = this.tservice.querytoolBymajor(major);
                mylist = this.tservice.querytoolBymajor(major);
                int toolLength = mylist.size();

                for (int num = 0; num < toolLength; num++) {
                    boolean T = false;
                    if (toolList1.isEmpty()) {
                        temp = mylist.get(num);
                        toolList1.add(temp);
                    }
                    for (int i = 0; i < toolList1.size(); i++) {
                        if (toolList1.get(i).getDdToolName().equals(mylist.get(num).getDdToolName())) {
                            T = true;
                        }
                    }
                    if (T == false) {
                        temp = mylist.get(num);
                        toolList1.add(temp);
                    }

                }
//                int total = mylist.size();
////                mylist = mylist.Skip(pageSize).Take(pageNumber).ToList();
//                return Json(new { total = total, rows = rows }, JsonRequestBehavior.AllowGet);

            }
            if (son == 2) {
                Allnum = this.tservice.querytoolByname(major).size();
//                toolList1 = this.tservice.querytoolBymajorF(pageinfo);
                toolList1 = this.tservice.querytoolByname(major);
            }
            for (int i = 0; i < toolList1.size(); i++) {
                ToolCenterModel mymodel = toolList1.get(i);
                jsonObject.put("ToolID", mymodel.getDdToolId());
                jsonObject.put("ToolName", mymodel.getDdToolName());
                jsonObject.put("ToolUrl", mymodel.getDdToolUrl());
                jsonObject.put("ToolVersion", mymodel.getDdToolVersion());
                jsonObject.put("Toolbz", mymodel.getDdToolBf());
                jsonMembers.add(jsonObject);
            }
//            json.put("total", Allnum);
//            json.put("rows", jsonMembers);

            String jsonstring = Tjson.formatJson(jsonMembers.toString());
            System.out.println(json.toString());

            PrintWriter out = null;
            out = response.getWriter();
            out.append(jsonstring);
            out.flush();
            out.close();
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }
}
