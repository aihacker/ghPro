package com.libs.common.cache;

import org.springframework.cache.concurrent.ConcurrentMapCache;

/**
 * Created by Administrator on 2017/9/5.
 */
public class AgentCache {

    public static final String AGENT_KEY = "gczx.agent";

    private static ConcurrentMapCache cache;

    static {
        cache = new ConcurrentMapCache("gczx_agent");
    }

    public static Integer get() {
        Integer agentId = cache.get(AGENT_KEY, Integer.class);
        return null == agentId ? -1 : agentId;
    }

    public static void set(Integer agentid) {
        cache.put(AGENT_KEY, agentid);
    }
}
