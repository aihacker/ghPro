package wxgh.app.sys.api;

import com.qq.weixin.mp.aes.AesException;
import com.weixin.crypt.BaseWxCrypt;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/4/12.
 */
@Component
public class ConversationCryptApi extends BaseWxCrypt {

    public ConversationCryptApi() throws AesException {
        super("imwh2Pr4GVad7MxNgpheW2QycAE0Yn", "VAqc1C5XcwPlt1DoeYDrWOvgBzpNhgTlo3qh9MSHGzk");
    }
}
