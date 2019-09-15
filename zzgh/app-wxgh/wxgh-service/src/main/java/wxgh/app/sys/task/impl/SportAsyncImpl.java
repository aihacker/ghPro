package wxgh.app.sys.task.impl;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pub.functions.DateFuncs;
import wxgh.app.sys.task.SportAsync;
import wxgh.app.utils.FindParentId;
import wxgh.data.entertain.sport.save.SportData;
import wxgh.data.entertain.sport.save.UserSport;
import wxgh.data.pub.user.UseridDeptMobile;
import wxgh.entity.entertain.sport.Sport;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.sys.service.weixin.entertain.sport.SportService;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/25.
 */
@Component
public class SportAsyncImpl implements SportAsync {

    @Async
    @Override
    public void saveSport(Map<String, UserSport> map, Date createTime) {
        Map<String, UseridDeptMobile> userMap = sportService.listUser();

        Sport sport;
        List<Sport> sports = new ArrayList<>();
        for (Map.Entry<String, UserSport> entry : map.entrySet()) {
            UserSport userSport = entry.getValue();
            String name = entry.getKey();
            sport = new Sport();
            UseridDeptMobile useridDeptMobile = userMap.getOrDefault(name, null);
            FindParentId findParentId = new FindParentId();
            if (useridDeptMobile != null) {
                sport.setStatus(1);
                Integer id =1;
                if(useridDeptMobile.getUserid()!= null) {
                    User user = userService.getUser(useridDeptMobile.getUserid());
                    String deptId = user.getDepartment();
                    if ("1".equals(user.getDepartment()) && user.getDeptid() == 1) { deptId = "1"; }

                    String[] tmp = findParentId.getSpiltIds(deptId);
                    id= findParentId.find(tmp);
                }else{
                    useridDeptMobile.setUserid("");
                }

                sport.setDeptid(id);
                sport.setUserid(useridDeptMobile.getUserid());
            } else {
                sport.setStatus(0);
                sport.setDeptid(0);
                sport.setUserid("");
            }

            //保存实时步数
            Sport relSport = sport.clone();
            SportdataToWxSport(relSport, userSport.getSport(), createTime, name);
            sports.add(relSport);

            //处理昨日数据
            List<SportData> sportDatas = userSport.getSports();
            if (!TypeUtils.empty(sportDatas)) {
                for (SportData sportData : sportDatas) {
                    Sport oldSport = sport.clone();
                    SportdataToWxSport(oldSport, sportData, null, name);
                    sports.add(oldSport);
                }
            }
        }

        if (!TypeUtils.empty(sports)) {
            sportService.addSport(sports);
            sports.clear();
        }
    }

    private void SportdataToWxSport(Sport wxSport, SportData sportData, Date createTime, String name) {
        Integer dateId;
        if (null == createTime) {
            dateId = sportData.getDateId();
            createTime = DateUtils.getLastTimeOfDate(dateId);
        } else {
            dateId = DateFuncs.toIntDate(createTime);
        }
        wxSport.setDateId(dateId);
        wxSport.setLikeCount(sportData.getLikeCount() == null ? 0 : sportData.getLikeCount());
        wxSport.setStepCount(sportData.getStepCount());
        wxSport.setName(name);
        wxSport.setNum(sportData.getNum() == null ? 0 : sportData.getNum());
        wxSport.setCreateTime(createTime);
    }


    public static boolean check(String str) {
        String regex = "\\w+,\\w+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    @Autowired
    private DeptService deptService;

    @Autowired
    private SportService sportService;

    @Autowired
    private UserService userService;
}
