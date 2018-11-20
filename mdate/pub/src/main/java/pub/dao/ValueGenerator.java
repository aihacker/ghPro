package pub.dao;

import java.beans.PropertyDescriptor;
import java.sql.Connection;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-3-18
 */
public interface ValueGenerator {

    Object generate(Connection cn, PropertyDescriptor fieldDesc);

}
