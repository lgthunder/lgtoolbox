package com.lg.log;

/**
 * @author leiting
 * @des
 * @since 16/9/23
 */
public class ConsoleLog implements ILog {

    @Override
    public void i(String tag, Object msg, Object... args) {
        System.out.print(tag + msg);
    }

    @Override
    public void d(String tag, Object msg, Object... args) {
        System.out.print(tag + msg);
    }

    @Override
    public void w(String tag, Object msg, Object... args) {
        System.out.print(tag + msg);
    }

    @Override
    public void e(String tag, Object msg, Object... args) {
        System.out.print(tag + msg);
    }

    @Override
    public void l(String tag, Object msg, Object... args) {
        System.out.print(tag + msg);
    }

    @Override
    public void p(String tag, Object msg, Object... args) {
        System.out.print(tag + msg);
    }
}
