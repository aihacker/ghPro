package com.weixin.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.libs.common.json.JsonUtils;
import com.weixin.bean.chat.message.ChatMsg;
import com.weixin.bean.message.Msg;

import java.io.IOException;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class ChatMsgSerialize extends JsonSerializer<ChatMsg<Msg>> {

    @Override
    public void serialize(ChatMsg<Msg> value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        Msg msg = value.getMsg();
        gen.writeStartObject();
        gen.writeFieldName("receiver");
        gen.writeStartObject();
        gen.writeStringField("type", value.getReceiverType().getType());
        gen.writeStringField("id", value.getReceiverId());
        gen.writeEndObject();
        gen.writeStringField("sender", value.getSender());
        gen.writeStringField("msgtype", msg.getMsgtype());
        gen.writeStringField(msg.getMsgtype(), JsonUtils.stringfy(msg));
        gen.writeEndObject();
    }
}
