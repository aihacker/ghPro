package wxgh.data.common.disease;


import wxgh.entity.common.disease.DiseaseJy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
public class DiseaseJyData implements Serializable {

    private List<DiseaseJy> diseaseJies;
    private Integer applyId;

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public List<DiseaseJy> getDiseaseJies() {
        return diseaseJies;
    }

    public void setDiseaseJies(List<DiseaseJy> diseaseJies) {
        this.diseaseJies = diseaseJies;
    }
}
