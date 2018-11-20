package pub.functions;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-3-19
 */
public class HtmlFuncs {

	public static String textToHtml(Object oText) {
		if (oText == null) return "";
		String text = oText.toString();
		text = text.replace("\r", "").replace("\t", "    ").replace(" ", "&nbsp;").replace(">", "&gt;");
		return "<p>" + text.replace("\n", "</p>\n<p>") + "</p>";
	}

	public static String textToHtml(String text) {
		if (text == null) return "";
		text = text.replace("\r\n", "<p />");
		return text;
	}

}
