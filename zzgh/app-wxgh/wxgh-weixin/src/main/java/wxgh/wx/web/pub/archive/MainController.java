package wxgh.wx.web.pub.archive;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.utils.WeixinUtils;
import wxgh.entity.pub.Archive;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.pub.ArchiveService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author hhl
 * @create 2017-07-31
 **/
@Controller
public class MainController {
    private String imgList = new String();

    @RequestMapping
    public void list(Model model){
        SeUser user = UserSession.getUser();
        String userid = user.getUserid();
        String sql = "select * from t_archive where userid = '"+userid+"'";
        List<Archive> archivesList = pubDao.queryList(Archive.class,sql);
        for(int i = 0;i<archivesList.size();i++){
            String[] imgListID = archivesList.get(i).getImgList().split(",");
            List<String> imgIdList = new ArrayList<String>();
            for(int j = 0;j<imgListID.length;j++){
                String sql2 = "select * from t_sys_file where file_id = '"+imgListID[j]+"'";
                SysFile sysFile = pubDao.query(SysFile.class,sql2);
                imgIdList.add(sysFile.getFilePath());
            }
            archivesList.get(i).setImgIdList(imgIdList);
        }
        model.put("archives",archivesList);
    }

    //删除档案
    @RequestMapping
    public ActionResult del(Integer id){
        String sql = "delete from t_archive where id ='"+id+"'";
        //删除一条；
        pubDao.execute(sql);
        return ActionResult.ok("删除档案成功");
    }

    //添加档案页面配置上传照片接口
    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    //添加档案
    @RequestMapping
    public ActionResult get(Archive archive){
        //判断档案是否符合条件
        if(StrUtils.empty(archive.getName())){
            return ActionResult.error("健康档案名称不能为空哦");
        }
        if (StrUtils.empty(archive.getImgIds())) {
            return ActionResult.error("健康档案图片不能为空哦");
        }

        //分割字符串
        String[] imgs = archive.getImgIds().split(",");
        //倒序
        Collections.reverse(Arrays.asList(imgs));
        //清空imgList
        List<String> imgList = new ArrayList<>();
        for(String mediaId:imgs){
            fileApi.wxDownload(mediaId, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    imgList.add(file.getFileId());
                }
            });
        }

        archive.setImgList(TypeUtils.listToStr(imgList));
        SeUser user = UserSession.getUser();
        archive.setUserid(user.getUserid());
        archive.setName(archive.getName());
        archive.setStatus(1);

        //增加一条记录
        archiveService.addArchive(archive);

        return ActionResult.ok();
    }

    @Autowired
    private FileApi fileApi;

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private PubDao pubDao;

}
