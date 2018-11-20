package com.gpdi.mdata.web.reportform.test;


        import java.util.ArrayList;

        import java.util.Arrays;

        import java.util.Collection;

        import java.util.HashSet;

        import java.util.List;

        import java.util.Set;



/**

 *

 * @ClassName:求数组的交集

 * @Description: A={6,3,9,3,2,4,5,7},B={5,8,6,2,1,9}，则输出3，4，7，1，8 思路：全集除掉交集，就是结果

 * @author

 * @date 2016年5月27日 上午9:56:25

 *

 */

public class TestDemo2 {

    public static void main(String[] args) {

// 注意：一定要使用创建对象的格式创建数组

       /* Integer[] a = new Integer[]{6, 3, 9, 3, 2, 4, 5, 7};

        Integer[] b = new Integer[]{5, 8, 6, 2, 1, 9};
*/
        String[] a = new String[]{"娃哈哈","脉动","怡宝"};
        String[] b = new String[]{"农夫山泉","雪碧","怡宝"};

        List _a = Arrays.asList(a);

        List _b = Arrays.asList(b);

// 创建集合

       // Collection realA = new ArrayList<Integer>(_a);
        Collection realA = new ArrayList<String>(_a);

       // Collection realB = new ArrayList<Integer>(_b);
        Collection realB = new ArrayList<String>(_b);

// 求交集

        realA.retainAll(realB);

        System.out.println("交集结果：" + realA);

        Set result = new HashSet();

// 求全集

        result.addAll(_a);

        result.addAll(_b);

        System.out.println("全集结果：" + result);

// 求差集：结果

        Collection aa = new ArrayList(realA);

        Collection bb = new ArrayList(result);

        bb.removeAll(aa);

        System.out.println("最终结果：" + bb);
    }
}