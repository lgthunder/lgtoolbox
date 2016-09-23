package com.lg.log;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public interface ILog {
    void i(String tag, Object msg, Object... args);

    void d(String tag, Object msg, Object... args);

    void w(String tag, Object msg, Object... args);

    void e(String tag, Object msg, Object... args);

    void l(String tag, Object msg, Object... args);

    void p(String tag, Object msg, Object... args);

}
