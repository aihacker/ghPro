package com.weixin.crypt.xml;

import com.weixin.bean.chat.call.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ConversationXmlParser {

    private static String getNodeValue(NodeList nodeList, int index) {
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(index);
            if (node != null) {
                return nodeList.item(index).getTextContent();
            }
        }
        return null;
    }

    /**
     * 解析创建、修改会话回调xml
     *
     * @param xml
     * @return
     */
    public static ConversationMsg parserConversation(String xml) {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList agentTypeNode = root.getElementsByTagName("AgentType");
            NodeList toUserNode = root.getElementsByTagName("ToUserName");
            NodeList countNode = root.getElementsByTagName("ItemCount");
            NodeList packageIdNode = root.getElementsByTagName("PackageId");
            NodeList itemNodes = root.getElementsByTagName("Item");

            ConversationMsg newsXml = new ConversationMsg();
            newsXml.setAgentType(agentTypeNode.item(0).getTextContent());
            newsXml.setToUser(toUserNode.item(0).getTextContent());
            newsXml.setItemCount(Integer.valueOf(countNode.item(0).getTextContent()));
            newsXml.setPackageId(packageIdNode.item(0).getTextContent());
            List<BaseItem> items = new ArrayList<>();
            for (int i = 0; i < itemNodes.getLength(); i++) {

                String msgType = root.getElementsByTagName("MsgType").item(i).getTextContent();
                String fromUser = root.getElementsByTagName("FromUserName").item(i).getTextContent();
                String createTime = root.getElementsByTagName("CreateTime").item(i).getTextContent();

                BaseItem baseItem = new BaseItem();
                baseItem.setMsgType(msgType);
                baseItem.setFromName(fromUser);
                baseItem.setCreateTime(Long.parseLong(createTime) * 1000);

                if (msgType.equals("event")) {
                    String event = root.getElementsByTagName("Event").item(i).getTextContent();
                    EventItem eventItem = EventItem.extend(baseItem);
                    eventItem.setEvent(event);
                    if (event.equals("create_chat")) { //创建会话
                        CreateItem createItem = new CreateItem(eventItem);
                        ChatInfo info = new ChatInfo();
                        createItem.setChatInfo(info);

                        NodeList chatInfoNode = root.getElementsByTagName("ChatInfo").item(i).getChildNodes();
                        info.setChatId(chatInfoNode.item(0).getTextContent());
                        info.setName(chatInfoNode.item(1).getTextContent());
                        info.setOwner(chatInfoNode.item(2).getTextContent());
                        info.setUserlist(chatInfoNode.item(3).getTextContent());

                        items.add(createItem);
                    } else if (event.equals("update_chat")) { //修改会话
                        EditItem editItem = new EditItem(eventItem);

                        editItem.setName(getNodeValue(root.getElementsByTagName("Name"), i));
                        editItem.setOwner(getNodeValue(root.getElementsByTagName("Owner"), i));
                        editItem.setAddUsers(getNodeValue(root.getElementsByTagName("AddUserList"), i));
                        editItem.setDelUsers(getNodeValue(root.getElementsByTagName("DelUserList"), i));
                        editItem.setChatid(getNodeValue(root.getElementsByTagName("ChatId"), i));

                        items.add(editItem);
                    } else if (event.equals("quit_chat")) { //退出会话
                        QuitItem quitItem = new QuitItem(eventItem);

                        String chatId = root.getElementsByTagName("ChatId").item(i).getTextContent();

                        quitItem.setChatid(chatId);

                        items.add(quitItem);
                    }
                }
            }
            newsXml.setItems(items);
            return newsXml;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
