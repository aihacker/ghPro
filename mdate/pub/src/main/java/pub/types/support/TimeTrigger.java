package pub.types.support;

import pub.types.Action;
import pub.types.ReloadTrigger;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-5-26
 */
@SuppressWarnings("unchecked")
public class TimeTrigger implements ReloadTrigger, Runnable {

    private Action action;
    public long intervalMillis;

    @Override
    public void setTriggerAction(Action action) {
        this.action = action;
    }

    public TimeTrigger(long intervalMillis) {
        this.intervalMillis = intervalMillis;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(this.intervalMillis);
                if (this.action != null) {
                    this.action.execute(null);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
