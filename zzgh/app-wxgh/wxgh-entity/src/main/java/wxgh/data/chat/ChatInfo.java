package wxgh.data.chat;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ChatInfo {

    private Integer id;
    private String groupId;
    private String name;
    private Integer type;

    private List<ChatModelInfo> models;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<ChatModelInfo> getModels() {
        return models;
    }

    public void setModels(List<ChatModelInfo> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "ChatInfo{" +
                "id=" + id +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", models=" + models +
                '}';
    }
}
