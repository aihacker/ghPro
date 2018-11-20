package pub.beans;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import pub.beans.converter.ConvertUtilsBeanEx;


/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @since 2008-9-14 3:18:05
 */
public class BeanUtilsBeanEx extends BeanUtilsBean {

	public BeanUtilsBeanEx() {
		super(new ConvertUtilsBeanEx(), new PropertyUtilsBean());
	}

}
