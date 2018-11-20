package pub.functions;

import java.io.Closeable;
import java.io.IOException;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-8-9
 */
public class IoFuncs {

	public static void tryClose(Closeable closable) {
		if (closable == null) {
			return;
		}
		try {
			closable.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
