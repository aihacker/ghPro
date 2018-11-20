package pub.dao.sql;

import pub.dao.query.Sort;
import pub.dao.sql.model.Column;
import pub.dao.sql.model.Columns;
import pub.functions.StrFuncs;
import pub.types.SortableData;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-28
 */
public class SqlFuncs {

    public static String getOrderBy(Columns columns, SortableData sortableData, String defaultOrderBy) {
        Sort sort = new Sort();
        sort.setSort(sortableData.getSort());
        sort.setDir(sortableData.getDir());
        return getOrderBy(columns, sort, defaultOrderBy);
    }

    public static String getOrderBy(Columns columns, Sort sort, String defaultOrderBy) {
        String sortCol = sort.getColumn();
        if (sortCol == null || sortCol.length() == 0) {
            return defaultOrderBy;
        }
        String result = null;
        for (Column column : columns) {
            String name = column.getAlias();
            if (name == null || name.length() == 0) {
                name = column.getSource();
                int pos = name.indexOf('.');
                if (pos != -1) {
                    name = name.substring(pos + 1);
                }
            }
            if (name.equalsIgnoreCase(sortCol)) {
                result = column.getSource();
                break;
            }
        }
        if (result == null) {
            System.out.println("Warning: sort column not found! " + sort.getColumn());
            return defaultOrderBy;
        }
        if(result.indexOf(' ') != -1 && result.charAt(0) != '(') {
            result = '(' + result + ')';
        }
        if (sort.isDesc()) {
            result += " desc";
        }
        return result;
    }

}
