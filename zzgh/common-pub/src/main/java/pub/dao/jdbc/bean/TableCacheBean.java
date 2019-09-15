package pub.dao.jdbc.bean;

import pub.dao.jdbc.analyze.TableAnalyze;
import pub.dao.jdbc.sql.SQL;

/**
 * Created by Administrator on 2017/7/12.
 */
public class TableCacheBean {

    private String[] entityPackages;
    private String tempDir;
    private String tablePrefix;

    public void setEntityPackages(String[] entityPackages) {
        this.entityPackages = entityPackages;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        SQL.PREFIX = tablePrefix;
    }

    public void init() {
        TableAnalyze analyze = new TableAnalyze(tempDir, tablePrefix);
        analyze.analyze(entityPackages);
    }
}
