package com.weixin.api;

import com.libs.common.cache.AgentCache;
import com.libs.common.json.JsonUtils;
import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.ContactTicket;
import com.weixin.bean.token.TokenVal;
import com.weixin.utils.DbUtils;
import com.weixin.utils.WeixinHttp;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2017/7/13.
 */
public class ContactTicketApi {

    private static final Logger log = Logger.getLogger(ContactTicketApi.class);
    private static ConcurrentMap<Integer, ContactTicket> ticketMap = new ConcurrentHashMap<>();

    public static ContactTicket getTicket(Integer agentId) throws WeixinException {
        if (agentId == null) {
            agentId = AgentCache.get();
        }
        ContactTicket ticket = ticketMap.getOrDefault(agentId, null);
        if (ticket == null || isTimeout(ticket)) {
            ticket = getFileTicket(agentId);
            if (ticket == null || isTimeout(ticket)) {
                ticket = getWxTicket(agentId);
            }
            ticketMap.put(agentId, ticket);
        }
        return ticket;
    }

    public static ContactTicket getTicket() throws WeixinException {
        return getTicket(null);
    }

    private static ContactTicket getWxTicket(Integer agentId) throws WeixinException {
        String url = Weixin.getAgentURL("ticket/get?type=contact", agentId);
        ContactTicket ticket = WeixinHttp.get(url, ContactTicket.class);
        if (log.isDebugEnabled()) {
            log.debug("get new weixin jsapi_ticket from weixin's server：" + JsonUtils.stringfy(ticket));
        }
        writeTicket(ticket, agentId);
        return ticket;
    }

    private static boolean isTimeout(ContactTicket ticket) {
        if ((System.currentTimeMillis() - ticket.getAddTime()) / 1000 > ticket.getExpiresIn() - 40) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件ticket
     *
     * @return
     */
    public static ContactTicket getFileTicket(Integer agentId) {
        TokenVal tokenVal = DbUtils.getVal(Weixin.getName(TokenVal.VAL_CONTACT_TICKET, agentId));
        if (tokenVal != null) {
            ContactTicket ticket = JsonUtils.parseBean(tokenVal.getVal(), ContactTicket.class);
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
    public static void writeTicket(ContactTicket ticket, Integer agentId) {
        ticket.setAddTime(System.currentTimeMillis());

        DbUtils.addVal(Weixin.getName(TokenVal.VAL_CONTACT_TICKET, agentId), JsonUtils.stringfy(ticket));
    }
}
