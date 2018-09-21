package com.casic.datadriver.model.data;

public class DataSnapshot {
    private Long ddDataSnapshotId;

    private Long ddDataId;

    private String ddDataValue;

    private String ddDataRecordTime;

    private Long ddSnapshotPersonId;

    private String ddSnapshotTime;

    private String ddDataTag;

    public Long getDdDataSnapshotId() {
        return ddDataSnapshotId;
    }

    public void setDdDataSnapshotId(Long ddDataSnapshotId) {
        this.ddDataSnapshotId = ddDataSnapshotId;
    }

    public Long getDdDataId() {
        return ddDataId;
    }

    public void setDdDataId(Long ddDataId) {
        this.ddDataId = ddDataId;
    }

    public String getDdDataValue() {
        return ddDataValue;
    }

    public void setDdDataValue(String ddDataValue) {
        this.ddDataValue = ddDataValue;
    }

    public String getDdDataRecordTime() {
        return ddDataRecordTime;
    }

    public void setDdDataRecordTime(String ddDataRecordTime) {
        this.ddDataRecordTime = ddDataRecordTime;
    }

    public Long getDdSnapshotPersonId() {
        return ddSnapshotPersonId;
    }

    public void setDdSnapshotPersonId(Long ddSnapshotPersonId) {
        this.ddSnapshotPersonId = ddSnapshotPersonId;
    }

    public String getDdSnapshotTime() {
        return ddSnapshotTime;
    }

    public void setDdSnapshotTime(String ddSnapshotTime) {
        this.ddSnapshotTime = ddSnapshotTime;
    }

    public String getDdDataTag() {
        return ddDataTag;
    }

    public void setDdDataTag(String ddDataTag) {
        this.ddDataTag = ddDataTag;
    }
}