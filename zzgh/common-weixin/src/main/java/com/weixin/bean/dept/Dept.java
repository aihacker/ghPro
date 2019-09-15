package com.weixin.bean.dept;

import com.libs.common.json.JsonNoneNull;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class Dept extends JsonNoneNull {

    private String name;
    private Integer parentid;
    private Integer order;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "name='" + name + '\'' +
                ", parentid=" + parentid +
                ", order=" + order +
                ", id=" + id +
                '}';
    }
}
