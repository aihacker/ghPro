package pub.spring.web.mvc.controller.support;

import org.springframework.web.servlet.mvc.condition.NameValueExpression;

/**
 * Created by zzl on 2015/9/16.
 */
public class ActionExpression implements NameValueExpression<String> {

    private String action;

    public ActionExpression(String action) {
        this.action = action;
    }

    @Override
    public String getName() {
        return "action";
    }

    @Override
    public String getValue() {
        return action;
    }

    @Override
    public boolean isNegated() {
        return false;
    }
}
