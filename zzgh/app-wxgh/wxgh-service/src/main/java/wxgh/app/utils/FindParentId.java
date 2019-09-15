package wxgh.app.utils;

import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import wxgh.app.sys.task.impl.SportAsyncImpl;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DELL on 2018/9/28.
 */
public class FindParentId {

    private Integer id=1;

    public Integer find(String[] deptIds){
        List<Integer> list=DeptId.BENBU.getDeptIds();
        for (int i=0;i<deptIds.length;i++){
            if(list.contains(Integer.parseInt(deptIds[i]))) {
                id=Integer.parseInt(deptIds[i]);
                break;
            }
            continue;
        }
        return id;
    }

    public String[] getSpiltIds(String deptIds){
        String[] tmp = deptIds.split(",");
        return tmp;
    }
}
