package pub.functions;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-4-7
 */
public class LogicFuncs {

	public static <T> T iif(Boolean cond, T o1, T o2) {
		return cond ? o1 : o2;
	}

}
