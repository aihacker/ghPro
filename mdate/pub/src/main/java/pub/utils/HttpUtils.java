package pub.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class HttpUtils {
	
	private HttpUtils(){}
	
	public static void printAndClose(HttpServletResponse resp, String json)throws Exception {
			
		
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter pw;
			try {
				pw = resp.getWriter();
				pw.println(json);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("put json to client error");
			}
			
		}

}
