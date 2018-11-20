package com.gpdi.mdata.web.reportform.info.companylegal.action;

import com.gpdi.mdata.sys.service.reportform.info.CompanyInfoService;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @description:公司信息查询
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 16:19
 * @modifier:
 */
@Controller
public class QueryAction {
    @Autowired
    private CompanyInfoService companyInfoService;

    @RequestMapping
    public QueryResult execute(YtcardData ytcardData, PageSettings settings) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int pageSize=settings.getPageSize();
        QueryResult result=companyInfoService.querylegal(ytcardData, settings);
        if(ytcardData.getName()!=null && ytcardData.getName()!="") {
            result=companyInfoService.querylegal(ytcardData, settings);
        }else{
            settings.setPageSize(result.getRowCount());
            result=companyInfoService.querylegal(ytcardData,settings);
            settings.setPageSize(pageSize);
        }
        Field[] fields=CompanylegalTempData.class.getDeclaredFields();
        List<String> fieldList=new ArrayList<String>();
        for (int j=0;j<fields.length;j++){
            String field=fields[j].getName().toString();
            fieldList.add(field);
        }
        List<CompanylegalTempData> list = new ArrayList<CompanylegalTempData>();
        if (ytcardData.getIsExport() != null && ytcardData.getIsExport() == 1) {
            if (result.getRows().size() > 0) {
                for (int i = 0; i < result.getRows().size(); i++) {
                    LinkedCaseInsensitiveMap map = (LinkedCaseInsensitiveMap) result.getRows().get(i);
                    CompanylegalTempData data = new CompanylegalTempData();
                    data.setMap(map);
                    Set<String> set =map.keySet();
                    for (String key : set) {
                        if(key.equals("Id")){
                            key=key.toLowerCase();
                        }
                        String keyIndex=key.substring(0,1).toUpperCase();
                        String keyBack=key.substring(1,key.length());
                        if(fieldList.contains(key) && map.get(key) != null) {
                            data.getClass().getMethod("set" + keyIndex + keyBack, String.class).invoke(data, map.get(key).toString());
                        }
                    }
                    list.add(data);
                }
                CompanylegalWriteApi allcontract = new CompanylegalWriteApi();
                allcontract.toExcel(list);
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                allcontract.response(response);
            }

        }
        return companyInfoService.querylegal(ytcardData, settings);
    }
}
