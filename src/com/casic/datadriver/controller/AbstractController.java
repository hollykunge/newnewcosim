/**
 * 
 */
package com.casic.datadriver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotent.core.service.BaseService;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.util.RequestUtil;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * The Class AbstractController.
 *
 * @author Administrator
 */
public abstract class AbstractController extends BaseController {

	/**
	 * Gets the form object.
	 *
	 * @param <T>
	 *            the generic type
	 * @param request
	 *            the request
	 * @param clazz
	 *            the clazz
	 * @return the form object
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getFormObject(HttpServletRequest request, Class<T> clazz) throws Exception {
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		String json = RequestUtil.getString(request, "json");
		JSONObject obj = JSONObject.fromObject(json);
		return (T) JSONObject.toBean(obj, clazz);
	}

	/**
	 * Del.
	 *
	 * @param <T>
	 *            the generic type
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param service
	 *            the service
	 * @throws Exception
	 *             the exception
	 */
	protected <T> void del(HttpServletRequest request, HttpServletResponse response, BaseService<T> service)
			throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		ResultMessage message = null;
		try {
			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
			service.delByIds(lAryId);
			message = new ResultMessage(ResultMessage.Success, "删除成功!");
		} catch (Exception ex) {
			message = new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
}
