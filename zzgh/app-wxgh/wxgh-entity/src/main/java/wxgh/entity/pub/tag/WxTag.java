package wxgh.entity.pub.tag;

import com.weixin.bean.tag.Tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/10.
 */
@Entity
@Table
public class WxTag extends Tag implements Serializable {

    private Integer id;
    private Integer tagType;

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    @Override
    public String toString() {
        return "WxTag{" +
                "id=" + id +
                ", tagType=" + tagType +
                '}';
    }
}
