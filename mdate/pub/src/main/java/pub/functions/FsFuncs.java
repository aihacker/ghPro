package pub.functions;

import java.io.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2009-1-8
 */
public class FsFuncs {

    public static String getFileName(String fullFileName) {
        int pos = fullFileName.lastIndexOf('\\');
        if (pos == -1) {
            pos = fullFileName.lastIndexOf('/');
        }
        if (pos == -1) {
            return fullFileName;
        }
        return fullFileName.substring(pos + 1);
    }

    public static void delete(File file) {
        assert file.exists();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                delete(child);
            }
        }
        boolean deleted = file.delete();
        assert deleted;
    }

    private static void tryClose(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File fromFile, File toFile) throws Exception {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            FileInputStream fis = new FileInputStream(fromFile);
            in = new BufferedInputStream(fis);
            FileOutputStream fos = new FileOutputStream(toFile);
            out = new BufferedOutputStream(fos);
            byte[] buf = new byte[4096];
            int cb = -1;
            while ((cb = fis.read(buf)) != -1) {
                out.write(buf, 0, cb);
            }
            out.flush();
        }
        finally {
            tryClose(out);
            tryClose(in);
        }
    }

    public static String changeExtension(String path, String ext) {
        int lastDotPos = path.lastIndexOf('.');
        if (ext.charAt(0) == '.') {
            ext = ext.substring(1);
        }
        path = path.substring(0, lastDotPos + 1) + ext;
        return path;
    }

    public static String fixFileNameChars(String fileName) {
        String badChars = "\\/:*?\"<>|";
        String fixChars = "＼／：＊？＂＜＞｜";
        String s1 = "";
        for (int n = 0; n < fileName.length(); n++) {
            char c = fileName.charAt(n);
            int index = badChars.indexOf(c);
            if (index != -1) {
                c = fixChars.charAt(n);
            }
            s1 += c;
        }
        return s1;
    }

    public static String readTextFile(File file, String encoding) {
        Reader reader = null;
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, encoding);
            BufferedReader br = new BufferedReader(isr);
            reader = br;
            String line = null; //not declared within while loop
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (reader != null) {
                IoFuncs.tryClose(reader);
            }
            else if (is != null) {
                IoFuncs.tryClose(is);
            }
        }
        return sb.toString();
    }

    public static void writeTextFile(File file, String content) {
        Writer writer = null;
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            writer = new OutputStreamWriter(os, "utf-8");
            writer.write(content);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (writer != null) {
                IoFuncs.tryClose(writer);
            }
            else if (os != null) {
                IoFuncs.tryClose(os);
            }
        }

    }

    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
