package com.gpdi.mdata.sys.service.reportform.daoexcel.pdfscream;

import com.gpdi.mdata.sys.entity.report.PdfTotalBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.GeneralDao2;

import java.util.List;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/8/9 21:44
 * @modifier:
 */
@Service
@Transactional(readOnly = false)
public class PdfRecognitionService {
    @Autowired
    private GeneralDao generalDao;

    @Transactional
    public void savePdfData(List<PdfTotalBean> temp) {
        //System.out.println(temp);
        for (int a = 0; a < temp.size(); a++) {
            String invoiceCode = temp.get(a).getInvoiceCode();
            String invoiceNumber = temp.get(a).getInvoiceNumber();
            Double invoiceSum = temp.get(a).getInvoiceSum();
            String throughCity = temp.get(a).getThroughCity();
            Integer serialNumber = temp.get(a).getSerialNumber();
            String dealTime = temp.get(a).getDealTime();
            String entrance = temp.get(a).getEntrance();
            String exit = temp.get(a).getExit();
            Double splitSum = temp.get(a).getSplitSum();
            String dealSum = temp.get(a).getDealSum();
            String etcCardNumber = temp.get(a).getEtcCardNumber();
            String licencePlateNumber = temp.get(a).getLicencePlateNumber();
            String sql = "insert into t_pdf_through_detail(invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number) values ('"+invoiceCode+"','"+invoiceNumber+"','"+invoiceSum+"','"+throughCity+"','"+serialNumber+"','"+dealTime+"','"+entrance+"','"+exit+"','"+splitSum+"','"+dealSum+"','"+etcCardNumber+"','"+licencePlateNumber+"')";
            //System.out.println("+++++"+generalDao);
            int ss = generalDao.execute(sql);
            System.out.println(ss);

        }

    }
}
