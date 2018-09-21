package com.casic.datadriver.model.tool;

import com.hotent.core.model.BaseModel;

public class ToolCenterModel extends BaseModel {
    private Long ddToolId;

    private String ddToolName;

    private String ddToolUrl;

    private String ddToolMajor;

    private String ddToolStutus;

    private String ddToolVersion;

    private String ddToolUser;

    private String ddToolData;

    private String ddToolBf;

    private String ddToolBf2;

    public Long getDdToolId() {
        return ddToolId;
    }

    public void setDdToolId(Long ddToolId) {
        this.ddToolId = ddToolId;
    }

    public String getDdToolName() {
        return ddToolName;
    }

    public void setDdToolName(String ddToolName) {
        this.ddToolName = ddToolName == null ? null : ddToolName.trim();
    }

    public String getDdToolUrl() {
        return ddToolUrl;
    }

    public void setDdToolUrl(String ddToolUrl) {
        this.ddToolUrl = ddToolUrl == null ? null : ddToolUrl.trim();
    }

    public String getDdToolMajor() {
        return ddToolMajor;
    }

    public void setDdToolMajor(String ddToolMajor) {
        this.ddToolMajor = ddToolMajor == null ? null : ddToolMajor.trim();
    }

    public String getDdToolStutus() {
        return ddToolStutus;
    }

    public void setDdToolStutus(String ddToolStutus) {
        this.ddToolStutus = ddToolStutus == null ? null : ddToolStutus.trim();
    }

    public String getDdToolVersion() {
        return ddToolVersion;
    }

    public void setDdToolVersion(String ddToolVersion) {
        this.ddToolVersion = ddToolVersion;
    }

    public String getDdToolUser() {
        return ddToolUser;
    }

    public void setDdToolUser(String ddToolUser) {
        this.ddToolUser = ddToolUser == null ? null : ddToolUser.trim();
    }

    public String getDdToolData() {
        return ddToolData;
    }

    public void setDdToolData(String ddToolData) {
        this.ddToolData = ddToolData == null ? null : ddToolData.trim();
    }

    public String getDdToolBf() {
        return ddToolBf;
    }

    public void setDdToolBf(String ddToolBf) {
        this.ddToolBf = ddToolBf == null ? null : ddToolBf.trim();
    }

    public String getDdToolBf2() {
        return ddToolBf2;
    }

    public void setDdToolBf2(String ddToolBf2) {
        this.ddToolBf2 = ddToolBf2 == null ? null : ddToolBf2.trim();
    }
}