package wxgh.entity.pub.tag;

/**
 * Created by Administrator on 2017/8/10.
 */
public enum TagType {

    WX_ADMIN(1); //管理员审核应用管理员

    private Integer type;

    TagType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
