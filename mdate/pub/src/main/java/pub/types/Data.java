package pub.types;

import org.springframework.ui.ExtendedModelMap;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-5-18
 */
public class Data extends ExtendedModelMap {

	public Data put(Object attributeValue) {
		addAttribute(attributeValue);
		return this;
	}

}