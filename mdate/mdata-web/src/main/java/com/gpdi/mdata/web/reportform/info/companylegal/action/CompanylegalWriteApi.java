package com.gpdi.mdata.web.reportform.info.companylegal.action;

import com.gpdi.mdata.web.utils.ExcelSupportApi;
import com.gpdi.mdata.web.utils.ExcelType;
import com.gpdi.mdata.web.utils.PercentUtil;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/15 10:07
 * @modifier:
 */
public class CompanylegalWriteApi extends ExcelSupportApi<CompanylegalTempData> {

    private Integer num=0;

    public CompanylegalWriteApi() {
        super( "供应商信息查询", ExcelType.XLS);
    }

    @Override
    public String[][] head() {
        return new String[][]{
                {
                        "序号",
                        "公司名称",
                        "公司历史曾用名",
                        "法人代表",
                        "纳税号",
                        "社会信用编码",
                        "股东1",
                        "股东2",
                        "股东3",
                        "股东4",
                        "高管1",
                        "高管2",
                        "高管3",
                        "实际控制人",
                        "是否国企",
                        "更新时间"
                }
        };
    }

    @Override
    public Integer[][] cellRange() {
        return new Integer[][]{
        };
    }

    @Override
    public void createCellItem(CompanylegalTempData data, Row row, int i) {
        if(data == null)
            return;

        Field[] fields=CompanylegalTempData.class.getDeclaredFields();
        List<String> fieldList=new ArrayList<String>();
        for (int j=0;j<fields.length-1;j++){
            String field=fields[j].getName().toString();
            fieldList.add(field);
        }
        LinkedCaseInsensitiveMap map=data.getMap();
        Set<String> set =map.keySet();
        for (String key : set) {
            if(key.equals("Id")){
                key=key.toLowerCase();
            }
            String keyIndex=key.substring(0,1).toUpperCase();
            String keyBack=key.substring(1,key.length());
            if(fieldList.contains(key)) {
                try {
                    if(num < fieldList.size()) {
                        createCell(row,num , data.getClass().getMethod("get" + keyIndex + keyBack, null).invoke(data, null));
                        num++;
                    }
                    if(num==fieldList.size()){
                        num=0;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        /*createCell(row, 0, data.getId());
        createCell(row, 1, data.getCompany_name());
        createCell(row, 2, data.getCompany_historical_name());
        createCell(row, 3, data.getLegal_representative());
        createCell(row, 4, data.getTax_code());
        createCell(row, 5, data.getCredit_code());
        createCell(row, 6, data.getShareholder_one());
        createCell(row, 7, data.getShareholder_two());
        createCell(row, 8, data.getShareholder_three());
        createCell(row, 9, data.getShareholder_four());
        createCell(row, 10, data.getSenior_admin_one());
        createCell(row, 11, data.getSenior_admin_two());
        createCell(row, 12, data.getSenior_admin_three());
        createCell(row, 13, data.getController());
        createCell(row, 14, data.getIs_country());
        createCell(row, 15, data.getInput_time());*/
    }

    @Override
    protected float rowHeight() {
        return 1;
    }

    private String formatRepair(int v){
        if(v == 0) return "无";
        else return "" + v;
    }
}
