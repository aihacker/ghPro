package wxgh.data.entertain.sport.history;

import java.io.Serializable;

/**
 * Created by xlkai.
 */
public class DateStep implements Serializable {

    private Integer dateId;
    private Integer stepCount;

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

}
