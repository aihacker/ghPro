package pub.models.listview;

import pub.types.Formatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-29
 */
public class MapBasedListViewModel implements ListViewModel {

    private List<Map<String, Object>> rows;
    private List<String> colNames;
    private List<String> colTitles;
    private Map<String, Formatter> formatters;

    public MapBasedListViewModel() {
        formatters = new HashMap<String, Formatter>();
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public List<String> getColNames() {
        return colNames;
    }

    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }

    public List<String> getColTitles() {
        return colTitles;
    }

    public void setColTitles(List<String> colTitles) {
        this.colTitles = colTitles;
    }

    public Map<String, Formatter> getFormatters() {
        return formatters;
    }

    public void setFormatters(Map<String, Formatter> formatters) {
        this.formatters = formatters;
    }

    @Override
    public String getColTitle(int col) {
        return colTitles.get(col);
    }

    @Override
    public Object getCellValue(int rowIndex, int colIndex) {
        String colName = colNames.get(colIndex);
        Map<String, Object> row = rows.get(rowIndex);
        Object value = row.get(colName);
        Formatter formatter = formatters.get(colName);
        if(formatter != null) {
            value = formatter.format(value);
        }
        return value;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColCount() {
        return colTitles.size();
    }
}
