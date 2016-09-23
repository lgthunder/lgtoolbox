package com.lg.task;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public interface TaskExecutor<T extends Task> {
    void execute(T task);

    void remove(T task);

    int getTaskCount();

    boolean isShutdown();

    void shutdown();

    boolean isTerminated();
}
