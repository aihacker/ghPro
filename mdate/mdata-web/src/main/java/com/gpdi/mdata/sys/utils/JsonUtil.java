package com.gpdi.mdata.sys.utils;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-05-12.
 */
public class JsonUtil {

  public  static String toArray(JSONArray jsonArray) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.getString(i));
        }
        String[] stringArray = list.toArray(new String[list.size()]);
        StringBuilder sb=new StringBuilder("[");
        int len=stringArray.length;
        for(int a=0;a<len;a++ ){
            sb.append(stringArray[a]).append(",");
        }
      sb.append("]");
        return sb.toString();
    }
}
