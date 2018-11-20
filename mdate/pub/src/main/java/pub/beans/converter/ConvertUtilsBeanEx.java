package pub.beans.converter;

import org.apache.commons.beanutils.ConvertUtilsBean;

import pub.functions.ColFuncs;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @since 2008-9-14 3:18:44
 */
public class ConvertUtilsBeanEx extends ConvertUtilsBean {

	public Object convert(String values[], Class clazz) {
		Object[] arr = (Object[]) super.convert(values, clazz);
		arr = ColFuncs.compactArray(arr);
		return arr;
	}
}
