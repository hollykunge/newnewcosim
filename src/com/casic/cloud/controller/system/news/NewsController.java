package com.casic.cloud.controller.system.news;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.casic.datadriver.publicClass.JsonFormat;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.system.news.News;
import com.casic.cloud.service.system.news.NewsService;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.casic.datadriver.publicClass.JsonFormat;

/**
 * <pre>
 * 对象功能:新闻公告 控制器类
 * 开发公司:中国航天科工集团二院
 * 开发人员:hollykunge
 * 创建时间:2013-04-12 21:43:57
 * </pre>
 */
@Controller
/**
 * Mapping路径和返回jsp路径映射规则：/package/sub-package/pre+method.jsp
 * @author hollykunge
 *
 */
@RequestMapping("/cloud/system/news/")
public class NewsController extends BaseController {
    @Resource
    private NewsService newsService;

    JsonFormat jsonFormat = new JsonFormat();

    /**
     * 添加或更新新闻公告。
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("save")
    @Action(description = "添加或更新新闻公告")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resultMsg = null;
        News news = getFormObject(request);
        try {
            //获取发布者
            news.setPublisher(ContextUtil.getCurrentUser().getFullname());
            //当前发布时间
            news.setPublishtime(new Date());

            if (news.getId() == null || news.getId() == 0) {
                news.setId(UniqueIdUtil.genId());
                newsService.add(news);
                resultMsg = getText("record.added", "新闻公告");
            } else {
                newsService.update(news);
                resultMsg = getText("record.updated", "新闻公告");
            }
            writeResultMessage(response.getWriter(), resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }

    /**
     * 取得 News实体
     *
     * @param request
     * @return
     * @throws Exception
     */
    protected News getFormObject(HttpServletRequest request) throws Exception {

        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[]{"yyyy-MM-dd"})));

        String json = RequestUtil.getString(request, "json");
        JSONObject obj = JSONObject.fromObject(json);

        News news = (News) JSONObject.toBean(obj, News.class);

        return news;
    }

    /**
     * 取得新闻公告分页列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("moreDetail")
    @Action(description = "查看帮助")
    public void moreDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<News> list = newsService.getAll(new QueryFilter(request, "newsItem"));

        JSONArray jsonMembers = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < list.size(); i++) {
            News news = list.get(i);
            jsonObject.put("helpId", news.getId());
            jsonObject.put("helpPublisher", news.getPublisher());
            jsonObject.put("helpPublishTime", news.getPublishtime());
            jsonObject.put("helpTitle", news.getTitle());
            jsonObject.put("helpContent", news.getContent());

            jsonMembers.add(jsonObject);
        }
        String formatJson = jsonFormat.formatJson(jsonMembers.toString());
        PrintWriter out = response.getWriter();
        out.append(formatJson);
        out.flush();
        out.close();
    }

    /**
     * 取得新闻公告分页列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    @Action(description = "后台的")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<News> list = newsService.getAll(new QueryFilter(request, "newsItem"));
        ModelAndView mv = this.getAutoView().addObject("newsList", list);

        return mv;
    }
    /**
     * 取得新闻公告分页列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("more")
    @Action(description = "前端的")
    public ModelAndView more(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<News> list = newsService.getAll(new QueryFilter(request, "newsItem"));
        ModelAndView mv = this.getAutoView().addObject("newsList", list);

        return mv;
    }
    /**
     * 删除新闻公告
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("del")
    @Action(description = "删除新闻公告")
    public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String preUrl = RequestUtil.getPrePage(request);
        ResultMessage message = null;
        try {
            Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
            newsService.delByIds(lAryId);
            message = new ResultMessage(ResultMessage.Success, "删除新闻公告成功!");
        } catch (Exception ex) {
            message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
        }
        addMessage(message, request);
        response.sendRedirect(preUrl);
    }

    /**
     * 编辑新闻公告
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping("edit")
    @Action(description = "编辑新闻公告")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        Long id = RequestUtil.getLong(request, "id");
        String returnUrl = RequestUtil.getPrePage(request);
        News news = newsService.getById(id);

        return getAutoView().addObject("news", news).addObject("returnUrl", returnUrl);
    }

    /**
     * 取得新闻公告明细
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("get")
    @Action(description = "查看帮助明细")
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long id = RequestUtil.getLong(request, "id");
        News news = newsService.getById(id);
        return getAutoView().addObject("news", news);
    }
}
