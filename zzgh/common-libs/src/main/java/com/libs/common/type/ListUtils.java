package com.libs.common.type;


import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_ List 工具类
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-12  11:20
 * --------------------------------------------------------- *
 */
public class ListUtils {

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @param n
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for(int i = 0; i < n; i++){
            List<T> value = null;
            if(remaider > 0){
                value = source.subList(i * number + offset, (i+1) * number + offset + 1);
                remaider--;
                offset++;
            }else{
                value = source.subList(i * number + offset, (i+1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}
