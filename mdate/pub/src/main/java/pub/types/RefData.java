package pub.types;

import org.springframework.ui.ExtendedModelMap;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-5-18
 */
public class RefData extends ExtendedModelMap {

	public RefData put(Object attributeValue) {
		addAttribute(attributeValue);
		return this;
	}
	
}
