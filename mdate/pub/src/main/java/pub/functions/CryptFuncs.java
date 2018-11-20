package pub.functions;

import org.apache.commons.codec.binary.Hex;
import pub.support.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version 2008-9-4
 */
public class CryptFuncs {

    public static String getMd5(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert msgDigest != null;
        if (text == null) {
            text = "";
        }
        msgDigest.update(text.getBytes());
        byte[] bts = msgDigest.digest();
        String result = StrFuncs.byte2hex(bts);
        return result;
    }

    public static String getHMac(String msg, String key) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("utf-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            byte[] rawHmac = mac.doFinal(msg.getBytes("utf-8"));
            String result = Hex.encodeHexString(rawHmac);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class AsciiIndexMap {
        static List<Integer> mapIndexes;
        static List<Integer> unMapIndexes;

        static {
            mapIndexes = new ArrayList<Integer>();
            for (int n = 0; n < 95; n++) {
                mapIndexes.add(n);
            }
            Collections.shuffle(mapIndexes, new Random(0));

            unMapIndexes = new ArrayList<Integer>(mapIndexes);
            for (int n = 0; n < 95; n++) {
                unMapIndexes.set(mapIndexes.get(n), n);
            }
        }
    }

    public static String makeAsciiUnreadable(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }
        String text1 = mapIndex(text, AsciiIndexMap.mapIndexes);
        return Base64.encode(text1);
    }

    public static String makeAsciiReadable(String text) {
        text = new String(Base64.decode(text));
        return mapIndex(text, AsciiIndexMap.unMapIndexes);
    }

    private static String mapIndex(String text, List<Integer> indexes) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < text.length(); n++) {
            int c = text.charAt(n);
            sb.append((char) (int) (indexes.get(c - 32) + 32));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String ts = "hello world";
        String ts1 = makeAsciiUnreadable(ts);
        System.out.println(ts1);
        String ts2 = makeAsciiReadable(ts1);
        System.out.println(ts2);
    }
}
