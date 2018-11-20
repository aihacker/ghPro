package com.gpdi.mdata.web.reportform.task.match;

import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author gan
 * @date 2018/4/19
 * @using
 */
public class ExecutorManage {

    private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(
            Runtime.getRuntime().availableProcessors() + 1);

    public static void submit(FutureTask<?> futureTask){
        service.submit(futureTask);
    }
}
