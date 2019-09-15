package wxgh.app.web.test;

import com.libs.common.excel.ExcelUtils;
import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.DeptApi;
import com.weixin.api.TagApi;
import com.weixin.api.UserApi;
import com.weixin.bean.dept.Dept;
import com.weixin.bean.tag.Tag;
import com.weixin.bean.user.SimpleUser;
import com.weixin.bean.user.User;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.sys.service.weixin.test.DeptService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
@Controller
public class MainController {

    /**
     * 同步企业号用户
     *
     * @param deptid
     * @throws WeixinException
     */
    @RequestMapping
    public void get_dept(Integer deptid) throws WeixinException {
        List<Dept> depts = DeptApi.list(deptid);

        for (Dept dept : depts) {
            deptService.addDept(dept);
        }
        deptService.next();
    }


    /**
     * 同步企业号用户
     *
     * @throws WeixinException
     */
    @RequestMapping
    public void user() throws WeixinException {
        List<SimpleUser> users = UserApi.simplelist(1, true, User.Status.ALL);
        for (SimpleUser user : users) {
            deptService.addUser(user);
        }


    }

    @RequestMapping
    public void updatemobile() throws  WeixinException{
        List<SimpleUser> users = UserApi.simplelist(1, true, User.Status.ALL);
        for(int i=0;i<users.size();i++){
            User user = UserApi.get(users.get(i).getUserid());
            deptService.updateMobile(user.getUserid(),user.getMobile());
        }
    }

//    @RequestMapping
//    public void deluser() {
//        List<String> list = new ArrayList<>();
//
//        list.add("DGwd18925977898");list.add("CmNF17727307879");list.add("xnwJ18928521521");list.add("Gmqx13336453033");
//        list.add("hriR13392208897");list.add("spUP18924540319");list.add("DzNy18927292363");list.add("wxgh18927292717");
//        list.add("jhYE18029226833");list.add("UpLJ13360343045");list.add("RWeU13392291181");list.add("acIq18928539500");
//        list.add("wxgh17796049220");list.add("KNFP13322851485");list.add("QKMY13326749600");list.add("KGTe18088836763");
//        list.add("NrGg13318382708");list.add("urKJ13318211168");list.add("zorb15015603008");list.add("yuSA18823138988");
//        list.add("nGIM13392292939");list.add("wxgh13322854526");list.add("wxgh13686537827");list.add("wxgh18928527877");
//        list.add("wxgh18928527877");list.add("KauR18028160004");list.add("kEqF13322856338");list.add("rUvb13392291101");
//        list.add("gOWS13318382815");list.add("UidL18128708343");list.add("OaEQ18929927838");list.add("EroB13380210168");
//        list.add("wxgh13392216263");list.add("wxgh13392292905");list.add("wxgh13392291699");list.add("wxgh18928522313");
//        list.add("wxgh13322853919");list.add("wxgh13380239375");list.add("wxgh13392291151");list.add("wxgh13380210533");
//        list.add("wxgh18924556189");list.add("wxgh13392291162");
//
//        for(String userid : list){
//            System.out.println();
//            System.out.println("userid::::"+userid);
//            try{
//                UserApi.delete(userid);
//            }catch (Exception e){
//                continue;
//            }
//
//        }
//
//    }

    /**
     * 设置userId
     *
     * @throws IOException
     */
    @RequestMapping
    public void party_dy() throws IOException {
        String path = "C:\\Users\\Administrator\\Desktop\\微信纪检群体，请帮忙录入，谢谢！\\数据整理.xlsx";

        File file = new File(path);
        Workbook wb = ExcelUtils.getWorkbook(file);
        for (int i = 0, len = wb.getNumberOfSheets(); i < len; i++) {
            Sheet sheet = wb.getSheetAt(i);
            for (int j = 1, rowNumb = sheet.getLastRowNum(); j < rowNumb; j++) {
                try {
                    Row row = sheet.getRow(j);
                    if (row != null) {
                        String name = ExcelUtils.getString(row.getCell(1));
                        if (!TypeUtils.empty(name)) {
                            String userid = deptService.getUserid(name);

                            Cell cell = row.getCell(2);
                            if (cell == null) {
                                cell = row.createCell(2);
                            }
                            cell.setCellValue(userid);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        File toFile = new File(file.getParentFile(), "news.xlsx");
        if (!toFile.exists()) {
            toFile.createNewFile();
        }
        wb.write(new FileOutputStream(toFile));
        wb.close();
    }

    @RequestMapping
    public void di_group() {
        String[] names = new String[]{"关键岗位人员", "化小承包小CEO", "经理人员", "党员"};
        List<ChatGroup> groups = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            ChatGroup group = new ChatGroup();
            group.setGroupId(StringUtils.uuid());
            group.setName(names[i]);
            group.setType(ChatGroup.TYPE_DI);

            groups.add(group);
        }
        deptService.addGroups(groups);
    }

    /**
     * 导入纪检人员
     */
    @RequestMapping
    public void di_import() throws IOException {
        String path = "C:\\Users\\Administrator\\Desktop\\微信纪检群体，请帮忙录入，谢谢！\\news.xlsx";

        File file = new File(path);

        File resultFile = new File(file.getParentFile(), "result.txt");
        if (!resultFile.exists()) resultFile.createNewFile();

        String[] groupIds = new String[]{"4b95d12e1eea4182bc215e8e524d0fe5",
                "7c78c82b7abe4015a4296e21e43b0851",
                "7d4dab7ab3984803bb3e6d71bdac36c0",
                "4c536254c49d4d2f9568a3761be4dc70"};

        Workbook wb = ExcelUtils.getWorkbook(file);
        for (int i = 0, len = wb.getNumberOfSheets(); i < len; i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<ChatUser> users = new ArrayList<>();
            for (int j = 1, rowNumb = sheet.getLastRowNum(); j <= rowNumb; j++) {
                try {
                    Row row = sheet.getRow(j);
                    String userid = ExcelUtils.getString(row.getCell(2));

                    ChatUser user = new ChatUser();
                    user.setUserid(userid);
                    user.setGroupId(groupIds[i]);

                    users.add(user);

                } catch (Exception e) {
                    FileUtils.writeStringToFile(resultFile, "sheet=" + i + ", row=" + j + ", error=" + e.getMessage(), "UTF-8", true);
                }
            }

            deptService.addChatUsers(users);
        }
        wb.close();
    }

    /**
     * 导入青年部落人员
     */
    @RequestMapping
    public void tribe() throws IOException {
        String path = "C:\\Users\\Administrator\\Desktop\\微信纪检群体，请帮忙录入，谢谢！\\userids.xlsx";

        File file = new File(path);

        File resultFile = new File(file.getParentFile(), "result.txt");
        if (!resultFile.exists()) resultFile.createNewFile();

        String tribeid = "242c502064e14f87910277674b7b1022";

        Workbook wb = ExcelUtils.getWorkbook(file);
        Sheet sheet = wb.getSheetAt(0);
        List<ChatUser> users = new ArrayList<>();
        for (int j = 0, rowNumb = sheet.getLastRowNum(); j <= rowNumb; j++) {
            try {
                Row row = sheet.getRow(j);
                String userid = ExcelUtils.getString(row.getCell(0));
                Date addTime = ExcelUtils.getDate(row.getCell(1), "yyyy-MM-dd HH:mm:ss");

                ChatUser user = new ChatUser();
                user.setUserid(userid);
                user.setAddTime(addTime);
                user.setGroupId(tribeid);

                users.add(user);

            } catch (Exception e) {
                FileUtils.writeStringToFile(resultFile, "sheet=" + 0 + ", row=" + j + ", error=" + e.getMessage(), "UTF-8", true);
            }
        }
        deptService.addChatUsers(users);
        wb.close();
    }

    @RequestMapping
    public void tag() throws WeixinException {
        List<Tag> tags = TagApi.list();
        deptService.addTag(tags);
    }

    @Autowired
    private DeptService deptService;
}
