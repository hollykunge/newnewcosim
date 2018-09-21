package com.casic.datadriver.controller.index;

import com.casic.datadriver.publicClass.JsonFormat;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.model.index.IndexInfo;
import com.casic.datadriver.service.index.IndexService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.fileupload.DiskFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/18 0018.
 */


@Controller
@RequestMapping("/datadriver/index/")
public class IndexInfoController extends BaseController {
    @Resource
    private IndexService indexService;

    JsonFormat Tjson = new JsonFormat();

    /**
     * 指标保存
     *
     * @return
     */
    @RequestMapping("save")
    @Action(description = "保存指标")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        IndexInfo index = getFormObject(request);
        try {
            if (index.getDdIndexId() == null || index.getDdIndexId() == 0) {
                index.setDdIndexId(UniqueIdUtil.genId());
                indexService.addDDIndex(index);
                resultMsg = getText("record.added", "指标信息");
            } else {
                indexService.update(index);
                resultMsg = getText("record.updated", "指标信息");
            }
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }


    /**
     * @param request
     * @return
     * @throws Exception
     */
    protected IndexInfo getFormObject(HttpServletRequest request) throws Exception {

        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[]{"yyyy-MM-dd"})));

        String json = RequestUtil.getString(request, "json");
        JSONObject obj = JSONObject.fromObject(json);

        IndexInfo index = (IndexInfo) JSONObject.toBean(obj, IndexInfo.class);

        return index;
    }

    /**
     * 查询index列表
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("indexlist")
    @Action(description = "根据条件查询指标基本信息列表")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView();
        Long projectId = RequestUtil.getLong(request, "id");
        List<IndexInfo> indexInfoList = indexService.getByProjectId(projectId);
        if (indexInfoList.size() == 0) {
            mv = this.getAutoView()
                    .addObject("projectId", projectId);
        } else {
            IndexInfo indexInfo = indexInfoList.get(0);
            mv = this.getAutoView()
                    .addObject("projectId", projectId)
                    .addObject("indexContent", indexInfo.getDdIndexLastestValue())
                    .addObject("indexId", indexInfo.getDdIndexId());
        }

        return mv;
    }

    /**
     * 只读index列表
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("readonly")
    @Action(description = "任务中的只读index")
    public ModelAndView readonly(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView mv = new ModelAndView();
        Long projectId = RequestUtil.getLong(request, "id");
        List<IndexInfo> indexInfolist = indexService.getByProjectId(projectId);

        if (indexInfolist.isEmpty()) {
            mv = this.getAutoView()
                    .addObject("projectId", projectId);
        } else {
            IndexInfo indexInfo = indexInfolist.get(0);
            mv = this.getAutoView()
                    .addObject("projectId", projectId)
                    .addObject("indexContent", indexInfo.getDdIndexLastestValue());
        }
        return mv;
    }

    /**
     * 查询index列表
     *
     * @param request  the request
     * @param response the response
     * @return the list
     * @throws Exception the exception
     */
    @RequestMapping("indexjson")
    @Action(description = "指标table")
    public void indexjson(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        Long pageSize = RequestUtil.getLong(request, "pageSize");
        Long pageNumber = RequestUtil.getLong(request, "pageNumber");

        PageInfo pageinfo = new PageInfo();

        pageinfo.setPageSize((pageNumber - 1) * pageSize);
        pageinfo.setPageNumber(pageSize);

        response.setContentType("application/json");

        try {
            Long projectId = RequestUtil.getLong(request, "id");
            pageinfo.setId(projectId);
            int allnum = this.indexService.getByProjectId(projectId).size();
            List<IndexInfo> indexInfoList = this.indexService.getByProjectIdF(pageinfo);
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < indexInfoList.size(); i++) {
                IndexInfo orderDataRelation = indexInfoList.get(i);

                jsonObject.put("ddIndexLastestValue", orderDataRelation.getDdIndexLastestValue());
                jsonObject.put("ddIndexId", orderDataRelation.getDdIndexId());
                jsonObject.put("ddIndexName", orderDataRelation.getDdIndexName());
                jsonObject.put("ddIndexOptimum", orderDataRelation.getDdIndexOptimum());
                jsonObject.put("ddIndexProjectId", orderDataRelation.getDdIndexProjectId());
                jsonObject.put("ddIndexProperty", orderDataRelation.getDdIndexProperty());
                jsonObject.put("ddIndexResponsiblePersonId", orderDataRelation.getDdIndexResponsiblePersonId());
                jsonObject.put("ddIndexSensitiveness", orderDataRelation.getDdIndexSensitiveness());
                jsonObject.put("ddIndexTypeId", orderDataRelation.getDdIndexTypeId());
                jsonMembers.add(jsonObject);
            }
            json.put("total", allnum);
            json.put("rows", jsonMembers);
            String jsonstring = Tjson.formatJson(json.toString());
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

    //添加空格
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    /**
     * 指标编辑、增加
     *
     * @return
     */
    @RequestMapping("indexedit")
    @Action(description = "指标编辑或者增加")
    public ModelAndView indexedit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        try {
            IndexInfo indexInfo = new IndexInfo();
            String returnUrl = RequestUtil.getPrePage(request);
            Long projectId = RequestUtil.getLong(request, "id");
            List<IndexInfo> indexInfoList = new ArrayList<IndexInfo>();
            indexInfoList = indexService.getByProjectId(projectId);
            for (int i = 0; i > indexInfoList.size(); i++) {
                indexInfo = indexInfoList.get(i);
            }
            return getAutoView().addObject("projectId", projectId)
                    .addObject("indexInfo", indexInfo)
                    .addObject("returnUrl", returnUrl);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
            return null;
        }
    }

    /**
     * 指标编辑、增加
     *
     * @return
     */
    @RequestMapping("lastvalue")
    @Action(description = "指标值刷新")
    public void refreshlastvalue(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            String json = request.getParameter("strJson");
            JSONObject obj = JSONObject.fromObject(json);

            Map<String, Class> map = new HashMap<String, Class>();
            map.put("indexinfo", IndexInfo.class);
            IndexInfo indexinfo = (IndexInfo) JSONObject.toBean(obj, IndexInfo.class, map);
            indexService.update(indexinfo);
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 指标删除
     *
     * @return
     */
    @RequestMapping("delindex")
    @Action(description = "指标删除")
    public void delindex(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            Long indexId = RequestUtil.getLong(request, "id");
            indexService.delById(indexId);
        } catch (Exception e) {
            String resultMsg = null;
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    @RequestMapping("importIndex")
    @Action(description = "导入EXCEL文件")
    public void importIndex(@RequestParam("filename") MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMessage message = null;
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

            if (file == null)
                return;
            logger.info(file.getOriginalFilename());

            String name = file.getOriginalFilename();// 获取上传文件名,包括路径
            //name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
            long size = file.getSize();
            if ((name == null || name.equals("")) && size == 0)
                return;
            InputStream in = file.getInputStream();
            List<IndexInfo> BrandMobileInfos = indexService
                    .importIndexXml(in);
            int count = BrandMobileInfos.size();

            message = new ResultMessage(ResultMessage.Success, "成功导入" + count + "条");
        } catch (Exception ex) {
            // 改为人工刷新缓存KeyContextManager.clearPeriodCacheData(new
            // PeriodDimensions());// 清理所有缓存
            message = new ResultMessage(ResultMessage.Fail, "导入失败" + ex.getMessage());
        }

        addMessage(message, request);
        response.sendRedirect(preUrl);
    }
}
