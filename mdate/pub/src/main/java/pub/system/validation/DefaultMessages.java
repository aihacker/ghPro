package pub.system.validation;


/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-15
 */
public class DefaultMessages {

//    public static final String required = T("err.default.required");//  不可为空
//    public static final String min = T("err.default.min");//  最小值为{rule.param}
//    public static final String max = T("err.default.max");//  最大值为{rule.param}
//    public static final String telno = T("err.default.telno");//  无效电话号码
//    public static final String mobile = T("err.default.mobile");//  无效手机号码
//    public static final String minLength = T("err.default.minLength");//  不可少于{rule.param}个字符
//    public static final String maxLength = T("err.default.maxLength");//  不可超过{rule.param}个字符
//    public static final String custom = T("err.default.custom");//  无效
//    public static final String number = T("err.default.number");//  请输入数值
//    public static final String integer = T("err.default.integer");//  请输入整数
//    public static final String email = T("err.default.email");//  不是有效的email
//    public static final String url = T("err.default.url");//  不是有效的URL.
//    public static final String digits = T("err.default.digits");//  只允许输入数字

    public static String get(String s) {
        String key = "err.default." + s;
        return s;// PubTranslator.T(key);
    }



}
