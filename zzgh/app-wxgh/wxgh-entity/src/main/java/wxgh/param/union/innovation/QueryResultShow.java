package wxgh.param.union.innovation;

import java.io.Serializable;

/**
 * Created by WIN on 2016/8/25.
 */
public class QueryResultShow implements Serializable {

    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
