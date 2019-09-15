package com.weixin.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.libs.common.type.TypeUtils;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.Msg;

import java.io.IOException;

/**
 * Created by XLKAI on 2017/7/11.
 */

public class MessageSerialize extends JsonSerializer<Message> {

    @Override
    public void serialize(Message value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        if (!TypeUtils.empty(value.getTouser())) {
            gen.writeStringField("touser", TypeUtils.listToStr(value.getTouser(), "|"));
        }
        if (!TypeUtils.empty(value.getToparty())) {
            gen.writeStringField("toparty", TypeUtils.listToStr(value.getToparty(), "|"));
        }
        if (!TypeUtils.empty(value.getTotag())) {
            gen.writeStringField("totag", TypeUtils.listToStr(value.getTotag(), "|"));
        }
        Msg msg = value.getMsg();
        gen.writeStringField("msgtype", msg.getMsgtype());
        gen.writeNumberField("agentid", value.getAgentid());
        if (value.getSafe() != null) {
            gen.writeNumberField("safe", value.getSafe());
        }
        gen.writeObjectField(msg.getMsgtype(), msg);
        gen.writeEndObject();
    }
}
