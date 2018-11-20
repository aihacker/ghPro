package pub.types;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 12-6-26
 */
public class LongIdText implements Serializable {

	private static final long serialVersionUID = 1L;

    public Long id;
    public String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
