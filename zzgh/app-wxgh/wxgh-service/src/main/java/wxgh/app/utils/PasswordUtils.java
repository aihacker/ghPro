package wxgh.app.utils;

import com.libs.common.crypt.MD5;
import com.libs.common.crypt.SHA1;
import com.libs.common.type.TypeUtils;

/**
 * Created by Administrator on 2017/8/9.
 */
public class PasswordUtils {

    public static boolean equles(String password, String dbpassword) {
        if (TypeUtils.empty(password)) return false;
        return getPassword(password).equals(dbpassword);
    }

    public static String getPassword(String password) {
        password = SHA1.encrypt(password);
        password = MD5.encrypt(password);
        return password;
    }

}
