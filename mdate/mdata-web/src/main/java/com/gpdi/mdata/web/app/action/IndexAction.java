package com.gpdi.mdata.web.app.action;


import com.gpdi.mdata.sys.dao.system.SysModuleDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.dao.GeneralDao;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version 2015/7/15
 */
@Controller
public class IndexAction {

    @RequestMapping
    public void execute(Model model) {
        System.out.println("APP index ： 进入页面");
    }

    @RequestMapping
    public ActionResult test() {
        return ActionResult.ok("WTF");
    }

    @Autowired
    SysModuleDao sysModuleDao;
    @Autowired
    GeneralDao generalDao;


    static Logger logger = LogManager.getLogger(IndexAction.class.getName());

}
