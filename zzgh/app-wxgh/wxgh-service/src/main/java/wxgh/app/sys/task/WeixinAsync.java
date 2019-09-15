package wxgh.app.sys.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.libs.common.type.TypeUtils;
import com.libs.common.xml.XmlUtils;
import com.weixin.WeixinException;
import com.weixin.bean.call.event.qywx.*;
import com.weixin.bean.chat.call.*;
import com.weixin.bean.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import wxgh.entity.pub.WxConversation;
import wxgh.sys.service.weixin.pub.ConverUserService;
import wxgh.sys.service.weixin.pub.ConversationService;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
@Component
public class WeixinAsync {

    @Async
    public void process_callback(String xml) {

        if (TypeUtils.empty(xml)) return;
        System.out.println(xml);
        try {
            JsonNode root = XmlUtils.getParse().readTree(xml);
            String toUser = root.path("ToUserName").asText();
            String fromUser = root.path("FromUserName").asText();
            Long createTime = root.path("CreateTime").asLong();
            Integer agentId = root.path("AgentID").asInt();
            String msgType = root.path("MsgType").asText();
            if (msgType.equals("event")) { //event事件
                String event = root.path("Event").asText();

                if ("subscribe".equals(event)) { //用户关注事件
                    userService.addOrUpdateUser(fromUser);
                } else if ("unsubscribe".equals(event)) { //用户取消关注
                    userService.updateUserStatus(fromUser, User.Status.NO_ATTENTION);
                } else if ("change_contact".equals(event)) { //通讯录改变
                    String changeType = root.path("ChangeType").asText();
                    if (changeType.equals("create_user")) { //新增用户
                        CreateUser user = XmlUtils.parseBean(xml, CreateUser.class);
                        userService.createUser(new wxgh.entity.pub.User(new User(user)));
                    } else if (changeType.equals("update_user")) { //更新用户
                        UpdateUser user = XmlUtils.parseBean(xml, UpdateUser.class);
                        userService.updateUser(new wxgh.entity.pub.User(new User(user)));
                    } else if (changeType.equals("delete_user")) { //删除用户
                        DeleteUser deleteUser = XmlUtils.parseBean(xml, DeleteUser.class);
                        userService.deleteUser(deleteUser.getUserid());
                    } else if (changeType.equals("create_party")) { //新增部门
                        CreateDept createDept = XmlUtils.parseBean(xml, CreateDept.class);
                        deptService.addDept(createDept);
                    } else if (changeType.equals("update_party")) { //更新部门
                        UpdateDept updateDept = XmlUtils.parseBean(xml, UpdateDept.class);
                        deptService.updateDept(updateDept);
                    } else if (changeType.equals("delete_party")) { //删除部门
//                        DeleteDept deleteDept = XmlUtils.parseBean(xml, DeleteDept.class);
//                        deptService.deleteDept(deleteDept);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WeixinException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void processConversation(ConversationMsg msg) {
        List<BaseItem> items = msg.getItems();
        for (BaseItem item : items) {
            if (item.getMsgType().equals("event")) {
                EventItem eventItem = (EventItem) item;
                if (eventItem.getEvent().equals("create_chat")) {
                    createChat((CreateItem) eventItem);
                } else if (eventItem.getEvent().equals("update_chat")) {
                    updateChat((EditItem) eventItem);
                } else if (eventItem.getEvent().equals("quit_chat")) {
                    quitChat((QuitItem) eventItem);
                }
            }
        }
    }

    /**
     * 退出会话
     *
     * @param item
     */
    private void quitChat(QuitItem item) {
        converUserService.del(item.getFromName(), item.getChatid());
    }

    /**
     * 更新会话
     *
     * @param item
     */
    private void updateChat(EditItem item) {
        WxConversation conversation = new WxConversation();
        conversation.setChatid(item.getChatid());
        conversation.setName(item.getName());
        conversation.setOwner(item.getOwner());

        List<String> addUsers = TypeUtils.strToList(item.getAddUsers(), "\\|");
        List<String> delUsers = TypeUtils.strToList(item.getDelUsers(), "\\|");

        conversationService.update(conversation, addUsers, delUsers);
    }

    /**
     * 创建会话
     *
     * @param item
     */
    private void createChat(CreateItem item) {
        ChatInfo chatInfo = item.getChatInfo();

        WxConversation conversation = new WxConversation();
        conversation.setChatid(chatInfo.getChatId());
        conversation.setName(chatInfo.getName());
        conversation.setOwner(chatInfo.getOwner());
        conversation.setType(WxConversation.TYPE_OTHER);
        conversation.setAddTime(new Date(item.getCreateTime()));

        List<String> userids = TypeUtils.strToList(chatInfo.getUserlist(), "\\|");

        conversationService.save(conversation, userids);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConverUserService converUserService;
}
