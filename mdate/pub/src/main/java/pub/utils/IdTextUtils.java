package pub.utils;

import pub.types.IdText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-25
 */
public class IdTextUtils {

    public static Map<Object, Object> buildMap(List<IdText> idTexts) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (IdText idText : idTexts) {
            map.put(idText.getId(), idText.getText());
        }
        return map;
    }

    public static List<IdText> buildList(Object... args) {
        List<IdText> its = new ArrayList<>();
        for (int n = 0; n < args.length; n += 2) {
            IdText it = new IdText(args[n], args[n + 1]);
            its.add(it);
        }
        return its;
    }
}
