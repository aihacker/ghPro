package wxgh.app.sys.excel.user;

import com.libs.common.excel.ExcelApi;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import wxgh.entity.pub.User;

/**
 * Created by Administrator on 2017/9/26.
 */
@Component
@Scope("prototype")
public class UserReadApi extends ExcelApi<User> {

    public UserReadApi() {
    }

    @Override
    public String[] head() {
        return new String[]{"用户ID", "姓名", "性别", "职位", "手机号码", "邮箱地址", "微信ID", "部门"};
    }

    @Override
    public void createCellItem(User user, Row row, int i) {
        createCell(row, 0, user.getUserid());
        createCell(row, 1, user.getName());
        createCell(row, 2, (user.getGender() == null || user.getGender() == 0) ? "未知" : (user.getGender() == 1 ? "男" : "女"));
        createCell(row, 3, user.getPosition());
        createCell(row, 4, user.getMobile());
        createCell(row, 5, user.getEmail());
        createCell(row, 6, user.getWeixinid());
        createCell(row, 7, user.getDepartment());
    }

    @Override
    public User getTemplate() {
        User user = new User();
        user.setUserid("wxgh+手机号码");
        user.setName("张三");
        user.setGender(1);
        user.setPosition("员工");
        user.setMobile("184****8708");
        user.setEmail("12****33.qq.com");
        user.setWeixinid("test_weixinid");
        user.setDepartment("本部-移互中心分会");
        return user;
    }
}
