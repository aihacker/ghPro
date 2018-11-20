package pub.utils;

import pub.functions.StrFuncs;
import pub.types.IdText;

import java.lang.reflect.Field;
import java.util.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-6-23
 */
@SuppressWarnings("unchecked")
public class ConstsUtils {

    public static List<IdText> buildIdTexts(Class clazz) {
        List<IdText> idTexts = new ArrayList<IdText>();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : fields) {
            Object value = null;
            try {
                value = field.get(null);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(field.getName().toUpperCase(), value);
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
//            if (value instanceof Number) {
            if(StrFuncs.isAllNumber(String.valueOf(value))){
                idTexts.add(new IdText(value, map.get(entry.getKey() + "TEXT")));
            }
        }
        Collections.sort(idTexts, new Comparator<IdText>() {
            @Override
            public int compare(IdText o1, IdText o2) {
                return ((Comparable) o1.id).compareTo(o2.id);
            }
        });
        return idTexts;
    }

}
