package pub.dao.sql.model;

import pub.functions.ColFuncs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-28
 */
public class Columns implements Iterable<Column> {

    private List<Column> columns;

    public Columns() {
        this.columns = columns = new ArrayList<Column>();
    }

    public Columns add(String columnSource) {
        Column column = new Column();
        column.setSource(columnSource);
        this.columns.add(column);
        return this;
    }

    public Columns as(String columnAlias) {
        ColFuncs.last(columns).setAlias(columnAlias);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Column column : columns) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            String source = column.getSource();
            if (source.indexOf(' ') != -1) {
                sb.append('(').append(source).append(')');
            }
            else {
                sb.append(source);
            }
            String alias = column.getAlias();
            if (alias != null && alias.length() > 0) {
                sb.append(" as ").append(alias);
            }
        }
        return sb.toString();
    }

    @Override
    public Iterator<Column> iterator() {
        return columns.iterator();
    }
}
