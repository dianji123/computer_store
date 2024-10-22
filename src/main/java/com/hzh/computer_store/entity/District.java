package com.hzh.computer_store.entity;

import java.io.Serializable;

/**省市区的实体类*/
public class District implements Serializable { //没有继承BaseEntity 所以要序列化

    private Integer id;
    private String parent;
    private String code;
    private String name;

    public District() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof District)) return false;

        District district = (District) object;

        if (getId() != null ? !getId().equals(district.getId()) : district.getId() != null) return false;
        if (getParent() != null ? !getParent().equals(district.getParent()) : district.getParent() != null)
            return false;
        if (getCode() != null ? !getCode().equals(district.getCode()) : district.getCode() != null) return false;
        return getName() != null ? getName().equals(district.getName()) : district.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getParent() != null ? getParent().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", parent='" + parent + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
