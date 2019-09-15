package wxgh.app.sys.excel.four;


import com.libs.common.excel.ExcelReadApi;
import com.libs.common.excel.ExcelReadSheet;
import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import wxgh.data.four.excel.FourDetailExcelData;
import wxgh.data.four.excel.FourProjectExcelData;
import wxgh.sys.service.weixin.four.excel.FourExcelService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-13  09:18
 * --------------------------------------------------------- *
 */
@Component
@Scope("prototype")
public class FourReadApi {

    public int excelCount = 0;
    public int projectCount = 0;
    public List<FourDetailExcelData> listFourDetail = new ArrayList<>();
    public List<FourProjectExcelData> listFourProject = new ArrayList<>();
    public List<ReadResult> detailResultList = new ArrayList<>();

    // 获取上级部门名称，(分公司)
    private String deptName;
    private Integer deptid;

    @Autowired
    private FourExcelService fourExcelService;

    public FourReadApi(){
        System.out.println("FourReadApi:" + this);
    }

//    public static void main(String[] args) {
//        String path = "C:\\Users\\Ape\\Desktop\\工会excel\\第一分公司-联通项目部.xlsx";
//        String path2 = "C:\\Users\\Ape\\Desktop\\工会excel\\第一分公司-广州联通项目部.xlsx";
//        FourReadApi api = new FourReadApi();
//        api._import(path2);
//    }

//    public void _import(String path){
//        final String[] one = {""};
//
//        ExcelReadApi.start()
//                .setStartColumn(2)
//                .setPath(path)
//                .readOneLine(2, new ExcelReadRow() {
//                    @Override
//                    public void row(List list, Integer row) {
//                        System.out.println("读取第一行");
//                        System.out.println(JsonUtils.stringfy(list));
//                        one[0] = list.get(0).toString();
//                    }
//                });
//
//        List<ReadResult> list = null;
//        // 判断是台账还是四小项目
//        if(one[0].trim().contains("分公司")){
//            list = detail(path);
//        }else{
//            list = project(path);
//        }
//        for (ReadResult read : list){
//            if (!read.getStatus())
//                System.out.println(read.getMessage());
//        }
//    }

    /**
     * 四小设备  台账明细表
     * @param inputStream
     */
    public List<ReadResult> detail(InputStream inputStream, String fileName){

        List<ReadResult> listReads = new ArrayList<>();

        ExcelReadApi
                .start(2)  // 从第2行开始读取
                .setInputStream(inputStream)
                .setFileName(fileName)
                .fliterRow(3) // 过滤第三行
                .set(8, ExcelReadApi.ExcelType.Integer)
                .set(7, ExcelReadApi.ExcelType.Date)
                .set(13, ExcelReadApi.ExcelType.Date)
                .set(14, ExcelReadApi.ExcelType.Integer)
                .set(17, ExcelReadApi.ExcelType.Double)
                .read(new ExcelReadSheet() {
                    @Override
                    public void sheet(List<List> list, Integer sheet) {

                    }

                    @Override
                    public void row(List list, Integer row) {
                        // 读取上级分公司
                        if(row == 2){
                            deptName = (String) list.get(1);
                            System.out.println("分公司deptName:" + deptName);
                            deptid = fourExcelService.getCompany(deptName);
                            System.out.println("分公司deptId:" + deptid);
                            if(deptid == 0){
                                listReads.add(ReadResult.error("分公司不存在：第 " + row + "行"));
                            }
                            return;
                        }
                        FourDetailExcelData fourDetail = new FourDetailExcelData();
                        fourDetail.setParentDeptName(deptName);

                        // 营销中心
                        String marketName = list.get(1).toString().trim();
                        if (com.libs.common.type.TypeUtils.empty(marketName)) {
                            listReads.add(ReadResult.error("营销中心不能为空：第 " + row + "行"));
                        }
                        fourDetail.setDeptName(marketName);
                        fourDetail.setDeptId(deptid);
                        Integer mid = fourExcelService.getMarket(marketName, fourDetail.getParentDeptName());
                        if (null == mid) {
                            listReads.add(ReadResult.error("该分公司的营销中心不存在：第 " + row + "行"));
                        }else {
                            fourDetail.setMid(mid);
                        }

                        String projectName = list.get(2).toString().trim();
                        if (com.libs.common.type.TypeUtils.empty(projectName)) {
                            listReads.add(ReadResult.error("四小项目不能为空：第 " + row + "行"));
                        }
                        fourDetail.setProjectName(projectName);

                        if(list.size() < 17)
                            return;

                        fourDetail.setProjectContent((String) list.get(3));
                        fourDetail.setBrand((String) list.get(4));
                        fourDetail.setModelName(String.valueOf(list.get(5)));
                        fourDetail.setBuyTime((Date) list.get(6));
                        fourDetail.setNumb((Integer) list.get(7));
                        fourDetail.setUnit((String) list.get(8));
                        fourDetail.setCondit((String) list.get(9));
                        fourDetail.setRemark((String) list.get(10));
                        fourDetail.setCondStr((String) list.get(11));
                        fourDetail.setPlanUpdate((Date) list.get(12));
                        fourDetail.setUsefulLife((Integer) list.get(13));
                        fourDetail.setRepairString((String) list.get(14));
                        fourDetail.setPriceSource((String) list.get(15));
                        fourDetail.setPrice((Double) list.get(16));

                        listFourDetail.add(fourDetail);
                        System.out.println(JsonUtils.stringfy(list));
                        // 统计总数
                        excelCount++;
                    }
                });
        detailResultList = listReads;
        return listReads;
    }

    /**
     * 四小项目
     * @param path
     */
    public List<ReadResult> project(String path){
        List<ReadResult> listReads = new ArrayList<>();
        ExcelReadApi
                .start(2)          // 从第二行开始
                .setStartColumn(2) // 从第二列开始读取
                .setPath(path)
                .set(5, ExcelReadApi.ExcelType.Integer)
                .read(new ExcelReadSheet() {

                    @Override
                    public void sheet(List<List> list, Integer sheet) {

                    }

                    @Override
                    public void row(List list, Integer row) {
                        FourProjectExcelData fourProject = new FourProjectExcelData();
                        fourProject.setCompany((String) list.get(0));
                        String projectName = list.get(1).toString().trim();
                        if(com.libs.common.type.TypeUtils.empty(projectName)){
                            listReads.add(ReadResult.error("projectName is null on Line " + row));
                        }
                        fourProject.setProjectName(projectName);

                        String deptName = list.get(2).toString().trim();
                        if(com.libs.common.type.TypeUtils.empty(deptName)){
                            listReads.add(ReadResult.error("deptName is null on Line " + row));
                        }
                        fourProject.setDeptname(deptName);
                        fourProject.setNumb((Integer) list.get(3));
                        fourProject.setScale((String) list.get(4));
                        fourProject.setIntroduction((String) list.get(5));
                        fourProject.setRemark((String) list.get(6));
                        listFourProject.add(fourProject);

                        projectCount++;
                    }
                });
        return listReads;
    }

    public String buildMessage(){
        if(detailResultList.size() == 0)
            return null;
        else{
            List<String> result = new ArrayList<>();
            for (ReadResult read : detailResultList){
                if (!read.getStatus())
                    result.add(read.getMessage() + "<br>");
            }
            return TypeUtils.listToStr(result, " ");
        }
    }

    public List<FourDetailExcelData> getListFourDetail() {
        return listFourDetail;
    }

    public List<FourProjectExcelData> getListFourProject() {
        return listFourProject;
    }

    public int getExcelCount() {
        return excelCount;
    }

    public int getProjectCount() {
        return projectCount;
    }
}
