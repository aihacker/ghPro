package pub.functions;

import org.apache.commons.codec.binary.Base64;
import pub.types.IdText;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @since 2008-9-11 15:13:03
 */
@SuppressWarnings("unchecked")
public class VarFuncs {

    public static boolean equals(Object v1, Object v2) {
        if (v1 == null && v2 == null) {
            return true;
        }
        if (v1 == null) {
            if (v2 instanceof String) {
                return ((String) v2).isEmpty();
            }
            return false;
        } else if (v2 == null) {
            if (v1 instanceof String) {
                return ((String) v1).isEmpty();
            }
            return false;
        }
        if (v1.equals(v2)) {
            return true;
        }
        if (v1 instanceof Date && v2 instanceof Date) {
            return ((Date) v1).getTime() == ((Date) v2).getTime();
        }
        return false;
    }

    public static Integer toInteger(Object value) {
        return toInteger(value, null);
    }

    public static Integer toInteger(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof BigDecimal) {
            return toInteger((BigDecimal) value);
        }
        if (value instanceof Long) {
            return toInteger((Long) value);
        }
        if (value instanceof Double) {
            return toInteger((Double) value);
        }
        if (value instanceof String) {
            try {
                return Integer.valueOf((String) value);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static Integer toInteger(BigDecimal value) {
        if (value == null) return null;
        return value.intValue();
    }

    public static Integer toInteger(Long value) {
        if (value == null) return null;
        return value.intValue();
    }

    public static Integer toInteger(Double value) {
        if (value == null) return null;
        return value.intValue();
    }

    public static <T> T nvl(T value, T nullValue) {
        return value == null ? nullValue : value;
    }

    public static String serializeToXData(Serializable obj) {
        if (obj == null) {
            return null;
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream zos = new GZIPOutputStream(bos);
            ObjectOutputStream oos = new ObjectOutputStream(zos);

            oos.writeObject(obj);
            oos.flush();
            zos.finish();
            zos.flush();

            String result = Base64.encodeBase64String(bos.toByteArray());

            oos.close();
            zos.close();
            bos.close();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Serializable deserializeXData(String str) {
        try {
            byte[] buf = Base64.decodeBase64(str);
            ByteArrayInputStream bis = new ByteArrayInputStream(buf);
            GZIPInputStream zis = new GZIPInputStream(bis);
            ObjectInputStream ois = new ObjectInputStream(zis);
            Object result = ois.readObject();
            ois.close();
            zis.close();
            bis.close();
            return (Serializable) result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String translate(Object value, List<IdText> idTexts) {
        String sValue = value == null ? "" : value.toString();
        for (IdText idText : idTexts) {
            if (idText.getId().toString().equals(sValue)) {
                return idText.getText().toString();
            }
        }
        return "";
    }

    public static Double toDouble(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    public static Double toDouble(Object value, Double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        if (value instanceof Integer) {
            return (double) (int) (Integer) value;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue();
        }
        if (value instanceof Float) {
            return (double) (float) (Float) value;
        }
        return defaultValue;
    }

    public static Date toDate(Object value, Date defaultValue) {
        if (value == null) {
            return defaultValue;
        }
//		if (value instanceof Double) {
//			//xxx
//			return HSSFDateUtil.getJavaDate((Double) value);
//		}
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof String) {
            try {
                return DateFuncs.convert((String) value);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static Long toLong(Object value) {
        return toLong(value, null);
    }

    public static Long toLong(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return (long) (Integer) value;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).longValue();
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Double) {
            return (long) (double) ((Double) value);
        }
        if (value instanceof String) {
            try {
                return Long.valueOf((String) value);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static String toString(Object value) {
        return value == null? null: value.toString();
    }

    public static Boolean toBoolean(Object value) {
        if(value == null) {
            return null;
        }
        String sValue = value.toString();
        if(value instanceof Number) {
            return !"0".equals(sValue);
        }
        return "TRUE".equalsIgnoreCase(sValue);
    }

    public static<T> T to(String sValue, Class<T> type) {
        if(type == String.class) {
            return (T) sValue;
        }
        if(type == Integer.class) {
            return (T) Integer.valueOf(sValue);
        }
        if(type == Long.class) {
            return (T) Long.valueOf(sValue);
        }
        if(type == Double.class) {
            return (T) Double.valueOf(sValue);
        }
        if(type == Date.class) {
            return (T) DateFuncs.parse(sValue);
        }
        throw new RuntimeException("unsupported type: " + type.getName());
    }

    public static<T> T to(Object o, Class<T> type) {
        if(type == Long.class) {
            return (T)toLong(o);
        }
        if(type == Integer.class) {
            return (T)toInteger(o);
        }
        if(type == Double.class) {
            return (T)toDouble(o, null);
        }
        if(type == String.class) {
            return (T)toString(o);
        }
        throw new RuntimeException("unsupported type: " + type.getName());
    }

    public static Object[] ensureArrayLength(Object[] arr, int length) {
        if(arr.length >= length) {
            return arr;
        }
        return Arrays.copyOf(arr, length);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.valueOf(s);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            double d = Double.valueOf(s);
            if(Double.isNaN(d) || Double.isInfinite(d)) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Double round(Double value, int places) {
        if(value == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
