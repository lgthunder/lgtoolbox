package com.lg.task;

import rx.functions.Action0;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class ITask<T> extends Task<T> {


    public static ITask task(Action0 runnable) {
        return new ITask(new Runnable() {
            @Override
            public void run() {
                runnable.call();
            }
        });
    }

    public ITask(Runnable runnable) {
        super(runnable);
    }

    public void cancelTask() {
        cancel(true);
    }

    public boolean isTaskCanceled() {
        return isCancelled();
    }
}
