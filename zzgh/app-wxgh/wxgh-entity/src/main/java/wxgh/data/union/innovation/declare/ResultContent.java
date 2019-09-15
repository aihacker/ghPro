package wxgh.data.union.innovation.declare;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-25 10:09
 *----------------------------------------------------------
 */
public class ResultContent {

    private String txt;
    private List<ResultImg> imgs;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public List<ResultImg> getImgs() {
        return imgs;
    }

    public void setImgs(List<ResultImg> imgs) {
        this.imgs = imgs;
    }
    
}

