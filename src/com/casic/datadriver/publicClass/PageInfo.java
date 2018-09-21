package com.casic.datadriver.publicClass;

/**
 * Created by 忠 on 2017/2/14.
 */
public class PageInfo {

    private Long id;

    private String Name;

    private Long pageSize;

    private Long pageNumber;

    private Long bf1;

    private String bf2;

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

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getBf1() {
        return bf1;
    }

    public void setBf1(Long bf1) {
        this.bf1 = bf1;
    }

    public String getBf2() {
        return bf2;
    }

    public void setBf2(String bf2) {
        this.bf2 = bf2;
    }
}
