package com.lg.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class ParserTaskExecutor extends ITaskExecutor {
    public static final String TAG = "ParserTaskExecutor";


    private static class SingletonHolder {
        private static ParserTaskExecutor INSTANCE = new ParserTaskExecutor();
    }

    public static ParserTaskExecutor INSTANCE = SingletonHolder.INSTANCE;

    @Override
    ThreadPoolExecutor create() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(4, new IThreadFactory(TAG));
    }


    @Override
    String executorName() {
        return null;
    }
}
