package com.lg.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class Task<T> implements RunnableFuture<T> {

    FutureTask<T> mTFutureTask;

    public Task(Runnable runnable) {
        mTFutureTask = new FutureTask<T>(runnable, null);
    }


    @Override
    public void run() {
        mTFutureTask.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return mTFutureTask.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return mTFutureTask.isCancelled();
    }

    @Override
    public boolean isDone() {
        return mTFutureTask.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return mTFutureTask.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return mTFutureTask.get(timeout, unit);
    }


}
