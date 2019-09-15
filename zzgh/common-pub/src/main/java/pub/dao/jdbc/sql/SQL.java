package pub.dao.jdbc.sql;

import com.libs.common.type.TypeUtils;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.jdbc.sql.bean.Where;
import pub.error.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */
public class SQL {

    public static String PREFIX = "t_";

    public enum StatementType {
        DELETE, INSERT, SELECT, UPDATE, COUNT
    }

    private StatementType statementType;
    private List<String> tables;
    private List<String> fields;
    private List<Where> wheres;
    private List<String> sets;
    private List<Join> joins;
    private List<Order> orders;
    private List<String> groupBy;
    private List<String> values;
    private String limit;
    private boolean distinct;
    private String count;
    private String startSql;

    public static String deleteByIds(String table, String ids) {
        if (TypeUtils.empty(ids)) throw new ValidationError("请选择需要删除的数据");
        SQL sql = new SqlBuilder()
                .where("id in (" + ids + ")")
                .delete(table)
                .build();
        return sql.sql();
    }

    private SQL(SqlBuilder b) {
        this.tables = b.tables;
        this.fields = b.fields;
        this.joins = b.joins;
        this.wheres = b.wheres;
        this.sets = b.sets;
        this.orders = b.orders;
        this.limit = b.limit;
        this.statementType = b.statementType;
        this.groupBy = b.groupBy;
        this.distinct = b.distinct;
        this.values = b.values;
        this.count = b.count;
        this.startSql = b.startSql;
    }

    private void whereSql(StringBuilder builder) {
        if (!TypeUtils.empty(wheres)) {
            builder.append("\nWHERE ");
            int i = 0;
            for (Where where : wheres) {
                if (i > 0) {
                    builder.append(" ");
                }
                where.toSql(i > 0, builder);
                i++;
            }
        }
    }

    private void joinSql(StringBuilder builder) {
        if (!TypeUtils.empty(joins)) {
            builder.append("\n");
            int i = 0;
            for (Join join : joins) {
                if (i > 0) builder.append("\n");
                join.toSql(builder);
                i++;
            }
        }
    }

    private String countSql(StringBuilder builder) {
        builder.append("SELECT ");
        builder.append("COUNT(" + count + ")");
        builder.append(" FROM ");
        builder.append(TypeUtils.listToStr(tables));

        //连表
        joinSql(builder);

        //条件
        whereSql(builder);

//        if (!TypeUtils.empty(groupBy)) {
//            builder.append(" GROUP BY ");
//            builder.append(TypeUtils.listToStr(groupBy));
//        }

        return builder.toString();
    }

    private String selectSql(StringBuilder builder) {
        builder.append("SELECT ");
        if (distinct) {
            builder.append("DISTINCT ");
        }

        //字段
        if (TypeUtils.empty(fields)) {
            builder.append("*");
        } else {
            builder.append(TypeUtils.listToStr(fields));
        }

        //表
        builder.append(" FROM ");
        builder.append(TypeUtils.listToStr(tables));

        //连表
        joinSql(builder);

        //条件
        whereSql(builder);

        //group by
        if (!TypeUtils.empty(groupBy)) {
            builder.append(" GROUP BY ");
            builder.append(TypeUtils.listToStr(groupBy));
        }

        //order by
        if (!TypeUtils.empty(orders)) {
            builder.append(" ORDER BY ");
            int i = 0;
            for (Order order : orders) {
                if (i > 0) {
                    builder.append(", ");
                }
                order.toSql(builder);
                i++;
            }
        }

        //limit
        if (limit != null && limit.length() > 0) {
            builder.append(" LIMIT ");
            builder.append(limit);
        }
        return builder.toString();
    }

    private String deleteSql(StringBuilder builder) {
        builder.append("DELETE FROM ");
        builder.append(TypeUtils.listToStr(tables));

        //join
        joinSql(builder);

        //where
        whereSql(builder);

        return builder.toString();
    }

    private String updateSql(StringBuilder builder) {
        builder.append("UPDATE ");
        builder.append(TypeUtils.listToStr(tables));

        //set
        if (!TypeUtils.empty(sets)) {
            builder.append(" SET ");
            builder.append(TypeUtils.listToStr(sets));
        }

        //where
        whereSql(builder);
        return builder.toString();
    }

    private String insertSql(StringBuilder builder) {
        builder.append("INSERT INTO ");
        builder.append(TypeUtils.listToStr(tables));

        //fields
        builder.append("(");
        builder.append(TypeUtils.listToStr(fields));
        builder.append(")");

        //values
        builder.append(" VALUES (");
        builder.append(TypeUtils.listToStr(values));
        builder.append(")");

        return builder.toString();
    }

    public String sql() {
        StringBuilder builder = new StringBuilder();
        if (!TypeUtils.empty(startSql)) {
            builder.append(startSql + ";\n");
        }
        String sql = null;
        switch (statementType) {
            case SELECT:
                sql = selectSql(builder);
                break;
            case COUNT:
                sql = countSql(builder);
                break;
            case DELETE:
                sql = deleteSql(builder);
                break;
            case INSERT:
                sql = insertSql(builder);
                break;
            case UPDATE:
                sql = updateSql(builder);
                break;
        }
        return sql;
    }

    public static class SqlBuilder {
        private StatementType statementType = StatementType.SELECT;
        private List<String> tables = new ArrayList<>();
        private List<String> sets = new ArrayList<>();
        private List<String> fields = new ArrayList<>();
        private List<Where> wheres = null;
        private List<Join> joins = null;
        private List<Order> orders = null;
        private List<String> groupBy = null;
        private List<String> values = null;
        private String limit = null;
        private boolean distinct = false;
        private String count = null;
        private String startSql = null;

        public SqlBuilder sys_file(String column) {
            return this.join("sys_file f", "f.file_id = " + column, Join.Type.LEFT)
                    .field("f.file_path as path, f.thumb_path as thumb");
        }

        public SqlBuilder simple_user(String column) {
            return this.join("user u", "u.userid = " + column)
                    .join("dept d", "d.id = u.deptid")
                    .field("u.userid, u.name as username, u.avatar, u.mobile, d.name as deptname");
        }

        public SqlBuilder select(String table) {
            this.statementType = StatementType.SELECT;
            return table(table);
        }

        public SqlBuilder start(String sql) {
            this.startSql = sql;
            return this;
        }

        public SqlBuilder select() {
            return select(null);
        }

        public SqlBuilder update(String table) {
            this.statementType = StatementType.UPDATE;
            if (table != null) {
                this.tables.clear();
            }
            return table(table);
        }

        public SqlBuilder update() {
            return update(null);
        }

        public SqlBuilder delete(String table) {
            this.statementType = StatementType.DELETE;
            if (table != null) {
                this.tables.clear();
            }
            return table(table);
        }

        public SqlBuilder delete() {
            return delete(null);
        }

        public SqlBuilder insert(String table) {
            this.statementType = StatementType.INSERT;
            if (table != null) {
                this.tables.clear();
            }
            return table(table);
        }

        public SqlBuilder insert() {
            return insert(null);
        }

        public SqlBuilder distinct() {
            this.distinct = true;
            return this;
        }

        public SqlBuilder set(String set) {
            if (TypeUtils.empty(sets)) {
                sets = new ArrayList<>();
            }
            this.sets.add(set);
            return this;
        }

        public SqlBuilder groupBy(String group) {
            if (group == null) {
                groupBy = null;
            } else {
                if (TypeUtils.empty(groupBy)) {
                    groupBy = new ArrayList<>();
                }
                this.groupBy.add(group);
            }
            return this;
        }

        public SqlBuilder value(String values) {
            if (TypeUtils.empty(this.values)) {
                this.values = new ArrayList<>();
            }
            this.values.add(values);
            return this;
        }

        public SqlBuilder table(String table) {
            if (table == null) {
                return this;
            }
            if (!table.startsWith(PREFIX)) {
                table = PREFIX + table;
            }
            tables.add(table);
            return this;
        }

        // 自定义table
        public SqlBuilder tableCustom(String table) {
            if (table == null) {
                return this;
            }
            tables.add(table);
            return this;
        }

        public SqlBuilder field(String fields) {
            this.fields.add(fields);
            return this;
        }

        public SqlBuilder where(String where) {
            return where(where, Where.Logic.AND);
        }

        public SqlBuilder where(String where, Where.Logic logic) {
            if (this.wheres == null) {
                this.wheres = new ArrayList<>();
            }
            this.wheres.add(new Where(where, logic));
            return this;
        }

        public SqlBuilder join(String table, String on, Join.Type joinType) {
            if (this.joins == null) {
                this.joins = new ArrayList<>();
            }
            if (!table.startsWith(PREFIX)) {
                table = PREFIX + table;
            }
            this.joins.add(new Join(table, on, joinType));
            return this;
        }

        public SqlBuilder join(String table, String on) {
            return join(table, on, Join.Type.INNER);
        }

        public SqlBuilder order(String fields, Order.Type orderType) {
            if (this.orders == null) {
                this.orders = new ArrayList<>();
            }
            this.orders.add(new Order(fields, orderType));
            return this;
        }

        public SqlBuilder order(String fields) {
            return order(fields, Order.Type.ASC);
        }

        public SqlBuilder limit(String limit) {
            this.limit = limit;
            return this;
        }

        public SqlBuilder count() {
            return count("*");
        }

        public SqlBuilder count(String expr) {
            this.statementType = StatementType.COUNT;
            this.count = expr;
            return this;
        }

        public SQL build() {
            return new SQL(this);
        }

    }
}
