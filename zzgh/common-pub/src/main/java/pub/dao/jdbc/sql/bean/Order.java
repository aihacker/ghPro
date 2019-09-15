package pub.dao.jdbc.sql.bean;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/29.
 */
public class Order {

    private String fields;
    private Type type = Type.ASC;

    public Order() {
    }

    public Order(String fields, Type type) {
        this.fields = fields;
        this.type = type;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void toSql(Appendable builder) {
        try {
            builder.append(fields);
            builder.append(" ");
            builder.append(type.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Type {
        DESC("DESC"),
        ASC("ASC");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
