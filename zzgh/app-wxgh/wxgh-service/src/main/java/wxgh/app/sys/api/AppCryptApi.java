package wxgh.app.sys.api;

import com.qq.weixin.mp.aes.AesException;
import com.weixin.crypt.BaseWxCrypt;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/13.
 */
@Component
public class AppCryptApi extends BaseWxCrypt {

    public AppCryptApi() throws AesException {
        super("2z2sivrPC5xR5KPBkRje2", "oM6fkvPQKA8zLvvJagnh5oMaNbrau5AiLBGwJmUt2ot");
    }
}
