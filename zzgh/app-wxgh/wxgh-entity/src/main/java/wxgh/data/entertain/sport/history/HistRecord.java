package wxgh.data.entertain.sport.history;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xlkai.
 */
public class HistRecord implements Serializable {

    private String name;
    private List<DateStep> dateSteps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DateStep> getDateSteps() {
        return dateSteps;
    }

    public void setDateSteps(List<DateStep> dateSteps) {
        this.dateSteps = dateSteps;
    }
}
