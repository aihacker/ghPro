package pub.dao.jdbc.sql.bean;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/29.
 */
public class Join {

    private String table;
    private String on;
    private Type joinType;

    public Join() {
    }

    public Join(String table, String on, Type joinType) {
        this.table = table;
        this.on = on;
        this.joinType = joinType;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public Type getJoinType() {
        return joinType;
    }

    public void setJoinType(Type joinType) {
        this.joinType = joinType;
    }

    public void toSql(Appendable builder) {
        try {
            builder.append(joinType.getType());
            builder.append(" JOIN ");
            builder.append(table);
            builder.append(" ON ");
            builder.append(on);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Type {
        INNER("INNER"), //相等联接、内联接
        LEFT("LEFT"), //左联接
        RIGHT("RIGHT"); //右联接

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
