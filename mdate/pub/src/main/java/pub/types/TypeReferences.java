package pub.types;


import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-11-12
 */
public class TypeReferences {

    public static TypeReference<Map<Long, String>> longStringMap = new TypeReference<Map<Long, String>>() {
    };

}
