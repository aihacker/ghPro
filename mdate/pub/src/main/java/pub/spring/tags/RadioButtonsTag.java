package pub.spring.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pub.types.IdText;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-5-2
 * Time: 23:38:39
 */
@SuppressWarnings("unchecked")
public class RadioButtonsTag extends org.springframework.web.servlet.tags.form.RadioButtonsTag {

	private static final long serialVersionUID = 1L;

	private String nullItem;

	public RadioButtonsTag() {
		setItemLabel("text");
		setItemValue("id");
		setDelimiter("&nbsp;");
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
