package wxgh.app.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.core.io.FileSystemResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * <pre>
 *     密码强度校验
 *
 *     1、令长度应大于等于8个字符
 *     2、口令应由大写字母、小写字母、数字、特殊符号中的3种及以上类型组成
 *     3、不得使用一串相同的数字或字母作为口令
 *     4、口令不能含有与账号名称相同的英文单词、汉语拼音或其简写
 *     5、口令中不应含有明确意义的英文单词或汉语拼音
 *     6、不得使用看似符合要求，实为连续键盘序列组合作为口令
 * </pre>
 *
 * @author 李志慧
 * @date 2017年10月29日
 */
public class CheckPwdComplexity {
    // 口令强度校验工具，单例
    private static CheckPwdComplexity checkPwdComplexity;
    // 连续键盘序列缓存
    private Map<Character, String> seqMap = new HashMap<>();
    // 常用英文单词和拼音缓存，大于等于三个字符
    private TreeSet<String> englishAndPinyin = new TreeSet<>();

    /**
     * 闯将默认的口令强度校验工具
     */
    public CheckPwdComplexity() {
        // 初始化连续键盘序列缓存
        initSeq();
        // 初始化常用英文单词和拼音缓存
     //   initEnglishAndPinyin();
    }

    /**
     * 初始化常用英文单词和拼音缓存
     */
    private void initEnglishAndPinyin() {
        InputStream in = null;
        try {
            //Resource res = new ClassPathResource("englishAndPinyin.txt");
        	FileSystemResource res=new FileSystemResource("C:\\Users\\DELL\\Desktop\\englishAndPinyin.txt");//D:\englishAndPinyin.txt
            in = res.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isNotEmpty(line)) {
                    englishAndPinyin.add(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * 初始化连续键盘序列缓存
     */
    private void initSeq() {
        String seq = "~` ~`!1\n" +
                "!1 ~!@Q`12q\n" +
                "@2 !@#QW123qw\n" +
                "#3 @#$WE234we\n" +
                "$4 #$%ER345er\n" +
                "%5 $%^RT456rt\n" +
                "^6 %^&TY567ty\n" +
                "&7 ^&*YU678yu\n" +
                "*8 &*(UI789ui\n" +
                "(9 *()IO890io\n" +
                ")0 ()_OP90-op\n" +
                "_- )_+P{0-=p[\n" +
                "+= _+{}-=[]\n" +
                "Qq !@QWA123qwa\n" +
                "Ww @#QWESA23qweas\n" +
                "Ee #$WERSD34wersd\n" +
                "Rr $%ERTDF45ertdf\n" +
                "Tt %^RTYFG56rtyfg\n" +
                "Yy ^&TYUGH67tyugh\n" +
                "Uu &*YUIHJ78yuihj\n" +
                "Ii *(UIOJK89uiojk\n" +
                "Oo ()IOPKL90iopkl\n" +
                "Pp )_OP{L:0-op[l;\n" +
                "{[ _+P{}:\"-=p[];'\n" +
                "}] +{}|\"=[]\\'\n" +
                "|\\ }]|\\\n" +
                "Aa QWASZqwasz\n" +
                "Ss WEASDZXweasdzx\n" +
                "Dd ERSDFXCersdfxc\n" +
                "Ff RTDFGCVrtdfgcv\n" +
                "Gg TYFGHVBtyfghvb\n" +
                "Hh YUGHJBNyughjbn\n" +
                "Jj UIHJKNMuihjknm\n" +
                "Kk IOJKLM<iojklm,\n" +
                "Ll OPKL:<>opkl;,.\n" +
                ":; P{L:\">?p[l;'./\n" +
                "\"' {}:\"?[];'/\n" +
                "Zz ASZXaszx\n" +
                "Xx SDZXCsdzxc\n" +
                "Cc DFXCVdfxcv\n" +
                "Vv FGCVBfgcvb\n" +
                "Bb GHVBNghvbn\n" +
                "Nn HJBNMhjbnm\n" +
                "Mm JKNM<jknm,\n" +
                "<, KLM<>klM<>\n" +
                ">. L:<>?l;,./\n" +
                "?/ :\">?;'./";
        String[] lines = seq.split("\\n");
        for (String line : lines) {
            String key = line.split(" ")[0];
            String value = line.split(" ")[1];
            seqMap.put(key.charAt(0), value);
            seqMap.put(key.charAt(1), value);
        }
    }

    /**
     * 创建口令强度校验工具
     *
     * @return 口令强度校验工具
     */
    synchronized public static CheckPwdComplexity newInstance() {
        if (checkPwdComplexity == null) {
            checkPwdComplexity = new CheckPwdComplexity();
        }
        return checkPwdComplexity;
    }

    /**
     * 校验的结果
     */
    public static class Message {
        // 类型
        private int type;
        // 提示信息
        private String msg;

        /**
         * 使用类型和提示信息构造提示信息
         *
         * @param type 类型
         * @param msg  提示信息
         */
        private Message(int type, String msg) {
            this.type = type;
            this.msg = msg;
        }

        /**
         * 获取类型
         *
         * @return 类型
         */
        public int getType() {
            return type;
        }

        /**
         * 设置类型
         *
         * @param type 类型
         */
        public void setType(int type) {
            this.type = type;
        }

        /**
         * 获取提示信息
         *
         * @return 提示信息
         */
        public String getMsg() {
            return msg;
        }

        /**
         * 设置提示信息
         *
         * @param msg 提示信息
         */
        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

    /**
     * 校验口令强度，提示信息为空则校验通过
     *
     * @param dlh 登录号
     * @param xm  姓名
     * @param pwd 口令
     * @return 提示信息
     */
    public Message check(String dlh, String xm, String pwd) {
        Message message = this.checkNull(dlh, xm, pwd);
        if (message == null) {
            message = this.checkLength(dlh, xm, pwd);
        }
        if (message == null) {
            message = this.checkComplexity(dlh, xm, pwd);
        }
        if (message == null) {
            message = this.checkSeq(dlh, xm, pwd);
        }
        if (message == null) {
            message = this.checkDlhAndName(dlh, xm, pwd);
        }
        if (message == null) {
            message = this.checkEnglishAndPinyin(dlh, xm, pwd);
        }
        return message;
    }

    /**
     * 校验口令不能为空
     *
     * @param dlh 登录号
     * @param xm  姓名
     * @param pwd 口令
     * @return 提示信息
     */
    public Message checkNull(String dlh, String xm, String pwd) {
        if (StringUtils.isEmpty(pwd)) {
            return new Message(1, "口令不能为空!");
        }
        return null;
    }

    /**
     * 校验口令长度应大于等于8个字符
     *
     * @param dlh 登录号
     * @param xm  姓名
     * @param pwd 口令
     * @return 提示信息
     */
    public Message checkLength(String dlh, String xm, String pwd) {
        if (pwd.length() < 8) {
            return new Message(2, "口令长度应大于等于8个字符!");
        }
        return null;
    }

    /**
     * 校验口令应由大写字母、小写字母、数字、特殊符号中的3种及以上类型组成
     *
     * @param dlh 登录号
     * @param xm  姓名
     * @param pwd 口令
     * @return 提示信息
     */
    public Message checkComplexity(String dlh, String xm, String pwd) {
        if (pwd != null) {
            char[] chars = pwd.toCharArray();
            int upper = 0;
            int lower = 0;
            int digit = 0;
            int other = 0;
            for (char c : chars) {
                if (Character.isUpperCase(c)) {
                    upper = 1;
                } else if (Character.isLowerCase(c)) {
                    lower = 1;
                } else if (Character.isDigit(c)) {
                    digit = 1;
                } else {
                    other = 1;
                }
            }
            if (upper + lower + digit + other < 3) {
                return new Message(4, "口令应由大写字母、小写字母、数字、特殊符号中的3种及以上类型组成!");
            }
        }
        return null;
    }

    /**
     * 校验口令看似符合要求，实为连续键盘序列组合作为口令
     *
     * @param dlh 登录号
     * @param xm  姓名
     * @param pwd 口令
     * @return 提示信息
     */
    public Message checkSeq(String dlh, String xm, String pwd) {
        String temp = "";
        for (int i = 0; i < pwd.length() - 1; i++) {
            if (seqMap.get(Character.valueOf(pwd.charAt(i))).indexOf(pwd.charAt(i + 1)) > -1) {
                temp = temp.concat("1");
            } else {
                temp = temp.concat(",");
            }
        }
        int max = 0;
        String[] seqs = temp.split(",");
        for (String seq : seqs) {
            max = Math.max(max, seq.length());
        }
        if (max >= 2) {
            return new Message(8, "口令不能使用连续键盘序列组合!");
        }
        return null;
    }

    /**
     * 校验口令不能含有与账号名称相同的英文单词、汉语拼音或其简写
     *
     * @param dlh 登录号
     * @param xm  姓名
     * @param pwd 口令
     * @return 提示信息
     */
    public Message checkDlhAndName(String dlh, String xm, String pwd) {
        if (StringUtils.isNotEmpty(dlh) && pwd.toLowerCase().contains(dlh.toLowerCase())) {
            return new Message(16, " 口令不能含有与账号名称相同的英文单词、汉语拼音或其简写!");
        }
        if (StringUtils.isNotEmpty(xm) && pwd.toLowerCase().contains(xm.toLowerCase())) {
            return new Message(16, " 口令不能含有与账号名称相同的英文单词、汉语拼音或其简写!");
        }
        if (StringUtils.isNotEmpty(xm) && pwd.toLowerCase().contains(getPingYin(xm).toLowerCase())) {
            return new Message(16, " 口令不能含有与账号名称相同的英文单词、汉语拼音或其简写!");
        }
        if (StringUtils.isNotEmpty(xm) && pwd.toLowerCase().contains(getFirstPingYin(xm).toLowerCase())) {
            return new Message(16, " 口令不能含有与账号名称相同的英文单词、汉语拼音或其简写!");
        }
        return null;
    }

    /**
     * 校验口令中不应含有明确意义的英文单词或汉语拼音
     *
     * @param dlh 登录号
     * @param xm  姓名
     * @param pwd 口令
     * @return 提示信息
     */
    public Message checkEnglishAndPinyin(String dlh, String xm, String pwd) {
        pwd = pwd.toLowerCase();
        for (int i = 0; i < pwd.length() - 2; i++) {
            for (int j = i + 2; j <= pwd.length(); j++) {
                if (englishAndPinyin.contains(pwd.substring(i, j))) {
                    return new Message(32, "口令中不应含有明确意义的英文单词或汉语拼音!");
                }
            }
        }
        return null;
    }

    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param chinese 汉字
     * @return 拼音全拼
     */
    private String getPingYin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder output = new StringBuilder();
        if (chinese != null && chinese.length() > 0) {
            char[] input = chinese.trim().toCharArray();
            try {
                for (char anInput : input) {
                    if (Character.toString(anInput).matches("[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(anInput, format);
                        output.append(temp[0]);
                    } else {
                        output.append(Character.toString(anInput));
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "";
        }
        return output.toString();
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     *
     * @param chinese 汉字
     * @return 拼音简拼
     */
    private String getFirstPingYin(String chinese) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aNameChar : nameChar) {
            if (aNameChar > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(aNameChar, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(aNameChar);
            }
        }
        return pinyinName.toString();
    }

}
