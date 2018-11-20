package com.gpdi.mdata.sys.support;

import pub.dao.PreGenerator;
import pub.dao.ValueGenerator;

import java.beans.PropertyDescriptor;
import java.security.SecureRandom;
import java.sql.Connection;

/**
 * Created by zzl on 2015/7/17.
 */
public class UuidGenerator implements ValueGenerator, PreGenerator {

    private static volatile SecureRandom numberGenerator = null;
    private static final long MSB = 0x8000000000000000L;

    @Override
    public Object generate(Connection cn, PropertyDescriptor fieldDesc) {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }
        String uuid = Long.toHexString(MSB | ng.nextLong()) +
                Long.toHexString(MSB | ng.nextLong());
        return uuid;
    }

}
