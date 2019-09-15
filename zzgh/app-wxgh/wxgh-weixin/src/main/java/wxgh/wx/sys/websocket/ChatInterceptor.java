package wxgh.wx.sys.websocket;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import wxgh.app.session.chat.SessionConsts;
import wxgh.param.chat.ChatObject;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11.
 */
public class ChatInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger log = Logger.getLogger(ChatInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
            HttpSession session = httpRequest.getServletRequest().getSession(false);
            ChatObject user = (ChatObject) session.getAttribute(SessionConsts.SESSION_CHAT);
            if (user == null) {
                return false;
            }
            attributes.put(SessionConsts.WEBSOCKET_SEESION, user);

            log.info("connect websocket successful（" + user.toString() + "）");
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
