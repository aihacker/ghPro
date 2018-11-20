package com.gpdi.mdata.web.system.utils;

import pub.system.validation.ValidationRules;

/**
 * Created by zoe on 16/8/8.
 */
public class ValidateUtils {
    public static void setRules(ValidationRules rules) {
        rules.on("varName").required()
                .on("varValue").required()
        ;
    }
}
