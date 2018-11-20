package pub.system.validation.model;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-15
 */
public class MessageModel {

    private RuleInfo rule;
    private FieldInfo field;

    public RuleInfo getRule() {
        return rule;
    }

    public void setRule(RuleInfo rule) {
        this.rule = rule;
    }

    public FieldInfo getField() {
        return field;
    }

    public void setField(FieldInfo field) {
        this.field = field;
    }
}
