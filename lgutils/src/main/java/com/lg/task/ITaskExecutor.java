package com.lg.task;

import com.lg.log.Logs;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public abstract class ITaskExecutor implements TaskExecutor<Task> {

    ThreadPoolExecutor mExecutorService;

    public ITaskExecutor() {
        mExecutorService = create();
    }

    abstract ThreadPoolExecutor create();

    abstract String executorName();

    @Override
    public void execute(Task task) {
        mExecutorService.submit(task);

    }

    @Override
    public void remove(Task task) {
        mExecutorService.remove(task);
    }

    @Override
    public int getTaskCount() {
        return mExecutorService.getQueue().size();
    }

    @Override
    public boolean isShutdown() {
        return mExecutorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return mExecutorService.isTerminated();
    }


    @Override
    public void shutdown() {
        mExecutorService.shutdown();
    }

    public void printStatus() {
        Logs.i(getClass().getSimpleName(), " thread count:" + mExecutorService.getActiveCount());
        Logs.i(getClass().getSimpleName(), " task undone count:" + mExecutorService.getQueue().size());
    }


}
