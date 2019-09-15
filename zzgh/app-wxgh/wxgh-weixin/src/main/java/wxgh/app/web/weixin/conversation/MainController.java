package wxgh.app.web.weixin.conversation;

import com.qq.weixin.mp.aes.AesException;
import com.weixin.bean.chat.call.ConversationMsg;
import com.weixin.bean.signature.Signature;
import com.weixin.crypt.xml.ConversationXmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wxgh.app.sys.api.ConversationCryptApi;
import wxgh.app.sys.task.WeixinAsync;

/**
 * Created by Administrator on 2017/7/13.
 */
@Controller
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String callback(Signature signature) throws AesException {
        return conversationCryptApi.verifyURL(signature);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String callback(Signature signature, @RequestBody String xml) throws AesException {
        String xmlStr = conversationCryptApi.decryptMsg(signature, xml);

        ConversationMsg msg = ConversationXmlParser.parserConversation(xmlStr);
        if (msg != null) {
            weixinAsync.processConversation(msg);
        }
        return msg.getPackageId();
    }

    @Autowired
    private ConversationCryptApi conversationCryptApi;

    @Autowired
    private WeixinAsync weixinAsync;

}
