package com.gpdi.mdata.sys.support;

import pub.dao.PostGenerator;
import pub.dao.ValueGenerator;
import pub.functions.DbFuncs;

import java.beans.PropertyDescriptor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-4-29
 */
public class IdentityGenerator implements ValueGenerator, PostGenerator {

    @Override
    public Object generate(Connection cn, PropertyDescriptor fieldDesc) {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            cstmt = cn.prepareCall("call IDENTITY()");
            rs = cstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            DbFuncs.tryClose(rs);
            DbFuncs.tryClose(cstmt);
        }
    }

}
