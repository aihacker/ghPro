package pub.dao.utils;

import pub.dao.query.QueryResult;
import pub.types.IdText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-19
 */
public class QueryResultUtils {

    public static void translate(QueryResult<Map<String, Object>> queryResult, String columnName, List<IdText> idTexts) {
        Map<String, String> idTextMap = new HashMap<String, String>();
        for(IdText idText: idTexts) {
            idTextMap.put(String.valueOf(idText.getId()), String.valueOf(idText.getText()));
        }
        for(Map<String, Object> row: queryResult.getRows()) {
            String id = String.valueOf(row.get(columnName));
            row.put(columnName, idTextMap.get(id));
        }
    }

}
