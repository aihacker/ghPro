package pub.types;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-5-1
 */
public class NotImplementedException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotImplementedException() {
		//
	}

	public NotImplementedException(String message) {
		super(message);
	}

}
