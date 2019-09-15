package pub.dao.jdbc.sql.bean;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/29.
 */
public class Where {

    private String where;
    private Logic logic;

    public Where() {
    }

    public Where(String where, Logic logic) {
        this.where = where;
        this.logic = logic;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void toSql(boolean hasLogic, Appendable builder) {
        try {
            if (hasLogic) {
                builder.append(logic.getLogic());
                builder.append(" ");
            }
            builder.append(where);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Logic {
        AND("AND"),
        OR("OR");

        private String logic;

        Logic(String logic) {
            this.logic = logic;
        }

        public String getLogic() {
            return logic;
        }
    }
}
