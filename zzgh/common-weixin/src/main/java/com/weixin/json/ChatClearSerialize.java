package com.weixin.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.weixin.bean.chat.ClearChat;

import java.io.IOException;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class ChatClearSerialize extends JsonSerializer<ClearChat> {
    @Override
    public void serialize(ClearChat value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeStringField("op_user", value.getOpUser());
        gen.writeFieldName("chat");
        gen.writeStartObject();
        gen.writeStringField("type", value.getType());
        gen.writeStringField("id", value.getId());
        gen.writeEndObject();
        gen.writeEndObject();
    }
}
