package pub.types.support;

import org.springframework.core.io.Resource;
import pub.types.Action;
import pub.types.Executable;
import pub.types.ReloadTrigger;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-5-26
 */
public class FileResourceChangeTrigger implements ReloadTrigger {

    private Action action;

    public Resource[] resources;

    @Override
    public void setTriggerAction(Action action) {
        this.action = action;
    }



}
