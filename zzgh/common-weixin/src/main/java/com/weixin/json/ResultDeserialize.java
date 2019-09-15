package com.weixin.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.libs.common.json.JsonUtils;
import com.weixin.bean.result.batch.PartyResult;
import com.weixin.bean.result.batch.UserResult;
import com.weixin.bean.result.batch.WorkResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class ResultDeserialize extends JsonDeserializer<WorkResult> {
    @Override
    public WorkResult deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        WorkResult result = new WorkResult();
        while (p.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = p.getCurrentName();
            p.nextToken();
            if ("errcode".equals(fieldName)) {
                result.setErrcode(p.getValueAsInt());
            } else if ("errmsg".equals(fieldName)) {
                result.setErrmsg(p.getText());
            } else if ("status".equals(fieldName)) {
                result.setStatus(p.getValueAsInt());
            } else if ("type".equals(fieldName)) {
                result.setType(p.getText());
            } else if ("total".equals(fieldName)) {
                result.setTotal(p.getValueAsInt());
            } else if ("percentage".equals(fieldName)) {
                result.setPercentage(p.getValueAsInt());
            } else if ("remaintime".equals(fieldName)) {
                result.setRemaintime(p.getValueAsInt());
            } else if ("result".equals(fieldName)) {
                if (result.getType().equals("sync_user") || result.getType().equals("replace_user")) {
                    List<UserResult> userResults = new ArrayList<>();
                    while (p.nextToken() == JsonToken.START_OBJECT) {
                        userResults.add(JsonUtils.getJson().readValue(p, UserResult.class));
                    }
                    result.setResult(userResults);
                } else {
                    List<PartyResult> partyResults = new ArrayList<>();
                    while (p.nextToken() == JsonToken.START_OBJECT) {
                        partyResults.add(JsonUtils.getJson().readValue(p, PartyResult.class));
                    }
                    result.setResult(partyResults);
                }
            }
        }
        return result;
    }
}
