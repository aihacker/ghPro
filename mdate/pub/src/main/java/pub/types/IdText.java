package pub.types;

import java.io.Serializable;

/**
 * describe: Created by IntelliJ IDEA. @author zzl Date: 2008-3-5 Time: 17:41:12
 */
// *******************************************************************************
/*
 * 郝金隆 2008-08-20
 *
 * 修改内容：添加serialVersionUID，以避免因为版本不同引起错误
 */
public class IdText implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -286766548785891716L;
	public Object id;
	public Object text;

	public IdText() {

	}

	public IdText(Object id, Object text) {
		this.id = id;
		this.text = text == null? "": text;
	}
//
//	public <T1, T2> IdText(T1 id, T2 text) {
//		this(id.toString(), text == null ? "" : text.toString());
//	}

//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public String getText() {
//		return text;
//	}

//	public Integer getIntId() {
//		return Integer.valueOf(id);
//	}


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }
}
