package com.casic.datadriver.publicClass;

/**
 * Created by 忠 on 2017/3/30.
 * 查询参数类，作用对后台多参数查询使用
 */
public class QueryParameters {

    private Long id;

    private String Name;

    private Long type;

    private String num;

    private Long backupsL;

    private String backupsS;

    public Long getBackupsL() {
        return backupsL;
    }

    public Boolean getBackup() {
        return isBackup;
    }

    public void setBackup(Boolean backup) {
        isBackup = backup;
    }

    private Boolean isBackup;

    public Long getBackupsL(int i) {
        return backupsL;
    }

    public void setBackupsL(Long backupsL) {
        this.backupsL = backupsL;
    }

    public String getBackupsS() {
        return backupsS;
    }

    public void setBackupsS(String backupsS) {
        this.backupsS = backupsS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
