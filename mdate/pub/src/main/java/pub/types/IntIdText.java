package pub.types;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 12-6-26
 */
public class IntIdText implements Serializable {

	private static final long serialVersionUID = 1L;

    public Integer id;
    public String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
