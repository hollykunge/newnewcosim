package com.casic.datadriver.publicClass;

/**
 * Created by d on 2017/6/5.
 */
public class ProjectTree {

    private Long id;

    private String name;

    private Long parentId;

    private Long bf1;

    private String bf2;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBf2() {
        return bf2;
    }

    public void setBf2(String bf2) {
        this.bf2 = bf2;
    }

    public Long getBf1() {
        return bf1;
    }

    public void setBf1(Long bf1) {
        this.bf1 = bf1;
    }

    @Override
    public String toString() {
        return "ProjectTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", bf1=" + bf1 +
                ", bf2='" + bf2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectTree)) return false;

        ProjectTree that = (ProjectTree) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!parentId.equals(that.parentId)) return false;
        if (!bf1.equals(that.bf1)) return false;
        return bf2.equals(that.bf2);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + parentId.hashCode();
        result = 31 * result + bf1.hashCode();
        result = 31 * result + bf2.hashCode();
        return result;
    }
}
