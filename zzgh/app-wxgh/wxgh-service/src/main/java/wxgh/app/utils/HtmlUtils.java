package wxgh.app.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/12/29
 */
public class HtmlUtils {

    public static String getNoHtml(String html) {
        if (html == null || html.length() <= 0) {
            return "";
        }
        try {
            Document document;
            if (html.startsWith("http")) {
                document = Jsoup.connect(html).get();
            } else {
                document = Jsoup.parse(html);
            }
            return document.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getWeixinImages(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        List<String> images = new ArrayList<>();

        Elements elements = document.select("img[src],img[data-src]");

        for (Element e : elements) {
            String dataSrc = e.attr("src");
            if (dataSrc == null || dataSrc.length() <= 0) {
                dataSrc = e.attr("data-src");
            }
            if (dataSrc != null && dataSrc.length() > 0) {
                images.add(dataSrc);
            }
        }

        String imgSrc = "";
        if (images.size() >= 8) {
            imgSrc = images.get(images.size() / 2);
        } else if (images.size() >= 5) {
            imgSrc = images.get(images.size() - 3);
        } else if (images.size() >= 4) {
            imgSrc = images.get(images.size() - 2);
        } else if (images.size() >= 2) {
            imgSrc = images.get(images.size() - 1);
        } else if (images.size() >= 1) {
            imgSrc = images.get(0);
        }

        return imgSrc;
    }

}
