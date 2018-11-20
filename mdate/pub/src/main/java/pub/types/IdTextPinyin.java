/**
 *
 * @Creater zrf
 * Sep 10, 2008
 * @Version 1.0
 **/
package pub.types;

import java.io.Serializable;

public class IdTextPinyin extends IdText implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4579663131459551207L;

	private String pinyin;

	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param pinyin the pinyin to set
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}
