package com.casic.cloud.model.taskfile;

import java.util.Date;

import com.hotent.core.model.BaseModel;

/**
 * @author FangYun
 *
 */
public class TaskFile extends BaseModel{
	private static final long serialVersionUID = 7551797382311315446L;
    public static Short FILE_DEL = Short.valueOf((short)1);
    public static Short FILE_NOT_DEL = Short.valueOf((short)0);
    public static String FILE_UPLOAD_UNKNOWN = "unknown";
    public static String FILETYPE_OTHERS = "others";
    protected Long fileId;
    protected Long taskId;
    protected String fileName;
    protected String filePath;
    protected Date createtime;
    protected String ext;
    protected String fileType;
    protected String note;
    protected Long creatorId;
    protected String creator;
    protected String creatorName;
    protected Long totalBytes;
    protected Short delFlag;
    protected String typeName;
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getTotalBytes() {
		return totalBytes;
	}
	public void setTotalBytes(Long totalBytes) {
		this.totalBytes = totalBytes;
	}
	public Short getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Short delFlag) {
		this.delFlag = delFlag;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
}
