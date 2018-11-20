package pub.spring.tags;

import pub.types.IdText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-5-3
 * Time: 16:22:16
 */
@SuppressWarnings("unchecked")
public class CheckboxesTag extends org.springframework.web.servlet.tags.form.CheckboxesTag {

	private static final long serialVersionUID = 1L;

	private String nullItem;

	public CheckboxesTag() {
		setItemLabel("text");
		setItemValue("id");
//		setDelimiter("&nbsp;");
		setDelimiter("");
	}

	protected String getNullItem() {
		return nullItem;
	}
	public void setNullItem(String nullItem) {
		this.nullItem = nullItem;
	}

	@Override
	protected Object getItems() {
		Object items = super.getItems();
		if (this.nullItem != null) {
			if (items == null) {
				items = new ArrayList();
			}
			List itemList = new ArrayList((Collection) items);
			itemList.add(0, new IdText("", nullItem));
			items = itemList;
		}
		return items;
	}

}
