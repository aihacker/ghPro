package pub.functions;

import java.util.Random;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2009-9-17
 */
public class MathFuncs {

	private static Random random = new Random();

	public static int randInt() {
		return random.nextInt();
	}

}
