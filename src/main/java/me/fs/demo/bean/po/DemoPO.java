package me.fs.demo.bean.po;

import java.util.Date;

/**
 * 与数据库表映射的对象
 *
 * @author FS
 */
public class DemoPO {
    private Integer id;
    private String prop1;
    private String prop2;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DemoVO{" +
                "id=" + id +
                ", prop1='" + prop1 + '\'' +
                ", prop2='" + prop2 + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
