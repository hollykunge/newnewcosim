package com.casic.cloud.controller.taskfile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.casic.cloud.model.taskfile.TaskFile;
import com.casic.cloud.model.taskfile.TaskFileNode;
import com.casic.cloud.service.taskfile.TaskFileNodeService;
import com.casic.cloud.service.taskfile.TaskFileService;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.hotent.core.annotion.Action;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.model.bpm.BpmNodeSet;
import com.hotent.platform.service.bpm.BpmNodeSetService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.system.SysUserService;

/**
 * @author FangYun
 * 
 */
@Controller
@RequestMapping(value = { "/cloud/taskfile/" })
public class TaskFileController extends BaseController {
	@Resource
	private TaskFileService taskFileService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private BpmService bpmService;
	@Resource
	private BpmNodeSetService bpmNodeSetService;
	@Resource
	private TaskFileNodeService taskFileNodeService;

	@RequestMapping(value = { "list" })
	@Action(description = "查看文件分页列表")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long taskId = Long.valueOf(RequestUtil.getLong(request, "taskId"));
		QueryFilter filter = new QueryFilter(request, "taskFileItem");
	
		TaskEntity taskEntity = bpmService.getTask(String.valueOf(taskId));
		String nodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId);
		filter.addFilter("setId", bpmNodeSet.getSetId());
		List<TaskFile> list = taskFileService.getAll(filter);
		//给taskFiles添加creatorName
		for(TaskFile taskFile:list){
			Long creatorId = taskFile.getCreatorId();
			
			ISysUser user = sysUserService.getById(creatorId);
			taskFile.setCreatorName(user.getFullname());
			
		}
		
		return getAutoView().addObject("taskFileList", list);
	}

	@RequestMapping(value = { "fileUpload" })
	@Action(description = "文件上传")
	public void fileUpload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		try {
			long userId = ContextUtil.getCurrentUserId().longValue();
			long taskId = RequestUtil.getLong(request, "taskId");
			ISysUser appUser = null;
			if (userId > 0L)
				appUser = sysUserService.getById(Long.valueOf(userId));
			Map<String, MultipartFile> files = request.getFileMap();
			Long fileId;
			for (Iterator<MultipartFile> it = files.values().iterator(); it.hasNext(); writer
					.println((new StringBuilder("{\"success\":\"true\",\"fileId\":\"")).append(fileId).append("\"}")
							.toString())) {
				fileId = Long.valueOf(UniqueIdUtil.genId());
				MultipartFile f = (MultipartFile) it.next();
				String oriFileName = f.getOriginalFilename();
				String extName = FileUtil.getFileExt(oriFileName);
				String fileName = (new StringBuilder()).append(fileId).append(".").append(extName).toString();
				String filePath = createFilePath(AppUtil.getRealPath("/taskFiles/temp"), fileName);
				FileUtil.writeByte(filePath, f.getBytes());
				TaskFile taskFile = new TaskFile();
				taskFile.setTaskId(taskId);
				taskFile.setFileId(fileId);
				taskFile.setFileName(oriFileName.substring(0, oriFileName.lastIndexOf('.')));
				Calendar cal = Calendar.getInstance();
				Integer year = Integer.valueOf(cal.get(Calendar.YEAR));
				Integer month = Integer.valueOf(cal.get(Calendar.MONTH) + 1);
				taskFile.setFilePath((new StringBuilder("taskFiles/temp/")).append(year).append("/").append(month)
						.append("/").append(fileName).toString());
				taskFile.setCreatetime(new Date());
				taskFile.setExt(extName);
				taskFile.setTotalBytes(Long.valueOf(f.getSize()));
				taskFile.setNote(FileUtil.getSize(f.getSize()));
				if (appUser != null) {
					taskFile.setCreatorId(appUser.getUserId());
					taskFile.setCreator(appUser.getUsername());
				} else {
					taskFile.setCreator(TaskFile.FILE_UPLOAD_UNKNOWN);
				}
				taskFile.setDelFlag(TaskFile.FILE_NOT_DEL);
				TaskEntity taskEntity = bpmService.getTask(String.valueOf(taskId));
				String nodeId = taskEntity.getTaskDefinitionKey();
				String actDefId = taskEntity.getProcessDefinitionId();
				BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId);
				taskFileService.addTaskFile(taskFile, bpmNodeSet.getSetId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			writer.println("{\"success\":\"false\"}");
		}
	}

	@RequestMapping(value = { "del" })
	@Action(description = "删除附件")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		String returnUrl = RequestUtil.getPrePage(request);
		Long lAryId[] = RequestUtil.getLongAryByStr(request, "fileId");
		try {
			Long along[];
			int j = (along = lAryId).length;
			for (int i = 0; i < j; i++) {
				Long id = along[i];
				TaskFile sysFile = (TaskFile) taskFileService.getById(id);
				String filePath = sysFile.getFilePath();
				FileUtil.deleteFile(AppUtil.getRealPath(filePath));
			}

			taskFileService.delByIds(lAryId);
			message = new ResultMessage(1, "删除流程文件成功");
		} catch (Exception e) {
			message = new ResultMessage(0, "删除流程文件失败");
		}
		addMessage(message, request);
		response.sendRedirect(returnUrl+"?taskId="+RequestUtil.getLong(request, "taskId"));
	}

	@RequestMapping(value = { "delByFileId" })
	@Action(description = "删除附件")
	public void delByFileId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		Long lAryId[] = RequestUtil.getLongAryByStr(request, "ids");
		try {
			Long along[];
			int j = (along = lAryId).length;
			for (int i = 0; i < j; i++) {
				Long id = along[i];
				TaskFile sysFile = (TaskFile) taskFileService.getById(id);
				String filePath = sysFile.getFilePath();
				FileUtil.deleteFile(AppUtil.getRealPath(filePath));
			}

			taskFileService.delByIds(lAryId);
			out.print("{\"success\":\"true\"}");
		} catch (Exception e) {
			out.print("{\"success\":\"false\"}");
		}
	}

	@RequestMapping(value = { "download" })
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long fileId = RequestUtil.getLong(request, "fileId", 0L);
		if (fileId == 0L)
			return;
		TaskFile taskFile = (TaskFile) taskFileService.getById(Long.valueOf(fileId));
		if (taskFile == null)
			return;
		String filePath = taskFile.getFilePath();
		if (StringUtil.isEmpty(filePath)) {
			return;
		} else {
			String fullPath = (new StringBuilder(String.valueOf(FileUtil.getRootPath()))).append(
					filePath.replace("/", File.separator)).toString();
			String fileName = (new StringBuilder(String.valueOf(taskFile.getFileName()))).append(".")
					.append(taskFile.getExt()).toString();
			FileUtil.downLoadFile(response, fullPath, fileName);
			return;
		}
	}

	@RequestMapping(value = { "taskfileNode" })
	public ModelAndView taskfileNode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId = RequestUtil.getString(request, "taskId");
		Long taskfileId = RequestUtil.getLong(request, "taskfileId");
		TaskEntity taskEntity = bpmService.getTask(taskId);
		String nodeId = taskEntity.getTaskDefinitionKey();
		String actDefId = taskEntity.getProcessDefinitionId();
		BpmNodeSet bpmNodeSet = bpmNodeSetService.getByActDefIdNodeId(actDefId, nodeId);
		Long defId = bpmNodeSet.getDefId();
		List<BpmNodeSet> bpmNodeSets = bpmNodeSetService.getAllByDefId(defId);
		List<TaskFileNode> fileNodes = taskFileNodeService.getTaskFileNodesByTaskfileId(taskfileId);
		List<TaskFileNode> fileNodeRights = new ArrayList<TaskFileNode>();
		for (final BpmNodeSet node : bpmNodeSets) {
			TaskFileNode hit = Iterables.find(fileNodes, new Predicate<TaskFileNode>() {
				@Override
				public boolean apply(TaskFileNode tfn) {
					return node.getSetId().equals(tfn.getSetId());
				}
			}, null);
			if (hit == null) {
				fileNodeRights.add(new TaskFileNode(-1L, taskfileId, node, false));
			} else {
				fileNodeRights.add(new TaskFileNode(hit.getTaskfileNodeId(), taskfileId, node, true));
			}
		}
		for(int i=0;i<fileNodeRights.size();i++){
			TaskFileNode taskFileNode = fileNodeRights.get(i);
			if(taskFileNode.getNode().getNodeName()==null){
				fileNodeRights.remove(taskFileNode);
				
			}
		}
		TaskFile tf = taskFileService.getById(taskfileId);
		ModelAndView mv = getAutoView();
		Collections.sort(fileNodeRights, new Comparator<TaskFileNode>() {
			@Override
			public int compare(TaskFileNode o1, TaskFileNode o2) {
				//System.out.println(" "+o1.getNode()+" "+o2.getNode());
				return o1.getSetId().compareTo(o2.getSetId());
			}
		});
		mv.addObject("fileNodeRights", fileNodeRights).addObject("currentSetId", bpmNodeSet.getSetId())
				.addObject("taskFile", tf);
		return mv;
	}

	@RequestMapping(value = { "updateRights" })
	public void updateRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Long taskfileId = RequestUtil.getLong(request, "taskfileId");
		Long[] setIds = RequestUtil.getLongAry(request, "setId");
		taskFileNodeService.updateRights(taskfileId, setIds);
		ResultMessage message = new ResultMessage(ResultMessage.Success, "更新权限成功！");
		super.addMessage(message, request);
		String preUrl = RequestUtil.getPrePage(request);
		response.sendRedirect(preUrl);
	}

	private String createFilePath(String tempPath, String fileName) {
		File one = new File(tempPath);
		Calendar cal = Calendar.getInstance();
		Integer year = Integer.valueOf(cal.get(Calendar.YEAR));
		Integer month = Integer.valueOf(cal.get(Calendar.MONTH) + 1);
		one = new File((new StringBuilder(String.valueOf(tempPath))).append("/").append(year).append("/").append(month)
				.toString());
		if (!one.exists())
			one.mkdirs();
		return (new StringBuilder(String.valueOf(one.getPath()))).append(File.separator).append(fileName).toString();
	}
}
