package com.casic.datadriver.model.data;

public class DataSnapShotId {


    public Long getDdDataId() {
        return ddDataId;
    }

    public void setDdDataId(Long ddDataId) {
        this.ddDataId = ddDataId;
    }

    public Long getDdDataSnapShotId() {
        return ddDataSnapShotId;
    }

    public void setDdDataSnapShotId(Long ddDataSnapShotId) {
        this.ddDataSnapShotId = ddDataSnapShotId;
    }

    public String getDdDataValue() {
        return ddDataValue;
    }

    public void setDdDataValue(String ddDataValue) {
        this.ddDataValue = ddDataValue;
    }

    public Integer getDdDataVersion() {
        return ddDataVersion;
    }

    public void setDdDataVersion(Integer ddDataVersion) {
        this.ddDataVersion = ddDataVersion;
    }

    private Long ddDataId;

    private Long ddDataSnapShotId;

    private String ddDataValue;

    private Integer ddDataVersion;






}