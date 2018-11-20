package pub.spring.tags;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;
import pub.types.IdText;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-12
 * Time: 16:38:24
 */
@SuppressWarnings("unchecked")
public class SelectTag extends org.springframework.web.servlet.tags.form.SelectTag {

	private static final long serialVersionUID = 1L;

	private String nullItem;
	private String firstItem;

	public SelectTag() {
		setItemLabel("text");
		setItemValue("id");
	}

	protected String getNullItem() {
		return nullItem;
	}
	public void setNullItem(String nullItem) {
		this.nullItem = nullItem;
	}

	public String getFirstItem() {
		return firstItem;
	}

	public void setFirstItem(String firstItem) {
		this.firstItem = firstItem;
	}

	@Override
	protected String getName() throws JspException {
		//return super.getName();
		return getPropertyPath();
	}

	@Override
	protected String autogenerateId() throws JspException {
		//return super.autogenerateId();
		return StringUtils.deleteAny(getPropertyPath(), "[]");
	}

	@Override
	protected Object getItems() {
		Object items = super.getItems();
		if (this.nullItem != null) {
			if (items == null || items.getClass().equals(Object.class)) {
				items = new ArrayList();
			}
			List itemList = new ArrayList((Collection) items);
			itemList.add(0, new IdText("", nullItem));
			items = itemList;
		}
		else if(this.firstItem != null) {
			List itemList = (List)items;
			if(items != null && itemList.size() > 0) {
				Object firstItemObj = itemList.get(0);
				//
				try {
					BeanUtils.setProperty(firstItemObj, getItemLabel(), firstItem);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return items;
	}

	@Override
	public int doEndTag() throws JspException {
		int result = super.doEndTag();

		if (Boolean.TRUE.equals(pageContext.getAttribute("_FORM_TAG_SHOW_ERRORS"))) {
			ErrorsTag errorsTag = new ErrorsTag();
			errorsTag.setPageContext(this.pageContext);
			errorsTag.setParent(getParent());
			errorsTag.setPath(getPath());
			try {
				errorsTag.doStartTag();
				errorsTag.doEndTag();
			}
			finally {
				errorsTag.doFinally();
			}
		}
		//
		return result;
	}
}
