package com.gpdi.mdata.web.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:分词工具
 * @author: WangXiaoGang
 * @data: Created in 2018/9/17 20:48
 * @modifier:
 */
public class JiebaUtil {

    public static String getSomeDiff(String param){
        String[] strs = param.split(",");
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> all = new ArrayList<>();
        List<String> allSame = new ArrayList<>();
        if(strs.length>1) {
            List<String> str0 = segmenter.sentenceProcess(strs[0]);
            List<String> str1 = segmenter.sentenceProcess(strs[1]);
            for (int j = 0; j < str1.size(); j++) {
                if (str0.contains(str1.get(j))) {
                    allSame.add(str1.get(j));
                }
            }
            for (int i = 0; i < strs.length; i++){
                List<String> result = segmenter.sentenceProcess(strs[i]);
                for (int j = 0; j < result.size(); j++) {
                    if (!allSame.contains(result.get(j))) {
                        result.set(j, "<b style='background-color:yellow'>"+result.get(j)+"</b>");
                    }
                }
                all.add(listToStr(result, false));
            }
            return listToStr(all, true);
        } else {
            return strs.toString();
        }
    }

    public static String listToStr(List<String> list,boolean useDouhao){
        StringBuilder sb = new StringBuilder();
        for (String str:list){
            sb.append(str);
            if(useDouhao){
                sb.append("<br/>");
            }
        }
        return useDouhao?sb.substring(0,sb.length() - 5):sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(getSomeDiff("办公室（安全保卫部）.后勤采购信息大厦16楼客户服务部办公家具配件物资00246,办公室（安全保卫部）.后勤采购信息大厦16楼客户服务部办公家具配件物资00262"));
    }
}
