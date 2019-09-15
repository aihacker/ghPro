package wxgh.wx.sys.websocket;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pub.web.ServletUtils;
import wxgh.app.session.chat.SessionConsts;
import wxgh.param.chat.ChatObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11.
 */
@Component
public class ChatHandler extends TextWebSocketHandler {

    public static final Map<String, Map<String, WebSocketSession>> sessionMap;

    private static final Logger log = Logger.getLogger(ChatHandler.class);

    static {
        sessionMap = new HashMap<>();
    }

    @Override
    //发送文字消息
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sendTextMessage(message);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
        session.sendMessage(returnMessage);

        log.debug("websocket handle message：" + message.getPayload().toString());

        super.handleMessage(session, message);
    }

    @Override
    //websocket连接成功
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ChatObject chatUser = (ChatObject) session.getAttributes().get(SessionConsts.WEBSOCKET_SEESION);

        Map<String, WebSocketSession> map = sessionMap.getOrDefault(chatUser.getId(), null);
        if (map == null) {
            map = new HashMap<>();
            map.put(chatUser.getUserid(), session);
            sessionMap.put(chatUser.getId(), map);
        } else {
            sessionMap.get(chatUser.getId()).put(chatUser.getUserid(), session);
        }
        log.debug("websocket connection success：" + chatUser.toString());
    }

    @Override
    //websocket连接关闭
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            ChatObject chatUser = (ChatObject) session.getAttributes().get(SessionConsts.WEBSOCKET_SEESION);

            if (sessionMap.containsKey(chatUser.getId())) {
                sessionMap.get(chatUser.getId()).remove(chatUser.getUserid());
                log.debug("websocket connection closed：" + chatUser.toString());
            }
        } catch (Exception e) {
            log.error("websocket connection closed error：" + e.getMessage());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        try {
            ChatObject chatUser = (ChatObject) session.getAttributes().get(SessionConsts.WEBSOCKET_SEESION);
            if (session.isOpen()) {
                session.close();
            }
            if (sessionMap.containsKey(chatUser.getId())) {
                sessionMap.get(chatUser.getId()).remove(chatUser.getUserid());
                log.error("websocket transport error：" + chatUser.toString() + "，" + exception.getMessage());
            }
        } catch (Exception e) {
            log.error("websocket transport error：" + e.getMessage());
        }
    }

    /**
     * 发送文字消息
     *
     * @param msg
     * @throws IOException
     */
    public void sendTextMessage(TextMessage msg) throws IOException {
        ChatObject chatUser = (ChatObject) ServletUtils.getSession().getAttribute(SessionConsts.SESSION_CHAT);
        if (chatUser == null) {
            System.err.println("您已在服务器失去连接，请稍候再试...");
            return;
        }

        if (sessionMap.containsKey(chatUser.getId())) {
            Map<String, WebSocketSession> map = sessionMap.get(chatUser.getId());
            for (Map.Entry<String, WebSocketSession> entry : map.entrySet()) {
                WebSocketSession session = entry.getValue();
                ChatObject user = (ChatObject) session.getAttributes().get(SessionConsts.WEBSOCKET_SEESION);
                if (user != null && !chatUser.getUserid().equals(user.getUserid()) && session.isOpen()) {
                    session.sendMessage(msg);
                }
            }
        }
    }

}
