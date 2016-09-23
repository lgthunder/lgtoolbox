package com.lg.task;

import com.lg.log.Logs;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class IThread extends Thread {
    public IThread() {
        super();
    }

    public IThread(ThreadGroup group, Runnable runnable, String threadName, long stackSize) {
        super(group, runnable, threadName, stackSize);
    }

    @Override
    public void run() {
        super.run();
        Logs.i(getName());
    }
}
