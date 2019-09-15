package com.libs.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by XLKAI on 2017/7/9.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class JsonNoneNull implements Serializable {
}
