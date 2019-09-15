package wxgh.admin.session.bean;

import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import wxgh.data.pub.NameValue;
import wxgh.entity.admin.NewAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/9.
 */
public class SeAdmin {

    private Integer id;
    private String adminId;
    private String name;
    private String cateId;
    private String navId;
    private String remark;
    private Map<String, String> extraMap;

    public SeAdmin() {
    }

    public SeAdmin(NewAdmin admin) {
        this.id = admin.getId();
        this.adminId = admin.getAdminId();
        this.name = admin.getName();
        this.cateId = admin.getCateId();
        this.remark = admin.getRemark();
        this.navId = admin.getNavId();
        Map<String, String> map = new HashMap<>();
        if (!TypeUtils.empty(admin.getExtra())) {
            List<NameValue> nameValues = JsonUtils.parseList(admin.getExtra(), NameValue.class);
            for (NameValue nameValue : nameValues) {
                map.put(nameValue.getName(), nameValue.getValue());
            }
        }
        this.extraMap = map;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNavId() {
        return navId;
    }

    public void setNavId(String navId) {
        this.navId = navId;
    }

    public Map<String, String> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, String> extraMap) {
        this.extraMap = extraMap;
    }
}
