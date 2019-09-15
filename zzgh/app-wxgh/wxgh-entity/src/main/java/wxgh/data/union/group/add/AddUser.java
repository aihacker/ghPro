package wxgh.data.union.group.add;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public class AddUser {

    private Boolean all;
    private Integer id;
    private List<AddItem> users;
    private List<AddItem> depts;


    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public List<AddItem> getUsers() {
        return users;
    }

    public void setUsers(List<AddItem> users) {
        this.users = users;
    }

    public List<AddItem> getDepts() {
        return depts;
    }

    public void setDepts(List<AddItem> depts) {
        this.depts = depts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
