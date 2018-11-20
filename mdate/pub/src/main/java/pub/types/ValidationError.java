package pub.types;

import java.io.Serializable;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 11-3-22
 */
public class ValidationError extends RuntimeException implements Serializable {

	public ValidationError(String message) {
		super(message);
	}


}
