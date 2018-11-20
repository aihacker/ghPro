package com.gpdi.mdata.web.reportform.test;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/9/4 18:12
 * @modifier:
 */
public class TestDemo4 {

//数组中三个只出现一次的数字[算法]

        public static void main(String args[]) {

            int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 4, 3, 5 };
            String str = "我们高兴高兴上班";
           // char[] re = str.toCharArray();

            find(num);
            //char ui = findFirst(str);
           // System.out.println(ui);

        }


    public static char findFirst(String str){

        if(str == null || str.length() == 0)

            return '#';

        int[] hashtable = new int[256];

        int len = str.length();

        char[] arr = str.toCharArray();

        for(int i = 0; i < len; i++){
                int n = arr[i];
            hashtable[n]++;

        }

        for(int i = 0; i < len;i++){

            if(hashtable[arr[i]] == 1)

                return arr[i];

        }

        return '#';

    }






        public static void find(int[] num) {

            if(num==null||num.length==0){

                return;

            }

            int len = num.length;

            int[] count = new int[256];

            for(int i=0;i<len;i++){

                int n = num[i];

                count[n]++;

            }

            int a= 0;//取前三个

            for(int i = 0 ; i < len;i++){

                int n = num[i];

                if(count[n]==1){

                    a++;

                    System.out.println(num[i]);






                }

            }
            return;
        }



    }


