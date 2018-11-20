package com.gpdi.mdata.web.reportform.info.tenderingrules.action;

import com.gpdi.mdata.sys.entity.report.FileRulers;
import com.gpdi.mdata.sys.entity.report.TenderingrulesWay;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.service.reportform.info.CompanyInfoService;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.web.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *招标规则
 */
@Controller
public class OperateAction {

    @Autowired
    private CompanyInfoService infoService;
    @RequestMapping
    public ActionResult save(HttpServletRequest request) {
        //获取规则编号最后记录
        String maxNumber =infoService.queryRulesMaxId();
        Integer number =0;
        if (maxNumber !=null && !maxNumber.equals("")){
            number = Integer.valueOf(maxNumber)+1;
        }

        //==========1.取招标规则文件的参数=============
        String startTime = request.getParameter("startTime");
//        String endTime = request.getParameter("endTime");
        String endTime = "2099-12-30";
        String startFileName = request.getParameter("startFileName");
        String startFileNumber = request.getParameter("startFileNumber");
        String endFileName = request.getParameter("endFileName");
        String endFileNumber = request.getParameter("endFileNumber");
        FileRulers fileRulers = new FileRulers();
        fileRulers.setRuleNumber(number);
        fileRulers.setStartDate(startTime);
        fileRulers.setEndDate(endTime);
        fileRulers.setFileBasisName(startFileName);
        fileRulers.setFileNumber(startFileNumber);
        fileRulers.setFileAbolishName(endFileName);
        fileRulers.setFileAbolishNumber(endFileNumber);

        //=============2.获取采购方式的参数===============
        String[] way = request.getParameterValues("way");
        String[] projectGoodsOne = request.getParameterValues("projectGoodsOne");
        String[] projectGoodsTwo = request.getParameterValues("projectGoodsTwo");
        String[] projectServicesOne = request.getParameterValues("projectServicesOne");
        String[] projectServicesTwo = request.getParameterValues("projectServicesTwo");
        String[] noProjectGoodsOne = request.getParameterValues("noProjectGoodsOne");
        String[] noProjectGoodsTwo = request.getParameterValues("noProjectGoodsTwo");
        String[] noProjectGoodsThree = request.getParameterValues("noProjectGoodsThree");
        String[] integratedServicesOne = request.getParameterValues("integratedServicesOne");
        String[] integratedServicesTwo = request.getParameterValues("integratedServicesTwo");
        String[] integratedServicesThree = request.getParameterValues("integratedServicesThree");
        List<TenderingrulesWay> listWay = new ArrayList<>();
        if (way !=null && way.length>0) {
            for (int i = 0; i < way.length; i++) {
                TenderingrulesWay tenderingrulesWay = new TenderingrulesWay();
                tenderingrulesWay.setWay(way[i]);
                tenderingrulesWay.setProjectGoodsOne(projectGoodsOne[i]);
                tenderingrulesWay.setProjectGoodsTwo(projectGoodsTwo[i]);
                tenderingrulesWay.setProjectServicesOne(projectServicesOne[i]);
                tenderingrulesWay.setProjectServicesTwo(projectServicesTwo[i]);
                tenderingrulesWay.setNoProjectGoodsOne(noProjectGoodsOne[i]);
                tenderingrulesWay.setProjectServicesTwo(noProjectGoodsTwo[i]);
                tenderingrulesWay.setNoProjectGoodsThree(noProjectGoodsThree[i]);
                tenderingrulesWay.setIntegratedServicesOne(integratedServicesOne[i]);
                tenderingrulesWay.setIntegratedServicesTwo(integratedServicesTwo[i]);
                tenderingrulesWay.setIntegratedServicesThree(integratedServicesThree[i]);
                tenderingrulesWay.setParent(number);
                listWay.add(tenderingrulesWay);
            }

        }
        //=======3.获取法定中的金额限定==================
        Map<String,Integer> map = new HashMap<>();
        Integer projectMoney = null;//工程施工类金额
        Integer designMoney =null;//工程设计监理类金额
        Integer purchaseMoney = null;//物资采购类金额
        Integer nopjOneMoney = null;//非工程货物中必须招标的金额
        Integer integerMoney = null;//综合服务中必须招标的金额
        if (noProjectGoodsOne !=null && noProjectGoodsOne.length>0){
           String nopjOne = noProjectGoodsOne[0]+"0000";//获取非工程货物中必须招标的金额
           if (nopjOne !=null && !nopjOne.equals("")){
               nopjOneMoney =  StringUtils.getNumber(nopjOne);
           }
       }
        if (integratedServicesOne !=null && integratedServicesOne.length>0){
            String inServiceOne = integratedServicesOne[0]+"0000";//获取综合服务中必须招标的金额
            if (inServiceOne !=null && !inServiceOne.equals("")){
                integerMoney =  StringUtils.getNumber(inServiceOne);
            }
        }
        String oneMoney = request.getParameter("oneMoney");
        String twoMoney = request.getParameter("twoMoney");
        String threeMoney = request.getParameter("threeMoney");
        if (oneMoney !=null && !oneMoney.equals("")){
            oneMoney = oneMoney.trim()+"0000";
            projectMoney = Integer.valueOf(oneMoney);
        }
        if (twoMoney !=null && !twoMoney.equals("")){
            twoMoney = twoMoney.trim()+"0000";
            designMoney = Integer.valueOf(twoMoney);
        }
        if (threeMoney !=null && !threeMoney.equals("")){
            threeMoney = threeMoney.trim()+"0000";
            purchaseMoney = Integer.valueOf(threeMoney);
        }
        map.put("projectMoney",projectMoney);
        map.put("designMoney",designMoney);
        map.put("purchaseMoney",purchaseMoney);
        map.put("nopjOneMoney",nopjOneMoney);
        map.put("integerMoney",integerMoney);

        //保存招标规则采购方式
        boolean flagFile = infoService.saveFileRulers(fileRulers,map,listWay,number);
        if (flagFile ==false){
            return ActionResult.ok("保存失败!");
        }
        return ActionResult.ok("保存成功!");
    }
}
