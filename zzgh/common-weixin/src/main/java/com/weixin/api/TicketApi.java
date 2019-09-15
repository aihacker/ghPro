package com.weixin.api;

import com.libs.common.cache.AgentCache;
import com.libs.common.json.JsonUtils;
import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.Ticket;
import com.weixin.bean.token.TokenVal;
import com.weixin.utils.DbUtils;
import com.weixin.utils.WeixinHttp;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2017/7/13.
 */
public class TicketApi {

    private static final Logger log = Logger.getLogger(TicketApi.class);
    private static ConcurrentMap<Integer, Ticket> ticketMap = new ConcurrentHashMap<>();

    public static String getTicket() throws WeixinException {
        return getTicket(null);
    }

    public static String getTicket(Integer agentId) throws WeixinException {
        if (agentId == null) {
            agentId = AgentCache.get();
        }
        Ticket ticket = ticketMap.getOrDefault(agentId, null);
        if (ticket == null || isTimeout(ticket)) {
            ticket = getFileTicket(agentId);
            if (ticket == null || isTimeout(ticket)) {
                ticket = getWxTicket(agentId);
            }
            ticketMap.put(agentId, ticket);
        }
        return ticket.getTicket();
    }

    private static Ticket getWxTicket(Integer agentId) throws WeixinException {
        String url = Weixin.getAgentURL("get_jsapi_ticket", agentId);
        Ticket ticket = WeixinHttp.get(url, Ticket.class);
        if (log.isDebugEnabled()) {
            log.debug("get new weixin jsapi_ticket from weixin's server：" + JsonUtils.stringfy(ticket));
        }
        writeTicket(ticket, agentId);
        return ticket;
    }

    private static boolean isTimeout(Ticket ticket) {
        if ((System.currentTimeMillis() - ticket.getAddTime()) / 1000 > ticket.getExpires() - 40) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件ticket
     *
     * @param agentId
     * @return
     */
    public static Ticket getFileTicket(Integer agentId) {
        TokenVal tokenVal = DbUtils.getVal(Weixin.getName(TokenVal.VAL_TICKET, agentId));
        if (tokenVal != null) {
            Ticket ticket = JsonUtils.parseBean(tokenVal.getVal(), Ticket.class);
            return ticket;
        }
        return null;
    }

    /**
     * 将ticket写入到文件
     *
     * @param ticket
     * @param agentId
     */
    public static void writeTicket(Ticket ticket, Integer agentId) {
        ticket.setAddTime(System.currentTimeMillis());

        DbUtils.addVal(Weixin.getName(TokenVal.VAL_TICKET, agentId), JsonUtils.stringfy(ticket));
    }
}
