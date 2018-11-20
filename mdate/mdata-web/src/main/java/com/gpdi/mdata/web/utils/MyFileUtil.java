package com.gpdi.mdata.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class MyFileUtil {


	public static String getDirectory(String fn){
		String s = fn;
		File f = new File(fn);		
		if(f.exists())
			try {
				s = f.getAbsolutePath().substring(0, 
					StringUtils.lastIndexOf(f.getAbsolutePath(), File.separator));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return s;
	}
	
	public static String forceCreateDir(String dir) throws IOException {
		File destDir = new File(dir);

		if (dir.indexOf(".") != -1) {
			if (dir.indexOf("/") != -1)
				dir = dir.substring(0, StringUtils.lastIndexOf(dir, "/"));
			File fDir = new File(dir);
			if (!fDir.exists())
				fDir.mkdirs();
			return fDir.getCanonicalPath();
		} else {
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			return destDir.getCanonicalPath();
		}
	}

	public static String forceDestZipPath(File srcFile, String dest)
			throws IOException {
		if (StringUtils.isEmpty(dest)) {
			if (srcFile.isDirectory()) {
				dest = srcFile.getCanonicalPath() + "/" + srcFile.getName()
						+ ".zip";
			} else {
				String fileName = srcFile.getName().substring(0,
						srcFile.getName().lastIndexOf("."));
				dest = srcFile.getCanonicalPath() + "/" + fileName + ".zip";
			}
		} else {
			forceCreateDir(dest);
			File fDest = new File(dest);
			if (fDest.isDirectory()) {
				String fileName = "";
				if (srcFile.isDirectory()) {
					fileName = srcFile.getName();
				} else {
					fileName = srcFile.getName().substring(0,
							srcFile.getName().lastIndexOf("."));
				}
				dest += "/" + fileName + ".zip";
			}

		}
		return dest;
	}

	public static String getFileExt(String filename) {
		String sExt = "";
		if (filename.indexOf(".") != -1)
			sExt = StringUtils.substring(filename,
					StringUtils.lastIndexOf(filename, ".") + 1);
		return sExt;
	}

	public static String trimExt(String filename) {
		if (filename.indexOf(".") != -1)
			return StringUtils.substring(filename, 0,
					StringUtils.lastIndexOf(filename, "."));
		else
			return filename;
	}

	public static String getNowTimeStr() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String stm = df.format(date);
		return stm;
	}

	// 源文件为UTF-8的格式
	public static boolean AppendHeadRowToFile(String filename, String head)
			throws Exception {
		boolean ret = false;

		File f = new File(filename);
		if (!f.exists() || f.isDirectory())
			throw new Exception("文件不存在或是个目录");

		String newfn = trimExt(filename) + "_" + getNowTimeStr() + "."
				+ getFileExt(f.getName());

		InputStream in = new FileInputStream(new File(filename));
		OutputStream out = new FileOutputStream(new File(newfn));
		try {
			// add head
			// if(MyFileUtil.getFileExt(f.getName()).equals("csv")){
			// out.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
			// }

			String sHead = head + "\n"; // "\r\n";
			byte[] b = sHead.getBytes("UTF-8");
			out.write(b, 0, b.length);

			// file
			byte[] buf = new byte[32768];// 8192
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
			ret = true;
		} finally {
			in.close();
			out.close();
		}
		if (ret) {
			File fold = new File(filename);
			fold.delete();			
			File fnew = new File(newfn);
			fnew.renameTo(fold);
		}

		return ret;
	}

	public static void deleteFiles(List<String> fileList) {
		for (String fn : fileList) {
			File f = new File(fn);
			if (f.exists())
				f.delete();
		}
	}

	public static String getFileEncoding(String filename) throws Exception {
		File f = new File(filename);
		if (!f.exists())
			throw new Exception("file not exists:" + filename);

		String sCmd = "/usr/bin/file " + filename;
		String sRet = "";

		Process proc = Runtime.getRuntime().exec(sCmd);

		InputStream istrErr = proc.getErrorStream();
		InputStream istrIn = proc.getInputStream();

		InputStreamReader srErr = new InputStreamReader(istrErr);
		BufferedReader bfrErr = new BufferedReader(srErr);

		InputStreamReader srIn = new InputStreamReader(istrIn);
		BufferedReader bfrIn = new BufferedReader(srIn);

		StringBuffer sbErr = new StringBuffer();
		String lineErr = "";
		while (lineErr != null && (lineErr = bfrErr.readLine()) != null) {
			sbErr.append(lineErr).append("\n");
		}
		StringBuffer sbIn = new StringBuffer();
		String lineIn = "";
		while (lineIn != null && (lineIn = bfrIn.readLine()) != null) {
			sbIn.append(lineIn).append("\n");
		}
		proc.waitFor();

		sRet = sbErr.toString() + sbIn.toString();
		int n = sRet.indexOf(":");
		if (n != -1)
			sRet = sRet.substring(n + 1, sRet.length());
		sRet = sRet.trim();
		if (sRet.endsWith("text"))
			sRet = sRet.substring(0, sRet.length() - 4).trim();

		return sRet.toUpperCase();
	}

	public static void deleteFileForce(String filename) throws Exception {
		File f = new File(filename);
		if (f.exists()) {
			String cmd = "rm -rf " + filename;
			Process proc = Runtime.getRuntime().exec(cmd);
			proc.waitFor();
		}
	}

	// 获取目录下所有文件
	public static List<String> getFileList(String filename) {
		List<String> list = new ArrayList<String>();
		File f = new File(filename);
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			for (int i = 0; i < fl.length; i++) {
				if (fl[i].isFile())
					list.add(fl[i].getAbsolutePath());
			}
		}

		return list;
	}

	public static long getFileSize(String filename) throws Exception {
		File f = new File(filename);
		if (!f.exists()){			
			throw new Exception("file not exists:" + filename);
		}
		return f.length();
	}

	public static long getFileLineCount(String filename) throws Exception {
		long ln = 0L;

		File f = new File(filename);
		if (!f.exists())
			throw new Exception("file not exists:" + filename);
		String scmd = "/usr/bin/wc -l " + filename;

		Process proc = Runtime.getRuntime().exec(scmd);

		InputStream istrErr = proc.getErrorStream();
		InputStream istrIn = proc.getInputStream();

		InputStreamReader srErr = new InputStreamReader(istrErr);
		BufferedReader bfrErr = new BufferedReader(srErr);

		InputStreamReader srIn = new InputStreamReader(istrIn);
		BufferedReader bfrIn = new BufferedReader(srIn);

		StringBuffer sbErr = new StringBuffer();
		String lineErr = "";
		while (lineErr != null && (lineErr = bfrErr.readLine()) != null) {
			sbErr.append(lineErr).append("\n");
		}
		StringBuffer sbIn = new StringBuffer();
		String lineIn = "";
		while (lineIn != null && (lineIn = bfrIn.readLine()) != null) {
			sbIn.append(lineIn).append("\n");
		}
		proc.waitFor();

		String sRet = (sbErr.toString() + sbIn.toString()).trim();
		// 65209735 abc.txt
		int n = sRet.indexOf(" ");
		if (n != -1) {
			sRet = sRet.substring(0, n).trim();
			ln = Long.valueOf(sRet);
		}
		return ln;
	}

	public static boolean convFileEncodingSimple(String filename,
			String fromEncoding, String toEncoding) throws Exception {
		boolean bRet = false;
		File f = new File(filename);
		if (!f.exists()) {
			throw new Exception("file not exists:" + filename);
		}
		String ext = getFileExt(filename);
		String tmpFn = trimExt(filename) + "_temp." + ext;
		StringBuilder sb = new StringBuilder();
		sb.append("/usr/bin/iconv -f ").append(fromEncoding).append(" -t ")
				.append(toEncoding);
		sb.append(" -s ").append(filename).append(" -o ").append(tmpFn);

		
		Process proc = Runtime.getRuntime().exec(sb.toString());
		proc.waitFor();

		File fTemp = new File(tmpFn);
		if (fTemp.exists()) {
			f.delete();
			fTemp.renameTo(new File(filename));
			bRet = true;
		}
		return bRet;
	}

	public static boolean convFileEncoding(String filename, String fromEncoding,
			String toEncoding) throws Exception {
		boolean bRet = false;
		filename = filename.replaceAll("//", "/");
		File f = new File(filename);
		if (!f.exists()) {
			throw new Exception("file not exists:" + filename);			
		}

		long fs = getFileSize(filename);
		int fcnt = (int)Math.ceil(fs*1.0D/(2147483648L*1.0D)); //文件数,每个文件限制为2G大小,超出就拆分,转换再合并
		
		if (fcnt >= 2) {//拆分成小文件
			long fl = getFileLineCount(filename);//行数
			int pl = (int)Math.ceil(fl*1.0D/(fcnt*1.0D)); //行数/文件个数,每个文件pl行			
			String dir = trimExt(filename);
			dir = dir + "_subfile/";
			deleteFileForce(dir);
			forceCreateDir(dir);
			String cmd = "/usr/bin/split -l " + pl + " " + filename + " " + dir	+ "sf";
			Process proc = Runtime.getRuntime().exec(cmd);
			proc.waitFor();

			List<String> flst = getFileList(dir);
			for (String s : flst) {
				convFileEncodingSimple(s, fromEncoding, toEncoding);
			}
			
			f.delete();
			cmd = "/usr/bin/cat " + dir + "sf* > " + filename;
			File fa = new File(dir + "run.sh");
			if (fa.exists())
				fa.delete();
			OutputStream out = new FileOutputStream(new File(dir + "run.sh"));

			byte[] b = cmd.getBytes("UTF-8");
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			proc = Runtime.getRuntime().exec(
					"/usr/bin/chmod +x " + dir + "run.sh");
			proc.waitFor();

			proc = Runtime.getRuntime().exec(dir + "run.sh");
			proc.waitFor();

			//deleteFileForce(dir);
			bRet = true;

		} else {
			bRet = convFileEncodingSimple(filename, fromEncoding, toEncoding);
		}

		return bRet;
	}
}
