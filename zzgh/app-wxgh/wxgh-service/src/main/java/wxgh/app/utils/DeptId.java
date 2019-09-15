package wxgh.app.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/10.
 */
public enum DeptId {

    NANHAI("南海", 3), CHANCHNEG("禅城", 8),
    KAIFA("开发小组", 21), BENBU("本部", 30),
    SANSHUI("三水", 100), GAOMING("高明", 110),
    SHUNDE("顺德", 118),SHENGGONGHUI("省工会",169);

    private String name;
    private Integer id;

    private DeptId(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getDeptIds() {
        List<Integer> list = new ArrayList<Integer>();
        for (DeptId deptId : DeptId.values()) {
            Integer id = deptId.id;
            list.add(id);
        }
        return list;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.id;
    }
}
