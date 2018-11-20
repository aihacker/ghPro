package com.gpdi.mdata.web.reportform.daoexcel.pdfrecognition.action;


import com.gpdi.mdata.sys.entity.report.PdfTotalBean;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.pdfscream.PdfRecognitionService;
import com.gpdi.mdata.web.utils.BigDecimalUtil;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;


import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:pdf识别文件
 * @author: zzy
 * @data: Created in 2018/6/22 9:26
 * @modifier:
 */

@Controller
public class QueryAction {
    protected static final Logger log = Logger.getLogger(QueryAction.class);
    //重入锁:就是一个线程在获取了锁之后，再次去获取了同一个锁，这时候仅仅是把状态值进行累加。不需要排队
    private ReentrantLock lock = new ReentrantLock();
    @Autowired
    private ExtractTextFromXLSService extractTextFromXLSService;

   /*@Autowired
    private PurchaseBillParentService purchaseParentService;*/
    @Autowired
    private PdfRecognitionService prs;

    long id =System.currentTimeMillis();

    @RequestMapping
    public void execute() {
    }
    @RequestMapping
    public ActionResult daoExcel(MultipartFile file, HttpServletRequest request) {
       // File path = new File("e:\\"  + file.getOriginalFilename());
        String filePath = request.getSession().getServletContext().getRealPath("temp/");
        File path = new File(filePath);

        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文件路径为:"+path);
        //======================================识别pdf文件(头)=========================================================
        try {
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream(path));
            PDDocument pdfDocument = PDDocument.load(inputStream);
            StringWriter writer = new StringWriter();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.writeText(pdfDocument, writer);
            String contents = writer.getBuffer().toString();

            PDDocumentCatalog cata = pdfDocument.getDocumentCatalog();
            String content = "";
            List pages = cata.getAllPages();
            int count = 1;
            for (int i = 0; i < pages.size(); i++) {

                PDPage page = (PDPage) pages.get(i);
                if (null != page) {
                    StringWriter sw = new StringWriter();
                    PDFTextStripper pst = new PDFTextStripper();
                    pst.setStartPage(i + 1);
                    pst.setEndPage(i + 1);
                    pst.writeText(pdfDocument, sw);
                    content += sw.getBuffer().toString();
                }
            }
           // System.out.println(content);
            //System.err.println(content);
            //=====================================识别出来后数据处理(头)============================================
            List<String> arr = new ArrayList<>();
            String regex = "ETC卡号：(.*) 开票金额";//获取到ETC卡号

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                System.out.println("ETC卡号: "+matcher.group(1));//获取到ETC卡号
                arr.add(matcher.group(1));
            }
            String regex2 = "车牌号：(.*) 开票申请时间";//获取车牌号
            Pattern pattern2 = Pattern.compile(regex2);
            Matcher matcher2 = pattern2.matcher(content);
            while (matcher2.find()) {
                System.out.println("车牌号: "+matcher2.group(1));//获取车牌号
                arr.add(matcher2.group(1));
            }
            List totalList = dataHandle(content,arr.get(0),arr.get(1));
           /* List uI = new ArrayList();
            uI.add("发票代码");
            uI.add("发票号码");
            uI.add("发票金额");
            uI.add("通行城市");
            uI.add("序号");
            uI.add("交易时间");
            uI.add("入口");
            uI.add("出口");
            uI.add("拆分金额");
            uI.add("交易金额");
            uI.add("ETC卡号");
            uI.add("车牌号");*/

            //===============================================================
           List<PdfTotalBean> temp = new LinkedList<>();
            for (int b = 0; b < totalList.size(); b++) {
                List<PdfTotalBean> list3= (List<PdfTotalBean>) totalList.get(b);
                   // List<PdfTotalBean> temp = new LinkedList<>();
                    PdfTotalBean pdftotalbean = new PdfTotalBean();
                    pdftotalbean.setInvoiceCode(String.valueOf(list3.get(0)));
                    pdftotalbean.setInvoiceNumber(String.valueOf(list3.get(1)));
                    pdftotalbean.setInvoiceSum(Double.valueOf(String.valueOf(list3.get(2))));
                    pdftotalbean.setThroughCity(String.valueOf(list3.get(3)));
                    pdftotalbean.setSerialNumber(Integer.valueOf(String.valueOf(list3.get(4))));
                    pdftotalbean.setDealTime(String.valueOf(list3.get(5)));
                    pdftotalbean.setEntrance(String.valueOf(list3.get(6)));
                    pdftotalbean.setExit(String.valueOf(list3.get(7)));
                    pdftotalbean.setSplitSum(Double.valueOf(String.valueOf(list3.get(8))));
                    pdftotalbean.setDealSum(String.valueOf(list3.get(9)));
                    pdftotalbean.setEtcCardNumber(String.valueOf(list3.get(10)));
                    pdftotalbean.setLicencePlateNumber(String.valueOf(list3.get(11)));
                    temp.add(pdftotalbean);


            }
            //PdfRecognitionService prs = new PdfRecognitionService();
            prs.savePdfData(temp);
          // extractTextFromXLSService.savePdfData(temp);//保存识别pdf的数据
            //==============================================================================================



           /* PdfColumnToMatching parser = null;
            List<PdfColumnTitle> list9 = new ArrayList<>();
            for (PdfColumnTitle column : PdfColumnTitle.values()) {
                column.setColumn(uI.indexOf(column.getName()));
                list9.add(column);
            }
            parser = new PdfColumnToMatching(list9);
            totalList.add(0, parser);*/

            //PdfTotalBean purchase = parser.parse(totalList);
            //List<PdfTotalBean> results = new LinkedList<>();
           // results.add(purchase);

           //extractTextFromXLSService.savePdfData(totalList);//保存识别pdf的数据

            //=====================================识别出来后数据处理(尾)============================================
        } catch (Exception e) {
            e.printStackTrace();
        }
        //======================================识别pdf文件(尾)=========================================================
        return ActionResult.ok("PDF发票识别成功");
    }

    public static List dataHandle(String content,String etc,String car){
        String[] strings = content.split("\r\n");
        List<String> titleList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        for(int i = 0;i<strings.length;i++){
            if(strings[i].split(" ").length == 3){
                if(isTitle(strings[i].split(" "))){
                    titleList.add(strings[i]);
                }
            }
            if(strings[i].split(" ").length == 8){
                stringList.add(strings[i]);

            }

            if(strings[i].split(" ").length == 11){
                int a1 = strings[i].indexOf(" ");
                String bString = strings[i].substring(a1+1);
                int a2 = bString.indexOf(" ");
                String cString = bString.substring(a2+1);
                int a3 = cString.indexOf(" ");
                String dString = cString.substring(a3+1);
                String eString = strings[i].substring(0, a3+a2+a1+2);
                stringList.add(dString);
                titleList.add(eString);
            }
        }
        Integer titleIndex = 0;
        Double sum = 0.0;

        List<List<String>> listMainLa = new ArrayList<>();//====================用于将40条数据(里面13条数据)放入一个总集合
        for(int i = 0;i<stringList.size();i++){
            if(sum == 0){
                String tempSum = titleList.get(titleIndex).split(" ")[2];//获取3位数据的发票金额
                sum = Double.parseDouble(tempSum);
                titleIndex++;
            }
            String tempPrice = stringList.get(i).split(" ")[6];//获取8位数据的拆分金额
            Double tempP = Double.parseDouble(tempPrice);
            sum = BigDecimalUtil.sub(sum, tempP);//发票金额减去拆分金额,如果等于0,则接下去的数据对应的就是i++的
            //System.out.println(titleList.get(titleIndex-1)+" "+stringList.get(i)+" "+etc+" "+car);
            //===============================用于将40条数据(里面13条数据)放入一个总集合=====================================================
            List<String> listMain = new ArrayList<>();//====================
            String[] titleArray = titleList.get(titleIndex-1).split(" ");
            for (int z = 0; z < titleArray.length; z++) {
                listMain.add(titleArray[z]);// listMain.add(titleArray[z]);
            }
            String[] stringArray = stringList.get(i).split(" ");
            for (int x = 0; x < stringArray.length; x++) {
                if (x == 2) {
                    String timeJoint = stringArray[x]+" "+stringArray[x+1];//将两个时间拼接成一个字符串
                    listMain.add(timeJoint);
                    x++;
                    continue;
                }
                listMain.add(stringArray[x]);
            }
            listMain.add(etc);
            listMain.add(car);
            listMainLa.add(listMain);
            //============================用于将40条数据(里面13条数据)放入一个总集合=========================================================
        }

        System.out.println(listMainLa);
        int yui=1;
        return  listMainLa;
    }

    public static boolean isTitle(String[] title){
        Boolean isTitle = true;
        for(int i = 0;i<title.length;i++){
            Integer check1 = title[i].indexOf("：");
            if(check1 != -1){//-1则没找到有:的数据
                isTitle = false;
            }
        }
        return isTitle;
    }
}