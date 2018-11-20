package com.gpdi.mdata.web.reportform.test;
import java.util.ArrayList;

import java.util.Collection;

import java.util.List;
/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/10/17 18:22
 * @modifier:
 */
public class TestDemo1 {


    public static void main(String args[]){

        //集合一

        List _first=new ArrayList();

        _first.add("jim");

        _first.add("tom");

        _first.add("jack");

        //集合二

        List _second=new ArrayList();

        _second.add("jack");

        _second.add("happy");

        _second.add("sun");

        _second.add("good");

        Collection exists=new ArrayList(_second);

        Collection notexists=new ArrayList(_second);

        exists.removeAll(_first);

        System.out.println("_second中不存在于_set中的："+exists);

        notexists.removeAll(exists);

        System.out.println("_second中存在于_set中的："+notexists);

    }

}
